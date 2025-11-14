package com.expense_tracker.backend.repository;

import com.expense_tracker.backend.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserId(Long userId);

    List<Budget> findByUserIdAndIsActive(Long userId, Boolean isActive);

    List<Budget> findByUserIdAndCategoryId(Long userId, Long categoryId);

}
