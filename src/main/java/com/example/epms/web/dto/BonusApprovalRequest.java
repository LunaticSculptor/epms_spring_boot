package com.example.epms.web.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BonusApprovalRequest {
    private Integer employeeId;
    private BigDecimal amount;
}
