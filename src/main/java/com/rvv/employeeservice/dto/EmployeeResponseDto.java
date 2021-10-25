package com.rvv.employeeservice.dto;

import com.rvv.employeeservice.model.Grade;
import lombok.Data;

@Data
public class EmployeeResponseDto {

    private Long id;

    private String name;

    private String surname;

    private Grade grade;

    private Double salary;
}
