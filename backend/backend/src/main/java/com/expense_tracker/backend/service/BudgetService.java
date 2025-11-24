package com.expense_tracker.backend.service;

import com.expense_tracker.backend.dto.request.BudgetRequest;
import com.expense_tracker.backend.entity.Budget;
import com.expense_tracker.backend.entity.User;
import com.expense_tracker.backend.exception.ResourceNotFoundException;
import com.expense_tracker.backend.repository.BudgetRepository;
import com.expense_tracker.backend.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    public List<Budget> getAllBudgets() {
        User currentUser = userService.getCurrentUser();
        return budgetRepository.findByUserId(currentUser.getUserId());
    }

    public List<Budget> getActiveBudgets() {
        User currentUser = userService.getCurrentUser();
        return budgetRepository.findByUserIdAndIsActive(currentUser.getUserId(), true);
    }

    public Budget getBudgetById(Long budgetId) {
        User currentUser = userService.getCurrentUser();
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + budgetId));

        if (!budget.getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Unauthorized access to budget");
        }

        return budget;
    }

    public Budget createBudget(BudgetRequest request) {
        User currentUser = userService.getCurrentUser();

        Budget budget = new Budget();
        budget.setUserId(currentUser.getUserId());
        budget.setCategoryId(request.getCategoryId());
        budget.setBudgetAmount(request.getBudgetAmount());
        budget.setPeriodType(request.getPeriodType());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setAlertThreshold(request.getAlertThreshold() != null ?
                request.getAlertThreshold() : new BigDecimal("80.00"));
        budget.setIsActive(true);

        return budgetRepository.save(budget);
    }

    public Budget updateBudget(Long budgetId, BudgetRequest request) {
        Budget budget = getBudgetById(budgetId);

        budget.setCategoryId(request.getCategoryId());
        budget.setBudgetAmount(request.getBudgetAmount());
        budget.setPeriodType(request.getPeriodType());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setAlertThreshold(request.getAlertThreshold());

        return budgetRepository.save(budget);
    }

    public void deleteBudget(Long budgetId) {
        Budget budget = getBudgetById(budgetId);
        budgetRepository.delete(budget);
    }

    public BigDecimal getBudgetSpent(Long budgetId) {
        Budget budget = getBudgetById(budgetId);
        User currentUser = userService.getCurrentUser();

        BigDecimal spent = expenseRepository.getTotalExpenseByCategoryAndDateRange(
                currentUser.getUserId(),
                budget.getCategoryId(),
                budget.getStartDate(),
                budget.getEndDate()
        );

        return spent != null ? spent : BigDecimal.ZERO;
    }
}
