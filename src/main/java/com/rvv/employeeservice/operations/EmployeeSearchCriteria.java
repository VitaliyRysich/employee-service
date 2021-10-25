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
    GREATER_SALARY("greaterSalary", "salary", SearchOperation.GREATER_THAN_EQUAL),
    LESS_SALARY("lessSalary", "salary", SearchOperation.LESS_THAN_EQUAL),
    MATCH_NAME("matchName", "name", SearchOperation.MATCH),
    MATCH_SURNAME("matchSurname", "surname", SearchOperation.MATCH);

    private final String key;
    private final String field;
    private final SearchOperation operation;
    @Setter
    private Object value;
}
