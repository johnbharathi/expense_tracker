export interface Category {
  categoryId: number;
  userId: number;
  categoryName: string;
  categoryType: 'EXPENSE' | 'INCOME';
  icon?: string;
  color?: string;
  isDefault?: boolean;
  createdAt?: Date;
}

export interface CategoryRequest {
  categoryName: string;
  categoryType: 'EXPENSE' | 'INCOME';
  icon?: string;
  color?: string;
}
