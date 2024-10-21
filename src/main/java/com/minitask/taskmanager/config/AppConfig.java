package com.minitask.taskmanager.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
//@ConfigurationProperties(prefix = "jakarta")
//@Setter //needs to be together if using @ConfigurationProperties
@Getter
public class AppConfig {

    @Value("${jakarta.validation.notEmptyMessage}")
    private String notEmptyMessage;

  /*  private ValidationProps validation;

    @Getter
    @Setter
    public static class ValidationProps {
        private String notEmptyMessage;
    }*/
}
