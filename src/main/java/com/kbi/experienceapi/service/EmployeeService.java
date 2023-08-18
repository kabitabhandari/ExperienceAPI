package com.kbi.experienceapi.service;

import com.kbi.experienceapi.model.employeesworld.Employee;
import com.kbi.experienceapi.model.employeesworld.EmployeeDesignation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final WebClient webClient;

    public EmployeeService(@Qualifier("calling-employee") WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<List<EmployeeDesignation>> getEmployees() {

        Employee[] employeeArrayResponse = webClient
                .get()
                .uri("/all-employees")
                .retrieve()
                .bodyToMono(Employee[].class)
                .block();


        List<EmployeeDesignation> ListOfEmployeeWithDesignation = Arrays.stream(employeeArrayResponse).map(e -> onlyIncludeDesignation(e)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(ListOfEmployeeWithDesignation);
        //return new ResponseEntity<>(ListOfEmployeeWithDesignation, HttpStatus.CREATED); // or this way to return RE

    }

    private EmployeeDesignation onlyIncludeDesignation(Employee emp) {
        EmployeeDesignation ed = new EmployeeDesignation();
        ed.setEmployeeid(emp.getEmployeeid());
        ed.setName(emp.getName());
        ed.setJobtitle(emp.getJobtitle());
        return ed;
    }
}
