package com.rvv.employeeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvv.employeeservice.dto.EmployeeReq;
import com.rvv.employeeservice.model.Employee;
import com.rvv.employeeservice.model.Grade;
import com.rvv.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        employee1 = Employee.builder()
                .id(1L)
                .name("Vitalii")
                .surname("Rysich")
                .grade(Grade.EXPERT)
                .salary(10000.0)
                .build();

        employee2 = Employee.builder()
                .id(2L)
                .name("Jan")
                .surname("Kowalski")
                .grade(Grade.NOVICE)
                .salary(8000.0)
                .build();
    }

    @Test
    void findEmployeeById() throws Exception {
        when(employeeService.findEmployeeById(any())).thenReturn(employee1);
        mockMvc.perform(get("/api/employee/{id}", 1)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void findEmployeeById_BadParameter() throws Exception {
        when(employeeService.findEmployeeById(any())).thenReturn(employee1);
        mockMvc.perform(get("/api/employee/{id}", "badParam")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteEmployeeById() throws Exception {
        mockMvc.perform(delete("/api/employee/{id}", 1)
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void saveEmployee() throws Exception {
        var employee = EmployeeReq.builder()
                .name("Vitalii")
                .surname("Rysich")
                .grade(Grade.EXPERT)
                .salary(10000.0)
                .build();
        
        when(employeeService.saveEmployee(any(EmployeeReq.class))).thenReturn(employee1);
        mockMvc.perform(post("/api/employee/")
                .content(objectMapper.writeValueAsString(employee))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void saveEmployee_BadRequest() throws Exception {
        var employee = EmployeeReq.builder()
                .name("Vitalii")
                .surname("Rysich")
                .grade(Grade.EXPERT)
                .salary(-10.0)
                .build();

        when(employeeService.saveEmployee(any(EmployeeReq.class))).thenReturn(employee1);
        mockMvc.perform(post("/api/employee/")
                .content(objectMapper.writeValueAsString(employee))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEmployeeById() throws Exception {
        var employee = EmployeeReq.builder()
                .name("Vitalii")
                .surname("Rysich")
                .grade(Grade.EXPERT)
                .salary(10000.0)
                .build();

        when(employeeService.updateEmployee(any(Long.class), any(EmployeeReq.class))).thenReturn(employee1);
        mockMvc.perform(put("/api/employee/{id}", 1)
                .content(objectMapper.writeValueAsString(employee))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void searchEmployeesByOneRequestParams() throws Exception {
        var list = List.of(employee1, employee2);
        when(employeeService.findAllByRequestParams(any(), any())).thenReturn(list);
        mockMvc.perform(get("/api/employee/search")
                .param("name", "Vitalii")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void searchEmployeesByFewParameters() throws Exception {
        var list = List.of(employee1, employee2);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "Vitalii");
        params.add("surname", "Rysich");

        when(employeeService.findAllByRequestParams(any(), any())).thenReturn(list);
        mockMvc.perform(get("/api/employee/search")
                .params(params)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
