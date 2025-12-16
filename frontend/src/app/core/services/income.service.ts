import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Income, IncomeRequest } from '../../shared/models';

@Injectable({
  providedIn: 'root',
})
export class IncomeService {
  private apiUrl = `${environment.apiUrl}/income`;

  constructor(private http: HttpClient) {}

  getAllIncome(): Observable<Income[]> {
    return this.http.get<Income[]>(this.apiUrl);
  }

  getIncomeByDateRange(
    startDate: string,
    endDate: string
  ): Observable<Income[]> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<Income[]>(`${this.apiUrl}/date-range`, { params });
  }

  getIncomeById(id: number): Observable<Income> {
    return this.http.get<Income>(`${this.apiUrl}/${id}`);
  }

  createIncome(request: IncomeRequest): Observable<Income> {
    return this.http.post<Income>(this.apiUrl, request);
  }

  updateIncome(id: number, request: IncomeRequest): Observable<Income> {
    return this.http.put<Income>(`${this.apiUrl}/${id}`, request);
  }

  deleteIncome(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getTotalIncome(startDate: string, endDate: string): Observable<number> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<number>(`${this.apiUrl}/total`, { params });
  }
}
