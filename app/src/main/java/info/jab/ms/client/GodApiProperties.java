package info.jab.ms.client;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "god.api")
public record GodApiProperties(
        @NonNull String greekUrl,
        @NonNull String romanUrl,
        @NonNull String nordicUrl,
        @DefaultValue("5") int timeoutSeconds
) {}
