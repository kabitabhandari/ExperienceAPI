package com.kbi.experienceapi.controller;

import com.kbi.experienceapi.model.employeesworld.Employee;
import com.kbi.experienceapi.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {
    private final EmployeeService employeeService;

    @Autowired
    public Controller(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/getEmployees-fromEmployeeWorld")
    public Employee[] fetchEmployeesList() {
        log.info("Calling to Application Employees World");
        return employeeService.getEmployees();
    }
}
