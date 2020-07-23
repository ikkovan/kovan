import { Component, OnInit } from '@angular/core';
import { EmployeeModel } from "../../../Models/employee.model";
import { EmployeesService } from "../../../service/employees.service";
import { EmployeesComponent } from '../employees.component';
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {
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
  backToList(){
    this.router.navigate(['employees']);
  }
  addRule(){
    this.router.navigate(['commands']);
  }

  runPayroll(id : number){
    console.log("payroll");
  }

}
