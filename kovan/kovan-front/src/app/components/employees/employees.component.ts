import { Component, OnInit } from '@angular/core';
import { EmployeeDetailsComponent } from './employee-details/employee-details.component';
import { Observable } from "rxjs";
import { EmployeesService } from "../../service/employees.service";
import { EmployeeModel } from "../../Models/employee.model";

import { Router } from '@angular/router';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent implements OnInit {
  search="";
  

  employees: Observable<EmployeeModel[]>;
  constructor(private employeeService: EmployeesService,
    private router: Router) { }

  ngOnInit(): void {
    this.reloadData();
  }

  reloadData() {
    this.employees = this.employeeService.getEmployeesList();

  }
  deleteEmployee(id: number) {
    this.employeeService.deleteEmployee(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }
  updateEmployee(id:number){
    this.router.navigate(['update', id]);
  }
  employeeDetails(id: number){
    this.router.navigate(['details', id]);
  }
}
