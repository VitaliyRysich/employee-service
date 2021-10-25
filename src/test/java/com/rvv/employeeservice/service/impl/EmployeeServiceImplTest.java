package com.rvv.employeeservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvv.employeeservice.dto.EmployeeReq;
import com.rvv.employeeservice.exception.EmployeeNotFoundException;
import com.rvv.employeeservice.model.Employee;
import com.rvv.employeeservice.model.Grade;
import com.rvv.employeeservice.repository.EmployeeRepository;
import com.rvv.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired EmployeeService employeeService;

    private MockMvc mockMvc;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private ModelMapper modelMapper;

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
    void findEmployeeById() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee1));
        Employee employee = employeeService.findEmployeeById(1L);
        assertNotNull(employee);
    }

    @Test
    void findEmployeeByIdShouldThrowException() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeById(1L));
    }

    @Test
    void deleteEmployeeById() {
        when(employeeRepository.existsById(any(Long.class))).thenReturn(true);
        employeeService.deleteEmployeeById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEmployeeByIdShouldThrowException() {
        when(employeeRepository.existsById(any(Long.class))).thenReturn(false);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployeeById(1L));
    }

    @Test
    void saveEmployee() {
        var employee = EmployeeReq.builder()
                .name("Vitalii")
                .surname("Rysich")
                .grade(Grade.EXPERT)
                .salary(10000.0)
                .build();

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        when(modelMapper.map(any(), any())).thenReturn(employee1);

        employeeService.saveEmployee(employee);

        assertThat(employee).isNotNull();
    }

    @Test
    void findAllByRequestParams() {

    }
}
