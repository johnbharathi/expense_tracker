package com.expense_tracker.backend.repository;

import com.expense_tracker.backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);

    List<Expense> findByUserIdOrderByExpenseDateDesc(Long userId);

    List<Expense> findByUserIdAndExpenseDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalExpenseByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(e.amount) FROM Expense e" +
            " WHERE e.userId = :userId AND e.categoryId = :categoryId AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalExpenseByCategoryAndDateRange(@Param("userId") Long userId, @Param("categoryId") Long categoryId,
                                                     @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
