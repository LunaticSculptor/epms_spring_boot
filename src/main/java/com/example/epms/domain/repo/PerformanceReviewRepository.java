package com.example.epms.domain.repo;

import com.example.epms.domain.Employee;
import com.example.epms.domain.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Integer> {

    long countByEmployee(Employee employee);

    @Query("SELECT AVG(pr.score) FROM PerformanceReview pr WHERE pr.employee = :employee")
    Double getAverageScoreByEmployee(@Param("employee") Employee employee);

    @Query("SELECT COUNT(r) FROM PerformanceReview r WHERE r.createdAt > :since")
    long countReviewsSince(@Param("since") Instant since);

    @Query("SELECT AVG(r.score) FROM PerformanceReview r WHERE r.createdAt > :since")
    Double averageScoreSince(@Param("since") Instant since);

}