package com.minitask.taskmanager.integration;

import com.minitask.taskmanager.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "jakarta.validation.notEmptyMessage=test empty message",
})
public class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    public void testValidationProperties() {
        assertThat(appConfig.getNotEmptyMessage()).isNotNull();
        assertThat(appConfig.getNotEmptyMessage()).isEqualTo("test empty message");
    }

}
