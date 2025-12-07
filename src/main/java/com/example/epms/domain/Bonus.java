package com.example.epms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "Bonus")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Bonus {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bonusId;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "employeeId", nullable = false,
              foreignKey = @ForeignKey(name = "fk_bonus_employee"))
  private Employee employee;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "approvedBy", nullable = false,
              foreignKey = @ForeignKey(name = "fk_bonus_approvedBy"))
  private Employee approvedBy;

  @NotNull
  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @NotNull
  @Column(nullable = false)
  private Instant approvedAt;
}