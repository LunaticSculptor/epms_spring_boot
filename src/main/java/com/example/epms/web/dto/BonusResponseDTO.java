package com.example.epms.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class BonusResponseDTO {
    Integer bonusId;
    String employeeEmail;
    String approvedByEmail;
    BigDecimal amount;
    Instant approvedAt;
}
