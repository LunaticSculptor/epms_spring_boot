package com.example.epms.domain.service;

import com.example.epms.domain.Bonus;
import com.example.epms.domain.Employee;
import com.example.epms.domain.repo.BonusRepository;
import com.example.epms.domain.repo.EmployeeRepository;
import com.example.epms.domain.repo.PerformanceReviewRepository;
import com.example.epms.web.dto.BonusResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BonusService {

    private final BonusRepository bonusRepository;
    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewRepository reviewRepository;

    @Transactional
    public BonusResponseDTO approveBonus(Integer employeeId, BigDecimal amount, String adminEmail) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));

        long reviewCount = reviewRepository.countByEmployee(employee);
        if (reviewCount < 3) {
            throw new IllegalArgumentException("Employee must have at least 3 approved reviews."); // 400 Bad Request
        }

        Double avgScore = reviewRepository.getAverageScoreByEmployee(employee);
        if (avgScore == null)
            avgScore = 0.0;

        // bonus = baseSalary * (avgScore / 10)
        BigDecimal expectedBonus = employee.getSalary()
                .multiply(BigDecimal.valueOf(avgScore))
                .divide(BigDecimal.TEN, 4, RoundingMode.HALF_UP);

        // Tolerance check: +/- 1%
        BigDecimal tolerance = expectedBonus.multiply(BigDecimal.valueOf(0.01));
        BigDecimal minBonus = expectedBonus.subtract(tolerance);
        BigDecimal maxBonus = expectedBonus.add(tolerance);

        System.out.println("Bonus Range between "+minBonus+" and "+maxBonus);

        if (amount.compareTo(minBonus) < 0 || amount.compareTo(maxBonus) > 0) {
            throw new IllegalArgumentException("Bonus amount is not proportional to performance.");
        }

        Employee admin = employeeRepository.findByUser_Email(adminEmail)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found."));

        Bonus bonus = Bonus.builder()
                .employee(employee)
                .approvedBy(admin)
                .amount(amount)
                .approvedAt(Instant.now())
                .build();

        Bonus saved = bonusRepository.save(bonus);

        return new BonusResponseDTO(
                saved.getBonusId(),
                saved.getEmployee().getUser().getEmail(),
                saved.getApprovedBy().getUser().getEmail(),
                saved.getAmount(),
                saved.getApprovedAt()
        );
    }
}
