package com.rvv.employeeservice;

import com.rvv.employeeservice.model.Employee;
import com.rvv.employeeservice.model.Grade;
import com.rvv.employeeservice.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employees API", version = "3.0", description = "Employees Information"))
public class EmployeeServiceApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }


    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Employee employee1 = Employee.builder()
                .name("Vitalii")
                .surname("Rysich")
                .grade(Grade.EXPERT)
                .salary(10000.0)
                .build();

        Employee employee2 = Employee.builder()
                .name("Jan")
                .surname("Kowalski")
                .grade(Grade.NOVICE)
                .salary(8000.0)
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
    }
}
