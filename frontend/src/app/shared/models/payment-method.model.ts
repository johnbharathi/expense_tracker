export interface PaymentMethod {
  paymentMethodId: number;
  userId: number;
  methodName: string;
  methodType:
    | 'CASH'
    | 'CREDIT_CARD'
    | 'DEBIT_CARD'
    | 'UPI'
    | 'NET_BANKING'
    | 'WALLET'
    | 'OTHER';
  isActive?: boolean;
  createdAt?: Date;
}

export interface PaymentMethodRequest {
  methodName: string;
  methodType:
    | 'CASH'
    | 'CREDIT_CARD'
    | 'DEBIT_CARD'
    | 'UPI'
    | 'NET_BANKING'
    | 'WALLET'
    | 'OTHER';
}
