package info.jab.ms.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "god.api")
public record GodApiProperties(
        String greekUrl,
        String romanUrl,
        String nordicUrl,
        @DefaultValue("5") int timeoutSeconds
) {}
