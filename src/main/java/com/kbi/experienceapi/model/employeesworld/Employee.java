package com.kbi.experienceapi.model.employeesworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String employeeid;
    private String name;
    private Integer age;
    private String jobtitle;
    private double salary;
}
