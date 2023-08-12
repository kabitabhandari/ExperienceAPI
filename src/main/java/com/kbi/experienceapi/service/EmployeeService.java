package com.kbi.experienceapi.service;

import com.kbi.experienceapi.model.employeesworld.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmployeeService {
    private final WebClient webClientForEmployee;

    public EmployeeService(WebClient.Builder builder) {
        webClientForEmployee = builder.baseUrl("http://localhost:8081").build();
    }


    public Employee[] getEmployees() {
        return
                webClientForEmployee
                        .get()
                        .uri("/employees")
                        .retrieve()
                        .bodyToMono(Employee[].class)
                        .block();
    }
}
