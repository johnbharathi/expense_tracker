export interface Income {
  incomeId: number;
  userId: number;
  categoryId: number;
  amount: number;
  incomeDate: string;
  source: string;
  description?: string;
  isRecurring?: boolean;
  recurringId?: number;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface IncomeRequest {
  categoryId: number;
  amount: number;
  incomeDate: string;
  source: string;
  description?: string;
}
