package com.example.epms.web;

import com.example.epms.domain.Bonus;
import com.example.epms.domain.service.BonusService;
import com.example.epms.web.dto.BonusApprovalRequest;
import com.example.epms.web.dto.BonusResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bonus")
@RequiredArgsConstructor
public class BonusController {

  private final BonusService bonusService;

  @PostMapping("/approve")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<BonusResponseDTO> approveBonus(@Valid @RequestBody BonusApprovalRequest request,
      Authentication authentication) {
    String adminEmail = authentication.getName();
    BonusResponseDTO bonus = bonusService.approveBonus(request.getEmployeeId(), request.getAmount(), adminEmail);
    return ResponseEntity.ok(bonus);
  }
}