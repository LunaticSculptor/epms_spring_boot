package com.example.epms.web.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class OverdueTrainingDTO {
    private Integer employeeId;
    private String employeeName;
    private String trainingName;
    private String trainingType;
    private LocalDate dueDate;
}
