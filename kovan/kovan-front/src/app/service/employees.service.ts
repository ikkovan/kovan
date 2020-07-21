import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { EmployeeModel } from '../Models/employee.model';
import { SimpleSnackBar } from '@angular/material/snack-bar';


@Injectable({
  providedIn: 'root'
})
export class EmployeesService {

  //burada spring boottan gelecek olan url konulacak
  private baseUrl = 'http://localhost:8080';


  constructor(private http: HttpClient) { }


  getEmployee(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/details/${id}`);
  }

  createEmployee(employee: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/add`, employee);
  }

  updateEmployee(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, value);
  }

  deleteEmployee(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, { responseType: 'text' });
  }

  getEmployeesList(): Observable<any> {
    //return this.http.get(`${this.baseUrl}`);
    //console.log(this._dataChange);
    this.http.get(`${this.baseUrl}/employees`).subscribe((res: Response) => {
      console.log(res);
    })
    return this.http.get(`${this.baseUrl}/employees`);
    ;
  }

}
