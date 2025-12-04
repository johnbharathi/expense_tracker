package com.expense_tracker.backend.dto.request;

import com.expense_tracker.backend.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentMethodRequest {

    public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public PaymentMethod.MethodType getMethodType() {
		return methodType;
	}

	public void setMethodType(PaymentMethod.MethodType methodType) {
		this.methodType = methodType;
	}

	@NotBlank(message = "Method name is required")
    private String methodName;

    @NotNull(message = "Method type is required")
    private PaymentMethod.MethodType methodType;
}