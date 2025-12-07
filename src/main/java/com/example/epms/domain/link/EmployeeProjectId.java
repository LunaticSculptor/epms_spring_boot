package com.example.epms.domain.link;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeProjectId implements Serializable {
  private Integer employeeId;
  private Integer projectId;

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EmployeeProjectId that)) return false;
    return java.util.Objects.equals(employeeId, that.employeeId)
        && java.util.Objects.equals(projectId, that.projectId);
  }
  @Override public int hashCode() { return Objects.hash(employeeId, projectId); }
}