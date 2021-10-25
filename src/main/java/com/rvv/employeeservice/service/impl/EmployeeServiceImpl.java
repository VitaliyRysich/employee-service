package com.rvv.employeeservice.service.impl;

import com.rvv.employeeservice.dto.EmployeeRequestDto;
import com.rvv.employeeservice.exception.EmployeeNotFoundException;
import com.rvv.employeeservice.model.Employee;
import com.rvv.employeeservice.model.Grade;
import com.rvv.employeeservice.operations.EmployeeSpecification;
import com.rvv.employeeservice.repository.EmployeeRepository;
import com.rvv.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.rvv.employeeservice.operations.EmployeeSearchCriteria.*;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findEmployeeById(Long id) {
        var employee = employeeRepository.findById(id);
        return employee.orElseThrow(() -> new EmployeeNotFoundException(
                String.format("Employee with ID: %s  not exist.", id)));
    }

    @Override
    public void deleteEmployeeById(Long id) {
        if (!employeeRepository.existsById(id)) throw new EmployeeNotFoundException(
                String.format("Employee with ID: %s  not exist.", id));
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeRequestDto employeeDto) {
        var employee = findEmployeeById(id);
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setGrade(employeeDto.getGrade());
        employee.setSalary(employeeDto.getSalary());
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAllByRequestParams(Map<String, String> requestParams) {
        var specification = new EmployeeSpecification();

        for (Map.Entry<String, String> param: requestParams.entrySet()) {
            var key = param.getKey();
            var value = param.getValue();
            var criteria = Arrays.stream(values())
                    .filter(c -> c.getKey().equals(key))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("Unsupported request param %s.", key)));

            criteria.setValue(criteria.getField().equals("grade")
                            ? Grade.valueOf(value.toUpperCase())
                            : value);

            specification.add(criteria);
        }
        return employeeRepository.findAll(specification);
    }
}
