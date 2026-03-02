package info.jab.ms.at;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

@Testcontainers
public abstract class AcceptanceTestBaseTest {

    private static final String APP_SERVICE = "app";
    private static final int APP_PORT = 8080;

    @Container
    static final ComposeContainer ENVIRONMENT =
        new ComposeContainer(new File("docker-compose.yml"))
            .withExposedService(APP_SERVICE, APP_PORT,
                Wait.forHttp("/actuator/health")
                    .forStatusCode(200)
                    .withStartupTimeout(Duration.ofSeconds(120)))
            .withLocalCompose(true);

    @BeforeAll
    static void setupRestAssured() {
        RestAssured.baseURI = "http://" + ENVIRONMENT.getServiceHost(APP_SERVICE, APP_PORT);
        RestAssured.port = ENVIRONMENT.getServicePort(APP_SERVICE, APP_PORT);
    }
}
