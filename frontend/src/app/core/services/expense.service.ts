import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Expense, ExpenseRequest } from '../../shared/models';

@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  private apiUrl = `${environment.apiUrl}/expenses`;

  constructor(private http: HttpClient) {}

  getAllExpenses(): Observable<Expense[]> {
    return this.http.get<Expense[]>(this.apiUrl);
  }

  getExpensesByDateRange(
    startDate: string,
    endDate: string
  ): Observable<Expense[]> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<Expense[]>(`${this.apiUrl}/date-range`, { params });
  }

  getExpensesByCategory(categoryId: number): Observable<Expense[]> {
    return this.http.get<Expense[]>(`${this.apiUrl}/category/${categoryId}`);
  }

  getExpenseById(id: number): Observable<Expense> {
    return this.http.get<Expense>(`${this.apiUrl}/${id}`);
  }

  createExpense(request: ExpenseRequest): Observable<Expense> {
    return this.http.post<Expense>(this.apiUrl, request);
  }

  updateExpense(id: number, request: ExpenseRequest): Observable<Expense> {
    return this.http.put<Expense>(`${this.apiUrl}/${id}`, request);
  }

  deleteExpense(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getTotalExpense(startDate: string, endDate: string): Observable<number> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<number>(`${this.apiUrl}/total`, { params });
  }
}
