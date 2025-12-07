package com.example.epms.web;

import com.example.epms.domain.service.PerformanceService;
import com.example.epms.web.dto.PerformanceReviewResponseDTO;
import com.example.epms.web.dto.ReviewRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final PerformanceService performanceService;

  @PostMapping
  @PreAuthorize("hasRole('HR_MANAGER')")
  public ResponseEntity<PerformanceReviewResponseDTO> submitReview(@Valid @RequestBody ReviewRequest request,
                                                                   Authentication authentication) {
    String reviewerEmail = authentication.getName();
    PerformanceReviewResponseDTO review = performanceService.submitReview(request.getEmployeeId(), request.getScore(),
        reviewerEmail);
    return ResponseEntity.ok(review);
  }
}