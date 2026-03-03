package info.jab.ms.at;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

/**
 * Base for acceptance tests. The app runs in a Docker container (separate JVM).
 * To hit breakpoints in app code (e.g. CalculatorService) during a test:
 * <ol>
 *   <li>Set a breakpoint on the first line of the test method (so the test pauses before calling the app).</li>
 *   <li>Run the acceptance test in Debug (e.g. right‑click {@code shouldReturnSumOfTwoIntegers} → Debug).
 *       The container starts; execution stops at your breakpoint.</li>
 *   <li>While paused, run your "Remote JVM Debug" config (localhost:5005). It attaches to the app in the container.</li>
 *   <li>Press Resume (F9). The test sends the request; the app handles it and stops at breakpoints in app code.</li>
 * </ol>
 */
@Testcontainers
public abstract class AcceptanceTestBaseTest {

    private static final String APP_SERVICE = "app";
    private static final int APP_PORT = 8080;
    private static final int DEBUG_PORT = 5005;

    @Container
    static final ComposeContainer ENVIRONMENT =
        new ComposeContainer(new File("docker-compose.yml"))
            .withExposedService(APP_SERVICE, APP_PORT,
                Wait.forHttp("/actuator/health")
                    .forPort(APP_PORT)
                    .forStatusCode(200)
                    .withStartupTimeout(Duration.ofSeconds(120)))
            .withExposedService(APP_SERVICE, DEBUG_PORT);

    @BeforeAll
    static void setupRestAssured() {
        RestAssured.baseURI = "http://" + ENVIRONMENT.getServiceHost(APP_SERVICE, APP_PORT);
        RestAssured.port = ENVIRONMENT.getServicePort(APP_SERVICE, APP_PORT);
    }
}
