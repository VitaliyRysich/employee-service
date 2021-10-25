package com.rvv.employeeservice.service;

import com.rvv.employeeservice.dto.EmployeeRequestDto;
import com.rvv.employeeservice.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee findEmployeeById(Long id);

    void deleteEmployeeById(Long id);

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Long id, EmployeeRequestDto employee);

    List<Employee> findAllByRequestParams(Map<String, String> requestParams);
}
