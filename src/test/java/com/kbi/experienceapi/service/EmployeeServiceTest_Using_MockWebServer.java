package com.kbi.experienceapi.service;

import com.kbi.experienceapi.model.employeesworld.Employee;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest_Using_MockWebServer {
    public MockWebServer mockWebServer;
    /**
     * Mock Web server allows our code to use real HTTP calls to a **local endpoint**. We get the benefit of testing
     * the intended HTTP interactions and dont have to face any challenge of mocking a complex Fluent Client, like
     * Reactive ones. Using Mock Web server is highly recommended to write the integration tests.
     */
    private WebClient webClient;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.close();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Test
    void getEmployeesShouldReturn200() {
        mockWebServer.enqueue(new MockResponse().setHeader("content-type", "application/json"));
        EmployeeService employeeService = new EmployeeService(webClient);

        Mono<Employee[]> employeesArrayInMono = employeeService.getEmployees();
        StepVerifier.create(employeesArrayInMono).assertNext(new Consumer<Employee[]>() {
                    @Override
                    public void accept(Employee[] t) {
                        List<Employee> employeees = Arrays.stream(t).toList();
                        Assertions.assertEquals("Samuel", employeees.get(0).getName());
                    }
                });
    }


}
