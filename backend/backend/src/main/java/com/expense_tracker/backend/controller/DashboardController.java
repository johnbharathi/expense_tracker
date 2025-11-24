package com.expense_tracker.backend.controller;

import com.expense_tracker.backend.dto.respose.DashboardResponse;
import com.expense_tracker.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboardData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        DashboardResponse response = dashboardService.getDashboardData(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current-month")
    public ResponseEntity<DashboardResponse> getCurrentMonthDashboard() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        DashboardResponse response = dashboardService.getDashboardData(startDate, endDate);
        return ResponseEntity.ok(response);
    }
}