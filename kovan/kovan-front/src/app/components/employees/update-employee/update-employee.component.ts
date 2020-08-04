import { Component, OnInit } from '@angular/core';
import { EmployeeModel } from "../../../Models/employee.model";
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeesService } from "../../../service/employees.service";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {
  id: number;
  employee: EmployeeModel;
  constructor(private route: ActivatedRoute, private router: Router,
    private employeeService: EmployeesService, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.employee = new EmployeeModel();

    this.id = this.route.snapshot.params['id'];

    this.employeeService.getEmployee(this.id)
      .subscribe(data => {
        console.log(data)
        this.employee = data;
      }, error => console.log(error));
  }

  updateEmployee() {
    console.log(this.employee.leaveFromWork);
    this.employeeService.updateEmployee(this.id, this.employee)
      .subscribe(data => {
        console.log(data)
        this._snackBar.open("Başarıyla güncellendi!", "Tamam", {
          duration: 3000,

        });
      }, error => {
        console.log(error)
        this._snackBar.open("Hata!", "Tamam", {
          duration: 3000,

        });
      });
    this.employee = new EmployeeModel();
    this.gotoList();
  }

  onSubmit() {
    if(this.employee.leaveFromWork){
      this.employeeService.deleteEmployee(this.employee.id)
      .subscribe(data => {
        console.log(data)
        this._snackBar.open("Başarıyla silindi!", "Tamam", {
          duration: 3000,

        });
      }, error => {
        console.log(error)
        this._snackBar.open("Hata!", "Tamam", {
          duration: 3000,

        });
      });
    }
    this.updateEmployee();
    

  }

  gotoList() {
    this.router.navigate(['/employees']);
  }

}
