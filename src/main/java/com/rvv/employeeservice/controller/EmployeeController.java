package com.rvv.employeeservice.controller;

import com.rvv.employeeservice.dto.EmployeeRequestDto;
import com.rvv.employeeservice.dto.EmployeeResponseDto;
import com.rvv.employeeservice.model.Employee;
import com.rvv.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> findEmployeeById (@PathVariable Long id) {
        var employee = employeeService.findEmployeeById(id);
        var employeeResponseDto = modelMapper.map(employee, EmployeeResponseDto.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById (@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> saveEmployee (@RequestBody EmployeeRequestDto employeeRequestDto) {
        var employee = modelMapper.map(employeeRequestDto, Employee.class);
        var savedEmployee = employeeService.saveEmployee(employee);
        var employeeResponseDto = modelMapper.map(savedEmployee, EmployeeResponseDto.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> deleteEmployeeById (@PathVariable Long id, @RequestBody EmployeeRequestDto employeeRequestDto) {
        var updatedEmployee = employeeService.updateEmployee(id, employeeRequestDto);
        var employeeResponseDto = modelMapper.map(updatedEmployee, EmployeeResponseDto.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDto>> searchEmployees (@RequestParam Map<String, String> requestParams) {
        var employeeList = employeeService.findAllByRequestParams(requestParams);
        var responseDtoList = employeeList
                .stream()
                .map(e -> modelMapper.map(e, EmployeeResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDtoList);
    }
}
