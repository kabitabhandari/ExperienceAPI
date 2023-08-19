package com.kbi.experienceapi.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * ________________________________________________________________ Integration Testing - Using
 * WebTestClient: ONLY USE IF NEEDED
 * ________________________________________________________________
 *
 * <p>Web Test Client is used to test the REAL Web Services using HTTP. So it requires the actual
 * service to be up and running This is why we have to use the EXACT same request and responses
 * while testing. For example if we are making a call to third party api @
 * https://vgh.doo.com/v1/rtt/reports then this service has to be up and running, otherwise our test
 * will fail.
 */
@SpringBootTest()
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest_Using_WebTestClient {

    @Autowired static WebTestClient webTestClient;

    @BeforeAll
    public static void setup() {
        webTestClient =
                WebTestClient.bindToServer()
                        .baseUrl("http://localhost:8081")
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .build();
    }

    @Test
    void getEmployeesShouldReturnList() {
        webTestClient.get().uri("/all-employees").exchange().expectStatus().is2xxSuccessful();
    }
}
