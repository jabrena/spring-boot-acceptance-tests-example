package info.jab.ms.client;

import org.jspecify.annotations.NullMarked;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "god.api")
@NullMarked
public record GodApiProperties(
        String greekUrl,
        String romanUrl,
        String nordicUrl,
        @DefaultValue("5") int timeoutSeconds
) {}
