package com.kbi.experienceapi.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
class ServiceWithConstructorTest {
    /**
     * Assuming our class had constructor initialization. eg ServiceWithConstructor.java
     */

    @Mock
    WebClient webClientForEmployee;




}