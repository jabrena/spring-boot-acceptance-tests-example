package info.jab.ms.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

@Component
public class GodApiClient {

    private final RestClient restClient;
    private final String greekUrl;
    private final String romanUrl;
    private final String nordicUrl;

    public GodApiClient(
            RestClient.Builder restClientBuilder,
            @Value("${god.api.greek-url}") String greekUrl,
            @Value("${god.api.roman-url}") String romanUrl,
            @Value("${god.api.nordic-url}") String nordicUrl,
            @Value("${god.api.timeout-seconds:5}") int timeoutSeconds) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(timeoutSeconds));
        factory.setReadTimeout(Duration.ofSeconds(timeoutSeconds));
        this.restClient = restClientBuilder.requestFactory(factory).build();
        this.greekUrl = greekUrl;
        this.romanUrl = romanUrl;
        this.nordicUrl = nordicUrl;
    }

    public List<String> getGreekGods() {
        return fetchGods(greekUrl);
    }

    public List<String> getRomanGods() {
        return fetchGods(romanUrl);
    }

    public List<String> getNordicGods() {
        return fetchGods(nordicUrl);
    }

    private List<String> fetchGods(String url) {
        var result = restClient.get()
            .uri(url)
            .retrieve()
            .body(new ParameterizedTypeReference<List<String>>() {});
        if (result == null) {
            return List.of();
        }
        return result;
    }
}
