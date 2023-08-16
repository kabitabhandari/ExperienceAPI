package com.kbi.experienceapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {
    private WebClient.Builder builder;

    @Autowired
    public WebclientConfig(WebClient.Builder builder) {
        this.builder = builder;
    }

    @Bean
    public WebClient webClientForEmployee() {

       return builder.baseUrl("http://localhost:8081").build();
    }

}
