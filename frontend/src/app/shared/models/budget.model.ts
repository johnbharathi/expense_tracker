export interface Budget {
  budgetId: number;
  userId: number;
  categoryId: number;
  budgetAmount: number;
  periodType: 'DAILY' | 'WEEKLY' | 'MONTHLY' | 'YEARLY';
  startDate: string;
  endDate: string;
  alertThreshold?: number;
  isActive?: boolean;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface BudgetRequest {
  categoryId: number;
  budgetAmount: number;
  periodType: 'DAILY' | 'WEEKLY' | 'MONTHLY' | 'YEARLY';
  startDate: string;
  endDate: string;
  alertThreshold?: number;
}
