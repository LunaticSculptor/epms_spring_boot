package com.example.epms.web.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class EmployeePerformanceDTO {
    private Integer employeeId;
    private String name;
    private String department;
    private Double averageScore;
    private BigDecimal salary;
    // Add other fields as needed
}
