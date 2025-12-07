package com.example.epms.domain.link;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeTrainingId implements Serializable {
  private Integer employeeId;
  private Integer trainingId;

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EmployeeTrainingId that)) return false;
    return java.util.Objects.equals(employeeId, that.employeeId)
        && java.util.Objects.equals(trainingId, that.trainingId);
  }
  @Override public int hashCode() { return Objects.hash(employeeId, trainingId); }
}