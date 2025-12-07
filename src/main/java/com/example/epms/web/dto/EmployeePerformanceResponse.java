package com.example.epms.web.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class EmployeePerformanceResponse {
    private List<EmployeePerformanceDTO> data;
    private int totalPages;
    private long totalRecords;
    private List<String> commonProjects;
}
