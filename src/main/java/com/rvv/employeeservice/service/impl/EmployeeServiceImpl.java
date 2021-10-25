package com.rvv.employeeservice.service.impl;

import com.rvv.employeeservice.dto.EmployeeReq;
import com.rvv.employeeservice.exception.EmployeeNotFoundException;
import com.rvv.employeeservice.model.Employee;
import com.rvv.employeeservice.model.Grade;
import com.rvv.employeeservice.operations.EmployeeSpecification;
import com.rvv.employeeservice.repository.EmployeeRepository;
import com.rvv.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.rvv.employeeservice.operations.EmployeeSearchCriteria.values;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

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
    public Employee saveEmployee(EmployeeReq employeeReq) {
        var employee = new Employee();
        modelMapper.map(employeeReq, employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeReq employeeReq) {
        var employee = findEmployeeById(id);
        modelMapper.map(employeeReq, employee);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAllByRequestParams(Map<String, String> requestParams, Pageable pageable) {
        requestParams.remove("size");
        requestParams.remove("page");
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
        return employeeRepository.findAll(specification, pageable).toList();
    }
}
