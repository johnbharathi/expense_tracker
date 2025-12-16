import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.devolopment';
import { BehaviorSubject, tap } from 'rxjs';
import {
  AuthResponse,
  LoginRequest,
  RegisterRequest,
} from '../../shared/models';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private currentUserSubject = new BehaviorSubject<AuthResponse | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      this.currentUserSubject.next(JSON.parse(storedUser));
    }
  }

  register(request: RegisterRequest) {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/register`, request)
      .pipe(
        tap((response) => {
          this.setCurrentUser(response);
        })
      );
  }

  login(request: LoginRequest) {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request).pipe(
      tap((response) => {
        this.setCurrentUser(response);
      })
    );
  }

  private setCurrentUser(user: AuthResponse): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
    localStorage.setItem('token', user.token);
    this.currentUserSubject.next(user);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getCurrentUser(): AuthResponse | null {
    return this.currentUserSubject.value;
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
  }
}
