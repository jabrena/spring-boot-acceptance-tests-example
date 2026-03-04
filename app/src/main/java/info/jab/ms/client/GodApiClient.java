package info.jab.ms.client;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

@Component
public class GodApiClient {

    private static final ParameterizedTypeReference<List<String>> GODS_TYPE =
        new ParameterizedTypeReference<>() {};

    private final @NonNull RestClient restClient;
    private final @NonNull GodApiProperties properties;

    public GodApiClient(RestClient.@NonNull Builder restClientBuilder, @NonNull GodApiProperties properties) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(properties.timeoutSeconds()));
        factory.setReadTimeout(Duration.ofSeconds(properties.timeoutSeconds()));
        this.restClient = restClientBuilder.requestFactory(factory).build();
        this.properties = properties;
    }

    public @NonNull List<String> getGreekGods() {
        return fetchGods(properties.greekUrl());
    }

    public @NonNull List<String> getRomanGods() {
        return fetchGods(properties.romanUrl());
    }

    public @NonNull List<String> getNordicGods() {
        return fetchGods(properties.nordicUrl());
    }

    private @NonNull List<String> fetchGods(@NonNull String url) {
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
