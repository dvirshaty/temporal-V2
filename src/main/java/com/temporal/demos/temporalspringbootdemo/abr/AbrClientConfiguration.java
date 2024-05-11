package com.temporal.demos.temporalspringbootdemo.abr;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "abr-client-configuration")
@Data
public class AbrClientConfiguration {
    private String baseUrl;
    private String username;
    private String password;
}
