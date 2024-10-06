package com.minitask.taskmanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jakarta")
@Getter
public class AppConfig {

    @Value("${validation.notEmptyMessage}")
    private String notEmptyMessage;
}
