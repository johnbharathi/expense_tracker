import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DashboardData } from '../../shared/models';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private apiUrl = `${environment.apiUrl}/dashboard`;

  constructor(private http: HttpClient) {}

  getDashboardData(
    startDate: string,
    endDate: string
  ): Observable<DashboardData> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<DashboardData>(this.apiUrl, { params });
  }

  getCurrentMonthDashboard(): Observable<DashboardData> {
    return this.http.get<DashboardData>(`${this.apiUrl}/current-month`);
  }
}
