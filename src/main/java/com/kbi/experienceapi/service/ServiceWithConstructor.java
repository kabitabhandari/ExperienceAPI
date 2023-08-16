package com.kbi.experienceapi.service;

import com.kbi.experienceapi.model.employeesworld.Employee;
import com.kbi.experienceapi.model.employeesworld.EmployeeDesignation;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceWithConstructor {
    private final WebClient webClientForEmployee;

    public ServiceWithConstructor(WebClient.Builder builder) {
        webClientForEmployee = builder.baseUrl("http://localhost:8081").build();
    }


    public List<EmployeeDesignation> getEmployees() {

        Employee[] employeeArray = webClientForEmployee
                .get()
                .uri("/employees")
                .retrieve()
                .bodyToMono(Employee[].class)
                .block();


        List<EmployeeDesignation> someAttributesEmployee  = Arrays.stream(employeeArray).map(e -> pickingFewPropertiesFromEmployee(e)).collect(Collectors.toList());


        // return employeeArray;
        return someAttributesEmployee;

    }

    private EmployeeDesignation pickingFewPropertiesFromEmployee(Employee emp) {
        EmployeeDesignation sae = new EmployeeDesignation();
        sae.setEmployeeid(emp.getEmployeeid());
        sae.setName(emp.getName());
        sae.setJobtitle(emp.getJobtitle());
        return sae;
    }
}
