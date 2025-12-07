package com.example.epms.domain.service;

import com.example.epms.domain.enums.TrainingType;
import com.example.epms.domain.link.EmployeeTraining;
import com.example.epms.domain.repo.EmployeeTrainingRepository;
import com.example.epms.web.dto.OverdueTrainingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingReportService {

    private final EmployeeTrainingRepository trainingRepository;

    @Transactional(readOnly = true)
    public List<OverdueTrainingDTO> getOverdueTrainings(LocalDate startDate, LocalDate endDate, String typeStr) {
        TrainingType type = null;
        if (typeStr != null && !typeStr.isEmpty()) {
            try {
                type = TrainingType.valueOf(typeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Should probably throw a specific exception or ignore?
                // Requirement says "trainingType must match one of the valid session types".
                // So strict validation.
                throw new IllegalArgumentException("Invalid training type: " + typeStr);
            }
        }

        List<EmployeeTraining> trainings = trainingRepository.findOverdueTrainings(startDate, endDate, type);

        return trainings.stream().map(et -> OverdueTrainingDTO.builder()
                .employeeId(et.getEmployee().getEmployeeId())
                .employeeName(et.getEmployee().getName())
                .trainingName(et.getTrainingSession().getName())
                .trainingType(et.getTrainingSession().getTrainingType().name())
                .dueDate(et.getDueDate())
                .build())
                .collect(Collectors.toList());
    }
}
