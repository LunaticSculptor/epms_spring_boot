package com.example.epms.domain.link;

import com.example.epms.domain.Employee;
import com.example.epms.domain.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "EmployeeProject",
       uniqueConstraints = @UniqueConstraint(name = "uk_emp_proj", columnNames = {"employeeId","projectId"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeProject {

  @EmbeddedId
  private EmployeeProjectId id;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @MapsId("employeeId")
  @JoinColumn(name = "employeeId", nullable = false,
              foreignKey = @ForeignKey(name = "fk_emp_proj_employee"))
  private Employee employee;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @MapsId("projectId")
  @JoinColumn(name = "projectId", nullable = false,
              foreignKey = @ForeignKey(name = "fk_emp_proj_project"))
  private Project project;

  @NotNull
  @Column(nullable = false)
  private LocalDate assignedDate;
}