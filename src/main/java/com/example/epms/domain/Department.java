package com.example.epms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "Department",
       uniqueConstraints = @UniqueConstraint(name = "uk_department_name", columnNames = "name"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Department {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer departmentId;

  @NotBlank
  @Column(nullable = false, length = 255)
  private String name;
}