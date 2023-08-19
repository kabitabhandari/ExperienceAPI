package com.kbi.experienceapi.service;

import com.kbi.experienceapi.model.employeesworld.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {
    private final WebClient webClient;

    public EmployeeService(@Qualifier("calling-employee") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Employee[]> getEmployees() {
        return webClient.get().uri("/all-employees").retrieve().bodyToMono(Employee[].class);
    }
}
