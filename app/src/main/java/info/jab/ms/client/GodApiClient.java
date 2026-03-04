package info.jab.ms.client;

import org.jspecify.annotations.NullMarked;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

@Component
@NullMarked
public class GodApiClient {

    private static final ParameterizedTypeReference<List<String>> GODS_TYPE =
        new ParameterizedTypeReference<>() {};

    private final RestClient restClient;
    private final GodApiProperties properties;

    public GodApiClient(RestClient.Builder restClientBuilder, GodApiProperties properties) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(properties.timeoutSeconds()));
        factory.setReadTimeout(Duration.ofSeconds(properties.timeoutSeconds()));
        this.restClient = restClientBuilder.requestFactory(factory).build();
        this.properties = properties;
    }

    public List<String> getGreekGods() {
        return fetchGods(properties.greekUrl());
    }

    public List<String> getRomanGods() {
        return fetchGods(properties.romanUrl());
    }

    public List<String> getNordicGods() {
        return fetchGods(properties.nordicUrl());
    }

    private List<String> fetchGods(String url) {
        try {
            var body = restClient.get()
                .uri(url)
                .retrieve()
                .body(GODS_TYPE);
            return body != null ? body : List.of();
        } catch (Exception e) {
            return List.of();
        }
    }
}
