package com.rvv.employeeservice.operations;

import com.rvv.employeeservice.model.Employee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification implements Specification<Employee> {

    private List<EmployeeSearchCriteria> searchCriteriaList;

    public EmployeeSpecification() {
        this.searchCriteriaList = new ArrayList<>();
    }

    public void add(EmployeeSearchCriteria criteria) {
        searchCriteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (EmployeeSearchCriteria criteria : searchCriteriaList) {
            var key = criteria.getField();
            var value = criteria.getValue();
            var operation = criteria.getOperation();

            if (operation.equals(SearchOperation.GREATER_THAN)) {
                predicates.add(criteriaBuilder.greaterThan(root.get(key), value.toString()));
            }
            else if (operation.equals(SearchOperation.LESS_THAN)) {
                predicates.add(criteriaBuilder.lessThan(root.get(key), value.toString()));
            }
            else if (operation.equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get(key), criteria.getValue().toString()));
            }
            else if (operation.equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(key), value.toString()));
            }
            else if (operation.equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(criteriaBuilder.notEqual(root.get(key), value));
            }
            else if (operation.equals(SearchOperation.EQUAL)) {
                predicates.add(criteriaBuilder.equal(root.get(key), value));
            }
            else if (operation.equals(SearchOperation.MATCH)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(key)),"%" + value.toString().toLowerCase() + "%"));
            }
            else if (operation.equals(SearchOperation.MATCH_END)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(key)),value.toString().toLowerCase() + "%"));
            }
            else if (operation.equals(SearchOperation.MATCH_START)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(key)),"%" + value.toString().toLowerCase()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
