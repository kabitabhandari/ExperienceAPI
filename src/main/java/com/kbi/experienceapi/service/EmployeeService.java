package com.kbi.experienceapi.service;

import com.kbi.experienceapi.model.employeesworld.Employee;
import com.kbi.experienceapi.model.employeesworld.SomeAttributesEmployee;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final WebClient webClientForEmployee;

    public EmployeeService(WebClient.Builder builder) {
        webClientForEmployee = builder.baseUrl("http://localhost:8081").build();
    }


    public List<SomeAttributesEmployee> getEmployees() {

        Employee[] employeeArray = webClientForEmployee
                .get()
                .uri("/employees")
                .retrieve()
                .bodyToMono(Employee[].class)
                .block();


        List<SomeAttributesEmployee> someAttributesEmployee  = Arrays.stream(employeeArray).map(e -> pickingFewPropertiesFromEmployee(e)).collect(Collectors.toList());


       // return employeeArray;
        return someAttributesEmployee;

    }

    private SomeAttributesEmployee pickingFewPropertiesFromEmployee(Employee emp) {
        SomeAttributesEmployee sae = new SomeAttributesEmployee();
        sae.setEmployeeid(emp.getEmployeeid());
        sae.setName(emp.getName());
        sae.setJobtitle(emp.getJobtitle());
        return sae;
    }
}
