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
  
  getRule(commandName: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/commandDetails/${commandName}`);
  }
 
  updateRule(commandName: string, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/ruleUpdate/${commandName}`, value);
  }

  deleteRule(commandName: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/command/delete/${commandName}`, { responseType: 'text' });
  }

  getRulesList() {
  
    return this.http.get<any>(`${this.baseUrl}/commands`);
    
  }
  getParameterList() {

    return this.http.get<string[]>(`${this.baseUrl}/getParameters`);
    
  }


}
