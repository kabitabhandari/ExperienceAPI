package com.kbi.experienceapi.something2try;

import com.kbi.experienceapi.model.employeesworld.Employee;
import com.kbi.experienceapi.model.employeesworld.EmployeeDesignation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class EmpServiceWithBuiLder {
    private final WebClient webClientForEmployee;

    public EmpServiceWithBuiLder(WebClient.Builder builder) {
        webClientForEmployee = builder.baseUrl("http://localhost:8081").build();
    }

    public ResponseEntity<List<EmployeeDesignation>> getEmployees() {

        Employee[] employeeArray =
                webClientForEmployee
                        .get()
                        .uri("/all-employees")
                        .retrieve()
                        .bodyToMono(Employee[].class)
                        .block();

        List<EmployeeDesignation> someAttributesEmployee =
                Arrays.stream(employeeArray)
                        .map(e -> pickingFewPropertiesFromEmployee(e))
                        .collect(Collectors.toList());

        // return employeeArray;
        return ResponseEntity.status(HttpStatus.OK).body(someAttributesEmployee);
    }

    private EmployeeDesignation pickingFewPropertiesFromEmployee(Employee emp) {
        EmployeeDesignation sae = new EmployeeDesignation();
        sae.setEmployeeid(emp.getEmployeeid());
        sae.setName(emp.getName());
        sae.setJobtitle(emp.getJobtitle());
        return sae;
    }
}
