package com.example.epms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Project",
       uniqueConstraints = @UniqueConstraint(name = "uk_project_name", columnNames = "name"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Project {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer projectId;

  @NotBlank
  @Column(nullable = false, length = 255)
  private String name;

  @NotNull
  @Column(nullable = false)
  private LocalDate startDate;

  @Column
  private LocalDate endDate;
}