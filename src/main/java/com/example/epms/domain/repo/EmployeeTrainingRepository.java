package com.example.epms.domain.repo;

import com.example.epms.domain.link.EmployeeTraining;
import com.example.epms.domain.link.EmployeeTrainingId;
import com.example.epms.domain.enums.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeTrainingRepository extends JpaRepository<EmployeeTraining, EmployeeTrainingId> {

    @Query("SELECT et FROM EmployeeTraining et " +
            "JOIN FETCH et.employee e " +
            "JOIN FETCH et.trainingSession ts " +
            "WHERE et.status <> 'COMPLETED' " +
            "AND et.dueDate BETWEEN :startDate AND :endDate " +
            "AND (:trainingType IS NULL OR ts.trainingType = :trainingType)")
    List<EmployeeTraining> findOverdueTrainings(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("trainingType") TrainingType trainingType);

    @Query("SELECT COUNT(t) FROM EmployeeTraining t WHERE t.dueDate > :since")
    long countDueTrainingsSince(@Param("since") LocalDate since);

    @Query("SELECT COUNT(t) FROM EmployeeTraining t WHERE t.dueDate > :since AND t.status = com.example.epms.domain.enums.TrainingStatus.COMPLETED")
    long countCompletedTrainingsSince(@Param("since") LocalDate since);
}