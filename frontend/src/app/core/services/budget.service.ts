import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Budget, BudgetRequest } from '../../shared/models';

@Injectable({
  providedIn: 'root',
})
export class BudgetService {
  private apiUrl = `${environment.apiUrl}/budgets`;

  constructor(private http: HttpClient) {}

  getAllBudgets(): Observable<Budget[]> {
    return this.http.get<Budget[]>(this.apiUrl);
  }

  getActiveBudgets(): Observable<Budget[]> {
    return this.http.get<Budget[]>(`${this.apiUrl}/active`);
  }

  getBudgetById(id: number): Observable<Budget> {
    return this.http.get<Budget>(`${this.apiUrl}/${id}`);
  }

  getBudgetSpent(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}/spent`);
  }

  createBudget(request: BudgetRequest): Observable<Budget> {
    return this.http.post<Budget>(this.apiUrl, request);
  }

  updateBudget(id: number, request: BudgetRequest): Observable<Budget> {
    return this.http.put<Budget>(`${this.apiUrl}/${id}`, request);
  }

  deleteBudget(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
