package com.expense_tracker.backend.repository;

import com.expense_tracker.backend.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByUserId(Long userId);

    List<PaymentMethod> findByUserIdAndIsActive(Long userId, Boolean isActive);
}
