package info.jab.ms.config;

import info.jab.ms.client.GodApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GodApiProperties.class)
public record GodApiConfiguration() { }
