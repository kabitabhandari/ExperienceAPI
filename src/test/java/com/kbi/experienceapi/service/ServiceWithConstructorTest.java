package com.kbi.experienceapi.service;

import com.kbi.experienceapi.config.WebclientConfig;
import com.kbi.experienceapi.model.employeesworld.Employee;
import com.kbi.experienceapi.model.employeesworld.EmployeeDesignation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceWithConstructorTest {
    /**
     * Assuming our class had constructor initialization. eg ServiceWithConstructor.java
     */

    @Mock
    WebClient webClientForEmployee;
    @Mock
    WebClient.Builder builder;
    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @Mock
    WebClient.ResponseSpec responseSpecMock;
    @InjectMocks
    ServiceWithConstructor serviceWithConstructor;

    @Test  // FAILS
    void getEmployeesShouldReturnList() {
        when(builder.baseUrl(any())).thenReturn(mockBaseUrl());
        when(webClientForEmployee.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/all-employees")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(Employee[].class)).thenReturn(Mono.just(employeeArrayResponseMock()));

        // convert employeeArrayResponseMock() to list

        ResponseEntity<List<EmployeeDesignation>> actual = serviceWithConstructor.getEmployees();
        Assertions.assertEquals(HttpStatus.OK.is2xxSuccessful(), actual.getStatusCode().is2xxSuccessful());
    }

    private WebClient.Builder mockBaseUrl() {
        return builder.baseUrl("/base-url");
    }

    private Employee[] employeeArrayResponseMock() {
        //Employee[] = [  {}   ,  {},    {},     {},     {} ]
        //names          emp0    emp1   emp2    emp3    emp4
        Employee[] emp = new Employee[5];
        emp[0] = new Employee("id1","name1",23,"software-engineer-1", 77000);
        emp[1] = new Employee("id2","name2",45,"software-engineer-2", 97000);
        emp[2] = new Employee("id3","name3",35,"software-engineer-3", 87000);
        emp[3] = new Employee("id4","name4",19,"intern", 57000);
        emp[4] = new Employee("id5","name5",27,"software-engineer-2", 67000);
        return emp;

    }



}