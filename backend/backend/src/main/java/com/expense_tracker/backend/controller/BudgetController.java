package com.expense_tracker.backend.controller;

import com.expense_tracker.backend.dto.request.BudgetRequest;
import com.expense_tracker.backend.entity.Budget;
import com.expense_tracker.backend.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Budget>> getActiveBudgets() {
        List<Budget> budgets = budgetService.getActiveBudgets();
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        Budget budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    @GetMapping("/{id}/spent")
    public ResponseEntity<Map<String, BigDecimal>> getBudgetSpent(@PathVariable Long id) {
        Budget budget = budgetService.getBudgetById(id);
        BigDecimal spent = budgetService.getBudgetSpent(id);
        BigDecimal remaining = budget.getBudgetAmount().subtract(spent);
        BigDecimal percentage = spent.divide(budget.getBudgetAmount(), 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));

        Map<String, BigDecimal> response = new HashMap<>();
        response.put("budgetAmount", budget.getBudgetAmount());
        response.put("spent", spent);
        response.put("remaining", remaining);
        response.put("percentage", percentage);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@Valid @RequestBody BudgetRequest request) {
        Budget budget = budgetService.createBudget(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(budget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id,
                                               @Valid @RequestBody BudgetRequest request) {
        Budget budget = budgetService.updateBudget(id, request);
        return ResponseEntity.ok(budget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}