import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PaymentMethod, PaymentMethodRequest } from '../../shared/models';

@Injectable({
  providedIn: 'root',
})
export class PaymentMethodService {
  private apiUrl = `${environment.apiUrl}/payment-methods`;

  constructor(private http: HttpClient) {}

  getAllPaymentMethods(): Observable<PaymentMethod[]> {
    return this.http.get<PaymentMethod[]>(this.apiUrl);
  }

  getActivePaymentMethods(): Observable<PaymentMethod[]> {
    return this.http.get<PaymentMethod[]>(`${this.apiUrl}/active`);
  }

  getPaymentMethodById(id: number): Observable<PaymentMethod> {
    return this.http.get<PaymentMethod>(`${this.apiUrl}/${id}`);
  }

  createPaymentMethod(
    request: PaymentMethodRequest
  ): Observable<PaymentMethod> {
    return this.http.post<PaymentMethod>(this.apiUrl, request);
  }

  updatePaymentMethod(
    id: number,
    request: PaymentMethodRequest
  ): Observable<PaymentMethod> {
    return this.http.put<PaymentMethod>(`${this.apiUrl}/${id}`, request);
  }

  deletePaymentMethod(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
