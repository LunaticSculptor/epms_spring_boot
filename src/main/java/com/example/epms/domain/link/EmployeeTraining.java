package com.example.epms.domain.link;

import com.example.epms.domain.Employee;
import com.example.epms.domain.TrainingSession;
import com.example.epms.domain.enums.TrainingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "EmployeeTraining",
       uniqueConstraints = @UniqueConstraint(name = "uk_emp_training", columnNames = {"employeeId","trainingId"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeTraining {

  @EmbeddedId
  private EmployeeTrainingId id;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @MapsId("employeeId")
  @JoinColumn(name = "employeeId", nullable = false,
              foreignKey = @ForeignKey(name = "fk_emp_training_employee"))
  private Employee employee;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @MapsId("trainingId")
  @JoinColumn(name = "trainingId", nullable = false,
              foreignKey = @ForeignKey(name = "fk_emp_training_training"))
  private TrainingSession trainingSession;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TrainingStatus status;

  @NotNull
  @Column(nullable = false)
  private LocalDate dueDate;
}