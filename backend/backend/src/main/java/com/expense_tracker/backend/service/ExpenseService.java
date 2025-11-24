package com.expense_tracker.backend.service;

    import com.expense_tracker.backend.dto.request.ExpenseRequest;
import com.expense_tracker.backend.entity.Expense;
import com.expense_tracker.backend.entity.User;
import com.expense_tracker.backend.exception.ResourceNotFoundException;
import com.expense_tracker.backend.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    public List<Expense> getAllExpenses() {
        User currentUser = userService.getCurrentUser();
        return expenseRepository.findByUserIdOrderByExpenseDateDesc(currentUser.getUserId());
    }

    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        User currentUser = userService.getCurrentUser();
        return expenseRepository.findByUserIdAndExpenseDateBetween(
                currentUser.getUserId(), startDate, endDate
        );
    }

    public List<Expense> getExpensesByCategory(Long categoryId) {
        User currentUser = userService.getCurrentUser();
        return expenseRepository.findByUserIdAndCategoryId(currentUser.getUserId(), categoryId);
    }

    public Expense getExpenseById(Long expenseId) {
        User currentUser = userService.getCurrentUser();
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));

        if (!expense.getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Unauthorized access to expense");
        }

        return expense;
    }

    public Expense createExpense(ExpenseRequest request) {
        User currentUser = userService.getCurrentUser();

        Expense expense = new Expense();
        expense.setUserId(currentUser.getUserId());
        expense.setCategoryId(request.getCategoryId());
        expense.setPaymentMethodId(request.getPaymentMethodId());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());
        expense.setNotes(request.getNotes());
        expense.setReceiptUrl(request.getReceiptUrl());
        expense.setIsRecurring(false);

        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long expenseId, ExpenseRequest request) {
        Expense expense = getExpenseById(expenseId);

        expense.setCategoryId(request.getCategoryId());
        expense.setPaymentMethodId(request.getPaymentMethodId());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());
        expense.setNotes(request.getNotes());
        expense.setReceiptUrl(request.getReceiptUrl());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long expenseId) {
        Expense expense = getExpenseById(expenseId);
        expenseRepository.delete(expense);
    }

    public BigDecimal getTotalExpenseByDateRange(LocalDate startDate, LocalDate endDate) {
        User currentUser = userService.getCurrentUser();
        BigDecimal total = expenseRepository.getTotalExpenseByUserAndDateRange(
                currentUser.getUserId(), startDate, endDate
        );
        return total != null ? total : BigDecimal.ZERO;
    }
}
