package com.kbi.experienceapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebclientConfig {

    @Bean
    @Qualifier("calling-employee")
    public WebClient webClientForEmployee() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    // OR below code does the same thing

    @Bean
    @Qualifier("calling-holidays")
    public WebClient webClientForHolidays(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }


}
