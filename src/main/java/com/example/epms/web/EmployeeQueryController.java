package com.example.epms.web;

import com.example.epms.domain.model.EmployeeSearchCriteria;
import com.example.epms.domain.service.PerformanceService;
import com.example.epms.web.dto.EmployeePerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeQueryController {

  private final PerformanceService performanceService;

  @GetMapping("/performance")
  public ResponseEntity<EmployeePerformanceResponse> getEmployeePerformance(
      @RequestParam(required = false) String department,
      @RequestParam(required = false) Integer minScore,
      @RequestParam(required = false) Integer minAverageScore,
      @RequestParam(required = false) Boolean includeCollaborations,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    EmployeeSearchCriteria criteria = new EmployeeSearchCriteria();
    criteria.setDepartment(department);
    criteria.setMinScore(minScore);
    criteria.setMinAverageScore(minAverageScore);
    criteria.setIncludeCollaborations(includeCollaborations);

    Pageable pageable = PageRequest.of(page, size);
    EmployeePerformanceResponse response = performanceService.getEmployeePerformance(criteria, pageable);
    return ResponseEntity.ok(response);
  }
}