import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RuleService {

  
  //burada spring boottan gelecek olan url konulacak
  private baseUrl = 'http://localhost:8080';


  constructor(private http: HttpClient) { }


  createRule(rule: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/addCommand`, rule);
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
      console.log(res);
    })
    
    return this.http.get(`${this.baseUrl}/commands`);
    
  }

}
