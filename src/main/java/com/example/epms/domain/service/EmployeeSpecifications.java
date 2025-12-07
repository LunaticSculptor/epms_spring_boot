package com.example.epms.domain.service;

import com.example.epms.domain.Employee;
import com.example.epms.domain.PerformanceReview;
import com.example.epms.domain.model.EmployeeSearchCriteria;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {

    public static Specification<Employee> createSpecification(EmployeeSearchCriteria criteria) {

        Specification<Employee> spec = Specification.where((root, query, cb) -> cb.conjunction());

        if (criteria.getDepartment() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(
                            cb.lower(root.get("department").get("name")),
                            criteria.getDepartment().toLowerCase()
                    )
            );
        }

        if (criteria.getMinScore() != null) {
            spec = spec.and((root, query, cb) -> {
                query.distinct(true);
                var reviews = root.join("reviews", JoinType.INNER);
                return cb.greaterThanOrEqualTo(reviews.get("score"), criteria.getMinScore());
            });
        }

        if (criteria.getMinAverageScore() != null) {
            spec = spec.and((root, query, cb) -> {
                Subquery<Double> sub = query.subquery(Double.class);
                var pr = sub.from(PerformanceReview.class);
                sub.select(cb.avg(pr.get("score")))
                        .where(cb.equal(pr.get("employee"), root));

                return cb.greaterThanOrEqualTo(
                        sub,
                        criteria.getMinAverageScore().doubleValue()
                );
            });
        }

        return spec;
    }
}
