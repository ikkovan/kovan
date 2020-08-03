import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rule } from '../Models/rule.model';

@Injectable({
  providedIn: 'root'
})
export class RuleService {

  
  //burada spring boottan gelecek olan url konulacak
  private baseUrl = 'http://localhost:8080';


  constructor(private http: HttpClient) { }


  createRule(commandPackage: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/defineCommand`, commandPackage);
  }
  
  getRule(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/ruleDetails/${id}`);
  }
 
  updateRule(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/ruleUpdate/${id}`, value);
  }

  deleteRule(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/deleteCommand/${id}`, { responseType: 'text' });
  }

  getRulesList(): Observable<any> {
  
    this.http.get(`${this.baseUrl}/commands`).subscribe((res: Response) => {
     
    })
    
    return this.http.get(`${this.baseUrl}/commands`);
    
  }
  getParameterList() {

    return this.http.get<string[]>(`${this.baseUrl}/getParameters`);
    
  }


}
