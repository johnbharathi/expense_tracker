package com.expense_tracker.backend.service;

import com.expense_tracker.backend.dto.request.PaymentMethodRequest;
import com.expense_tracker.backend.entity.PaymentMethod;
import com.expense_tracker.backend.entity.User;
import com.expense_tracker.backend.exception.ResourceNotFoundException;
import com.expense_tracker.backend.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private UserService userService;

    public List<PaymentMethod> getAllPaymentMethods() {
        User currentUser = userService.getCurrentUser();
        return paymentMethodRepository.findByUserId(currentUser.getUserId());
    }

    public List<PaymentMethod> getActivePaymentMethods() {
        User currentUser = userService.getCurrentUser();
        return paymentMethodRepository.findByUserIdAndIsActive(currentUser.getUserId(), true);
    }

    public PaymentMethod getPaymentMethodById(Long paymentMethodId) {
        User currentUser = userService.getCurrentUser();
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + paymentMethodId));

        if (!paymentMethod.getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Unauthorized access to payment method");
        }

        return paymentMethod;
    }

    public PaymentMethod createPaymentMethod(PaymentMethodRequest request) {
        User currentUser = userService.getCurrentUser();

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setUserId(currentUser.getUserId());
        paymentMethod.setMethodName(request.getMethodName());
        paymentMethod.setMethodType(request.getMethodType());
        paymentMethod.setIsActive(true);

        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod updatePaymentMethod(Long paymentMethodId, PaymentMethodRequest request) {
        PaymentMethod paymentMethod = getPaymentMethodById(paymentMethodId);

        paymentMethod.setMethodName(request.getMethodName());
        paymentMethod.setMethodType(request.getMethodType());

        return paymentMethodRepository.save(paymentMethod);
    }

    public void deletePaymentMethod(Long paymentMethodId) {
        PaymentMethod paymentMethod = getPaymentMethodById(paymentMethodId);
        paymentMethodRepository.delete(paymentMethod);
    }
}
