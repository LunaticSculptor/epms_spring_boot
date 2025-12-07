package com.example.epms.domain.service;

import com.example.epms.domain.Employee;
import com.example.epms.domain.PerformanceReview;
import com.example.epms.domain.model.EmployeeSearchCriteria;
import com.example.epms.domain.repo.EmployeeRepository;
import com.example.epms.domain.repo.PerformanceReviewRepository;
import com.example.epms.web.dto.EmployeePerformanceDTO;
import com.example.epms.web.dto.EmployeePerformanceResponse;
import com.example.epms.web.dto.PerformanceReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceReviewRepository reviewRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public PerformanceReviewResponseDTO submitReview(Integer employeeId, Integer score, String reviewerEmail) {
        if (score < 1 || score > 10) {
            throw new IllegalArgumentException("Score must be between 1 and 10.");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));

        Employee reviewer = employeeRepository.findByUser_Email(reviewerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Reviewer not found: " + reviewerEmail));

        PerformanceReview review = PerformanceReview.builder()
                .employee(employee)
                .reviewer(reviewer)
                .score(score)
                .createdAt(Instant.now())
                .build();

        PerformanceReview saved = reviewRepository.save(review);

        return new PerformanceReviewResponseDTO(
                saved.getReviewId(),
                saved.getEmployee().getUser().getEmail(),
                saved.getReviewer().getUser().getEmail(),
                saved.getScore(),
                saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public EmployeePerformanceResponse getEmployeePerformance(
            EmployeeSearchCriteria criteria,
            Pageable pageable) {
        System.out.println(criteria);
        Specification<Employee> spec = EmployeeSpecifications
                .createSpecification(criteria);
        Page<Employee> page = employeeRepository.findAll(spec, pageable);

        List<EmployeePerformanceDTO> dtos = page.getContent().stream().map(emp -> {
            Double avgScore = reviewRepository.getAverageScoreByEmployee(emp);
            return EmployeePerformanceDTO.builder()
                    .employeeId(emp.getEmployeeId())
                    .name(emp.getName())
                    .department(emp.getDepartment().getName())
                    .averageScore(avgScore)
                    .salary(emp.getSalary())
                    .build();
        }).toList();

        List<String> commonProjects = getStrings(criteria, dtos, page);

        return EmployeePerformanceResponse.builder()
                .data(dtos)
                .totalPages(page.getTotalPages())
                .totalRecords(page.getTotalElements())
                .commonProjects(commonProjects)
                .build();
    }

    private static List<String> getStrings(EmployeeSearchCriteria criteria, List<EmployeePerformanceDTO> dtos, Page<Employee> page) {
        List<String> commonProjects = new ArrayList<>();
        if (Boolean.TRUE.equals(criteria.getIncludeCollaborations()) && !dtos.isEmpty()) {
            Map<String, Integer> projectCounts = new HashMap<>();
            for (Employee emp : page.getContent()) {
                if (emp.getProjects() != null) {
                    for (com.example.epms.domain.link.EmployeeProject ep : emp.getProjects()) {
                        String projName = ep.getProject().getName();
                        projectCounts.put(projName, projectCounts.getOrDefault(projName, 0) + 1);
                    }
                }
            }
            projectCounts.forEach((name, count) -> {
                if (count >= 2)
                    commonProjects.add(name);
            });
        }
        return commonProjects;
    }
}
