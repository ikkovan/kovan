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
  _dataChange = new BehaviorSubject<EmployeeModel[]>(
    [
      {
       id       : 220014,
       name     : "Filiz",
       lastName : "Çetin",
       salary_template: 1,// kullanılacak şablon numarası
       isMarried : true,
      },
      {
        id       : 450014,
        name     : "Selin",
        lastName : "Çetin",
        salary_template: 1,
        isMarried : false,
       }

    ]
  );
  //burada spring boottan gelecek olan url konulacak
  private baseUrl = 'http://localhost:8080/kovan/employees';
 

  constructor(private http: HttpClient) { }
  
  getEmployee(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createEmployee(employee: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, employee);
  }

  updateEmployee(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, value);
  }

  deleteEmployee(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

  getEmployeesList(): Observable<any> {
    //return this.http.get(`${this.baseUrl}`);
    console.log(this._dataChange);
    return this._dataChange;
  }
  
}
