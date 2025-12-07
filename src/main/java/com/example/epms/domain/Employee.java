package com.example.epms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "Employee", uniqueConstraints = @UniqueConstraint(name = "uk_employee_userId", columnNames = "userId"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer employeeId;

  @NotNull
  @OneToOne(optional = false)
  @JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(name = "fk_employee_user"))
  private User user;

  @NotBlank
  @Column(nullable = false, length = 255)
  private String name;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "departmentId", nullable = false, foreignKey = @ForeignKey(name = "fk_employee_department"))
  private Department department;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "managerId", foreignKey = @ForeignKey(name = "fk_employee_manager"))
  private Employee manager;

  @OneToMany(mappedBy = "manager")
  @JsonIgnore
  private Set<Employee> reports;

  @OneToMany(mappedBy = "employee")
  @JsonIgnore
  private Set<PerformanceReview> reviews;

  @OneToMany(mappedBy = "employee")
  @JsonIgnore
  private Set<com.example.epms.domain.link.EmployeeProject> projects;

  @NotNull
  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal salary;
}