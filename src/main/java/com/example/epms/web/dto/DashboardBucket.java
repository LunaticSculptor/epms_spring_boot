package com.example.epms.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardBucket {
//    private long newEmployees;
    private long reviewsSubmitted;
    private long bonusesApproved;
    private double averageScore;
    private double trainingCompletionRate;
}
