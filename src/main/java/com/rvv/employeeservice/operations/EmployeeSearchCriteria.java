package com.rvv.employeeservice.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public enum EmployeeSearchCriteria {
    NAME("name", "name", SearchOperation.EQUAL),
    SURNAME("surname","surname" , SearchOperation.EQUAL),
    GRADE("grade","grade", SearchOperation.EQUAL),
    SALARY("salary", "salary", SearchOperation.EQUAL),
    MIN_SALARY("minSalary", "salary", SearchOperation.GREATER_THAN_EQUAL),
    MAX_SALARY("maxSalary", "salary", SearchOperation.LESS_THAN_EQUAL);

    private final String key;
    private final String field;
    private final SearchOperation operation;
    @Setter
    private Object value;
}
