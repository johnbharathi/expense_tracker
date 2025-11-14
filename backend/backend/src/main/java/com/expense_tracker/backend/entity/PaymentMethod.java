package com.expense_tracker.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long paymentMethodId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "method_name", nullable = false, length = 100)
    private String methodName;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type", nullable = false)
    private MethodType methodType;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public enum MethodType {
        CASH, CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, WALLET, OTHER
    }
}