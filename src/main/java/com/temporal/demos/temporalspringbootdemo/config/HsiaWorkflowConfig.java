package com.temporal.demos.temporalspringbootdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hsia-workflow-saga")
@Data
public class HsiaWorkflowConfig {

    private long waitForAtpSeconds;
    private long waitForHsiaCallbackSeconds;

}
