package com.rvv.employeeservice.dto;

import com.rvv.employeeservice.model.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@Builder
public class EmployeeReq {
    @Schema(name = "name", example = "Jan")
    private String name;
    @Schema(name = "surname", example = "Kowalski")
    private String surname;
    @Schema(name = "grade", example = "NOVICE | ADVANCED_BEGINNER | COMPETENT | PROFICIENT | EXPERT")
    private Grade grade;
    @Min(0)
    @Schema(name = "salary", example = "10000.0")
    private Double salary;
}
