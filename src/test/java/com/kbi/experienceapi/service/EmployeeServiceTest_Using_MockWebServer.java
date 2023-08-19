package com.kbi.experienceapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper = new ObjectMapper();
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
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort()); //port= 61664
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Test
    void getEmployeesShouldReturn200() throws JsonProcessingException {
        //mocked response
        mockWebServer
                .enqueue(
                        new MockResponse()
                                .setHeader("content-type", "application/json") //optional, until this service specifically specifies response header is produced by it. Check for it's controller, if it has produces : application/json
                                .setHeader("key", "value") //if more than one header
                                .setBody(objectMapper.writeValueAsString(employeeArrayResponseMock())));

        //actual response
        EmployeeService employeeService = new EmployeeService(webClient);
        Mono<Employee[]> actualResponseOfEmployeesArrayInMono = employeeService.getEmployees();

        //assertion
        StepVerifier.create(actualResponseOfEmployeesArrayInMono)
                .assertNext(new Consumer<Employee[]>() {
                    @Override
                    public void accept(Employee[] t) {
                        List<Employee> employeees = Arrays.stream(t).toList();
                        Assertions.assertEquals("name1", employeees.get(0).getName());
                    }
                })
                .expectComplete()
                .verify();
    }

    private Employee[] employeeArrayResponseMock() {
        //Employee[] = [  {}   ,  {},    {},     {},     {} ]
        //names          emp0    emp1   emp2    emp3    emp4
        Employee[] emp = new Employee[5];
        emp[0] = new Employee("id1", "name1", 23, "software-engineer-1", 77000);
        emp[1] = new Employee("id2", "name2", 45, "software-engineer-2", 97000);
        emp[2] = new Employee("id3", "name3", 35, "software-engineer-3", 87000);
        emp[3] = new Employee("id4", "name4", 19, "intern", 57000);
        emp[4] = new Employee("id5", "name5", 27, "software-engineer-2", 67000);
        return emp;

    }

}
