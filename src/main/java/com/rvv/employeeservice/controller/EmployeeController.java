package com.rvv.employeeservice.controller;

import com.rvv.employeeservice.dto.EmployeeReq;
import com.rvv.employeeservice.dto.EmployeeResp;
import com.rvv.employeeservice.model.Employee;
import com.rvv.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;

    @Operation(summary = "Find one employee", description = "Find a employee by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResp.class)) }),
            @ApiResponse(responseCode = "404", description = "No employees found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResp> findEmployeeById (@PathVariable Long id) {
        var employee = employeeService.findEmployeeById(id);
        var employeeResp = modelMapper.map(employee, EmployeeResp.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResp);
    }


    @Operation(summary = "Delete one employee", description = "Delete a employee by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted",content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "No employees found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById (@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Save an employee")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee saved", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResp.class)) }),
            @ApiResponse(responseCode = "500", description = "An error occured.", content = @Content) })
    @PostMapping
    public ResponseEntity<EmployeeResp> saveEmployee (@Valid @RequestBody EmployeeReq employeeReq) {
        var savedEmployee = employeeService.saveEmployee(employeeReq);
        var employeeResp = modelMapper.map(savedEmployee, EmployeeResp.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResp);
    }

    @Operation(summary = "Update one employee", description = "Update a employee by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResp.class)) }),
            @ApiResponse(responseCode = "404", description = "No emploees found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResp> updateEmployeeById (@PathVariable Long id,
                                                            @Valid @RequestBody EmployeeReq employeeReq) {
        var updatedEmployee = employeeService.updateEmployee(id, employeeReq);
        var employeeResp = modelMapper.map(updatedEmployee, EmployeeResp.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResp);
    }

    @Operation(summary = "Search employees", description = "Search for users by providing any number of arguments")
    @Parameters(value = {
            @Parameter(name = "name", description = "Search by name", in = ParameterIn.QUERY),
            @Parameter(name = "surname", description = "Search by surname", in = ParameterIn.QUERY),
            @Parameter(name = "grade", description = "Search by grade", in = ParameterIn.QUERY,
                    example = "NOVICE | ADVANCED_BEGINNER | COMPETENT | PROFICIENT | EXPERT"),
            @Parameter(name = "salary", description = "Search by salary", in = ParameterIn.QUERY),
            @Parameter(name = "greaterSalary", description = "Search by salary greater than a given value", in = ParameterIn.QUERY),
            @Parameter(name = "lessSalary", description = "Search by salary less than a given value", in = ParameterIn.QUERY),
            @Parameter(name = "matchName", description = "Search by several letters of the name", in = ParameterIn.QUERY,
                    example = "Andrz | drzej | ndrz"),
            @Parameter(name = "matchSurname", description = "Search by several letters of the surname", in = ParameterIn.QUERY,
                    example = "Kowals | lski | owal")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emploees found",content = {
                    @Content(mediaType = "application/json") })
    })
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResp>> searchEmployees ( @RequestParam(required = false) Map<String, String> requestParams,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        var employeeList = employeeService.findAllByRequestParams(requestParams, PageRequest.of(page, size));
        var responseDtoList = employeeList
                .stream()
                .map(e -> modelMapper.map(e, EmployeeResp.class))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDtoList);
    }

}
