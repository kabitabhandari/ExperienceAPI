package com.kbi.experienceapi.controller;

import com.kbi.experienceapi.mapper.EmployeeMapper;
import com.kbi.experienceapi.model.employeesworld.Employee;
import com.kbi.experienceapi.model.employeesworld.EmployeeDesignation;
import com.kbi.experienceapi.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Employees 360", description = " Employees 360 Controller")
@RestController
@RequestMapping(value = Controller.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class Controller {

    // Work on swagger Part
    public static final String PATH = "/emp";
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;


    @Autowired
    public Controller(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @Operation(summary = "Get Employees with Designation", description = "Get Employees with Designation using GET")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/fromEmployeeWorld",
            produces = {MediaType.APPLICATION_JSON_VALUE}, // This api will produce json output
            consumes = {MediaType.APPLICATION_JSON_VALUE}) // This api will accepts/consumes json input
    public ResponseEntity <List<EmployeeDesignation>> fetchEmployeesList() {
        log.info("Calling to Application Employees World");
        Mono<Employee[]> employeeArray=  employeeService.getEmployees();
        ResponseEntity<List<EmployeeDesignation>>  customizedEmployeesList = employeeMapper.customizeEmployeeArray(employeeArray);
        return customizedEmployeesList;
    }
}
