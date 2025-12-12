export interface Expense {
  expenseId: number;
  userId: number;
  categoryId: number;
  paymentMethodId?: number;
  amount: number;
  expenseDate: string;
  description?: string;
  notes?: string;
  receiptUrl?: string;
  isRecurring?: boolean;
  recurringId?: number;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface ExpenseRequest {
  categoryId: number;
  paymentMethodId?: number;
  amount: number;
  expenseDate: string;
  description?: string;
  notes?: string;
  receiptUrl?: string;
}
