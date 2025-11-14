package com.expense_tracker.backend.repository;

import com.expense_tracker.backend.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUserId(Long userId);

    List<Income> findByUserIdOrderByIncomeDateDesc(Long userId);

    List<Income> findByUserIdAndIncomeDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.userId = :userId AND i.incomeDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalIncomeByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
