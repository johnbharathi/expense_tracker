package com.expense_tracker.backend.service;


import com.expense_tracker.backend.dto.request.IncomeRequest;
import com.expense_tracker.backend.entity.Income;
import com.expense_tracker.backend.entity.User;
import com.expense_tracker.backend.exception.ResourceNotFoundException;
import com.expense_tracker.backend.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserService userService;

    public List<Income> getAllIncome() {
        User currentUser = userService.getCurrentUser();
        return incomeRepository.findByUserIdOrderByIncomeDateDesc(currentUser.getUserId());
    }

    public List<Income> getIncomeByDateRange(LocalDate startDate, LocalDate endDate) {
        User currentUser = userService.getCurrentUser();
        return incomeRepository.findByUserIdAndIncomeDateBetween(
                currentUser.getUserId(), startDate, endDate
        );
    }

    public Income getIncomeById(Long incomeId) {
        User currentUser = userService.getCurrentUser();
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found with id: " + incomeId));

        if (!income.getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Unauthorized access to income");
        }

        return income;
    }

    public Income createIncome(IncomeRequest request) {
        User currentUser = userService.getCurrentUser();

        Income income = new Income();
        income.setUserId(currentUser.getUserId());
        income.setCategoryId(request.getCategoryId());
        income.setAmount(request.getAmount());
        income.setIncomeDate(request.getIncomeDate());
        income.setSource(request.getSource());
        income.setDescription(request.getDescription());
        income.setIsRecurring(false);

        return incomeRepository.save(income);
    }

    public Income updateIncome(Long incomeId, IncomeRequest request) {
        Income income = getIncomeById(incomeId);

        income.setCategoryId(request.getCategoryId());
        income.setAmount(request.getAmount());
        income.setIncomeDate(request.getIncomeDate());
        income.setSource(request.getSource());
        income.setDescription(request.getDescription());

        return incomeRepository.save(income);
    }

    public void deleteIncome(Long incomeId) {
        Income income = getIncomeById(incomeId);
        incomeRepository.delete(income);
    }

    public BigDecimal getTotalIncomeByDateRange(LocalDate startDate, LocalDate endDate) {
        User currentUser = userService.getCurrentUser();
        BigDecimal total = incomeRepository.getTotalIncomeByUserAndDateRange(
                currentUser.getUserId(), startDate, endDate
        );
        return total != null ? total : BigDecimal.ZERO;
    }
}
