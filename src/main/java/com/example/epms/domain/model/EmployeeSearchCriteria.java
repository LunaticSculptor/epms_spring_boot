package com.example.epms.domain.model;

import lombok.Data;

@Data
public class EmployeeSearchCriteria {
    private String department;
    private Integer minScore;
    private Integer minAverageScore;
    private Boolean includeCollaborations;
}
