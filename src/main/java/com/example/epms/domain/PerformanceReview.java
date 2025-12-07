package com.example.epms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "PerformanceReview")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PerformanceReview {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reviewId;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "employeeId", nullable = false,
              foreignKey = @ForeignKey(name = "fk_review_employee"))
  private Employee employee;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "reviewerId", nullable = false,
              foreignKey = @ForeignKey(name = "fk_review_reviewer"))
  private Employee reviewer;

  @NotNull @Min(1) @Max(10)
  @Column(nullable = false)
  private Integer score;

  @NotNull
  @Column(nullable = false)
  private Instant createdAt;
}