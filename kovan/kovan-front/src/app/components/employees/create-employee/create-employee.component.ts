import { Component, OnInit } from '@angular/core';
import { EmployeesService } from "../../../service/employees.service";
import { EmployeeModel } from "../../../Models/employee.model";

import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent implements OnInit {
  employee: EmployeeModel = new EmployeeModel();
  submitted = false;
  constructor(private employeeService: EmployeesService,
    private router: Router,private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }
 
  
  save() {
    this.employeeService.createEmployee(this.employee)
      .subscribe(data => console.log(data), error => console.log(error));
    this.employee = new EmployeeModel();
    this.gotoList();
  }
  
  onSubmit() {
    this.submitted = true;
    this.save();    
    this._snackBar.open("Başarıyla eklendi!", "Tamam", {
      duration: 3000,
      
    });
  }

  gotoList() {
    this.router.navigate(['/employees']);
    
  }
}
