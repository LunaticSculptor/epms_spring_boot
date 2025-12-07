package com.example.epms.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDashboardResponse {
    private DashboardBucket weekly;
    private DashboardBucket monthly;
    private DashboardBucket yearly;
    private long totalEmployees;
}
