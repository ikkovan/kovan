import { Component, OnInit } from '@angular/core';
import { EmployeeModel } from "../../../Models/employee.model";
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeesService } from "../../../service/employees.service";

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {
  id: number;
  employee: EmployeeModel;
  constructor(private route: ActivatedRoute,private router: Router,
    private employeeService: EmployeesService) { }

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
    this.employeeService.updateEmployee(this.id, this.employee)
      .subscribe(data => console.log(data), error => console.log(error));
    this.employee = new EmployeeModel();
    this.gotoList();
  }

  onSubmit() {
    this.updateEmployee();    
  }

  gotoList() {
    this.router.navigate(['/employees']);
  }

}
