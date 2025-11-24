package com.expense_tracker.backend.service;


import com.expense_tracker.backend.dto.respose.DashboardResponse;
import com.expense_tracker.backend.entity.User;
import com.expense_tracker.backend.repository.ExpenseRepository;
import com.expense_tracker.backend.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DashboardService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserService userService;

    public DashboardResponse getDashboardData(LocalDate startDate, LocalDate endDate) {
        User currentUser = userService.getCurrentUser();

        BigDecimal totalIncome = incomeRepository.getTotalIncomeByUserAndDateRange(currentUser.getUserId(), startDate, endDate);

        BigDecimal totalExpense = expenseRepository.getTotalExpenseByUserAndDateRange(currentUser.getUserId(), startDate, endDate);

        totalIncome = totalIncome != null ? totalIncome : BigDecimal.ZERO;
        totalExpense = totalExpense != null ? totalExpense : BigDecimal.ZERO;

        BigDecimal balance = totalIncome.subtract(totalExpense);

        int expenseCount = expenseRepository.findByUserIdAndExpenseDateBetween(currentUser.getUserId(), startDate, endDate).size();

        int incomeCount = incomeRepository.findByUserIdAndIncomeDateBetween(currentUser.getUserId(), startDate, endDate).size();

        return new DashboardResponse(totalIncome, totalExpense, balance, expenseCount, incomeCount);
    }
}
