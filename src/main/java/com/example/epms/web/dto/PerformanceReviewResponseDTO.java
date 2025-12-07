package com.example.epms.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PerformanceReviewResponseDTO {
    Integer reviewId;
    String employeeEmail;
    String reviewerEmail;
    Integer score;
    Instant createdAt;
}
