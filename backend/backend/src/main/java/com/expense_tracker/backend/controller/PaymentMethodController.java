package com.expense_tracker.backend.controller;

import com.expense_tracker.backend.dto.request.PaymentMethodRequest;
import com.expense_tracker.backend.entity.PaymentMethod;
import com.expense_tracker.backend.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
@CrossOrigin(origins = "*")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PaymentMethod>> getActivePaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getActivePaymentMethods();
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable Long id) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
        return ResponseEntity.ok(paymentMethod);
    }

    @PostMapping
    public ResponseEntity<PaymentMethod> createPaymentMethod(@Valid @RequestBody PaymentMethodRequest request) {
        PaymentMethod paymentMethod = paymentMethodService.createPaymentMethod(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable Long id,
                                                             @Valid @RequestBody PaymentMethodRequest request) {
        PaymentMethod paymentMethod = paymentMethodService.updatePaymentMethod(id, request);
        return ResponseEntity.ok(paymentMethod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }
}