import { Component, OnInit, Input } from '@angular/core';
import { EmployeeModel } from "../../../Models/employee.model";
import { EmployeesService } from "../../../service/employees.service";
import { EmployeesComponent } from '../employees.component';
import { Router, ActivatedRoute } from '@angular/router';
import { PayrollService } from "../../../service/payroll.service";
import { Payroll } from 'src/app/Models/payroll.model';
import { PayrollDetailComponent } from '../../payroll/payroll-detail/payroll-detail.component';
@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {
  id: number;

  employee: EmployeeModel;
 
  
  constructor(private route: ActivatedRoute,private router: Router,
    private employeeService: EmployeesService
  ) { }

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

  runPayroll(type:number, id : number){

      this.router.navigate(['payroll/show',type,id]);
   
  }
 

}
