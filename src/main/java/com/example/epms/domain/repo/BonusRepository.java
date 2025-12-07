package com.example.epms.domain.repo;
import com.example.epms.domain.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Integer>{

    @Query("SELECT COUNT(b) FROM Bonus b WHERE b.approvedAt > :since")
    long countBonusesSince(@Param("since") Instant since);
}