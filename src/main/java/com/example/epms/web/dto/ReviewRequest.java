package com.example.epms.web.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Integer employeeId;
    private Integer score;
}
