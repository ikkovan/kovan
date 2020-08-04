import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PayrollService {
  private baseUrl = 'http://localhost:8080/payroll';

  constructor(private http: HttpClient) { }

  //payroll calculation & presentation
  createPayroll(id: number, type: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/show/new/${type}/${id}`, { responseType: 'text' });
  }

  getPayrollList(): Observable<any> {
    return this.http.get(`${this.baseUrl}/payrolls`);

  }
}
