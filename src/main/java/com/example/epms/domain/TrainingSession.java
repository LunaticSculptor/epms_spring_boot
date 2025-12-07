package com.example.epms.domain;

import com.example.epms.domain.enums.TrainingType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "TrainingSession",
       uniqueConstraints = @UniqueConstraint(name = "uk_training_name", columnNames = "name"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TrainingSession {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer trainingId;

  @NotBlank
  @Column(nullable = false, length = 255)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TrainingType trainingType;

  @NotNull
  @Column(nullable = false)
  private Instant createdAt;
}