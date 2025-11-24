package com.expense_tracker.backend.controller;

import com.expense_tracker.backend.dto.request.IncomeRequest;
import com.expense_tracker.backend.entity.Income;
import com.expense_tracker.backend.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/income")
@CrossOrigin(origins = "*")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncome() {
        List<Income> incomeList = incomeService.getAllIncome();
        return ResponseEntity.ok(incomeList);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Income>> getIncomeByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Income> incomeList = incomeService.getIncomeByDateRange(startDate, endDate);
        return ResponseEntity.ok(incomeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable Long id) {
        Income income = incomeService.getIncomeById(id);
        return ResponseEntity.ok(income);
    }

    @PostMapping
    public ResponseEntity<Income> createIncome(@Valid @RequestBody IncomeRequest request) {
        Income income = incomeService.createIncome(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(income);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id,
                                               @Valid @RequestBody IncomeRequest request) {
        Income income = incomeService.updateIncome(id, request);
        return ResponseEntity.ok(income);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalIncome(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal total = incomeService.getTotalIncomeByDateRange(startDate, endDate);
        return ResponseEntity.ok(total);
    }
}