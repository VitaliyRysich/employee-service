package com.rvv.employeeservice.service;

import com.rvv.employeeservice.dto.EmployeeReq;
import com.rvv.employeeservice.model.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee findEmployeeById(Long id);

    void deleteEmployeeById(Long id);

    Employee saveEmployee(EmployeeReq employeeReq);

    Employee updateEmployee(Long id, EmployeeReq employeeReq);

    List<Employee> findAllByRequestParams(Map<String, String> requestParams, Pageable pageable);
}
