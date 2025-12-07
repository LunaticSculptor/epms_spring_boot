package com.example.epms.web;

import com.example.epms.domain.service.TrainingReportService;
import com.example.epms.web.dto.OverdueTrainingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
public class TrainingController {

  private final TrainingReportService trainingReportService;

  @GetMapping("/overdue")
  @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
  public ResponseEntity<List<OverdueTrainingDTO>> getOverdueTrainings(
      @RequestParam LocalDate startDate,
      @RequestParam LocalDate endDate,
      @RequestParam(required = false) String trainingType) {
    List<OverdueTrainingDTO> report = trainingReportService.getOverdueTrainings(startDate, endDate, trainingType);
    return ResponseEntity.ok(report);
  }
}