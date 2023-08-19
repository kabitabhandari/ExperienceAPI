package com.kbi.experienceapi.mapper;

import com.kbi.experienceapi.model.employeesworld.Employee;
import com.kbi.experienceapi.model.employeesworld.EmployeeDesignation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeMapper {

    public ResponseEntity<List<EmployeeDesignation>> customizeEmployeeArray(
            Mono<Employee[]> employeeArray) {
        Employee[] empArray = employeeArray.block();
        List<Employee> employees = Arrays.stream(empArray).toList();
        List<EmployeeDesignation> ListOfEmployeeWithDesignation =
                employees.stream().map(e -> onlyIncludeDesignation(e)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(ListOfEmployeeWithDesignation);
        // return new ResponseEntity<>(ListOfEmployeeWithDesignation, HttpStatus.CREATED); // or
        // this way to return RE
    }

    private EmployeeDesignation onlyIncludeDesignation(Employee emp) {
        EmployeeDesignation ed = new EmployeeDesignation();
        ed.setEmployeeid(emp.getEmployeeid());
        ed.setName(emp.getName());
        ed.setJobtitle(emp.getJobtitle());
        return ed;
    }
}
