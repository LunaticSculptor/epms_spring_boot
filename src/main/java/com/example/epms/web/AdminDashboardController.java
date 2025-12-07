package com.example.epms.web;

import com.example.epms.domain.service.AdminDashboardService;
import com.example.epms.web.dto.AdminDashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

  private final AdminDashboardService dashboardService;

  @GetMapping("/dashboard")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<AdminDashboardResponse> getDashboard() {
    AdminDashboardResponse stats = dashboardService.getDashboardStats();
    return ResponseEntity.ok(stats);
  }
}