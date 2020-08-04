import { Component, OnInit } from '@angular/core';
import { Payroll } from 'src/app/Models/payroll.model';
import { PayrollPackage } from 'src/app/Models/payroll-package.model';
import { ActivatedRoute, Router } from '@angular/router';
import { PayrollService } from 'src/app/service/payroll.service';
import { Parameter } from 'src/app/Models/parameter.model';

@Component({
  selector: 'app-payroll-detail',
  templateUrl: './payroll-detail.component.html',
  styleUrls: ['./payroll-detail.component.css']
})
export class PayrollDetailComponent implements OnInit {

  id: number;
  type: number;
  payroll: Payroll;
  payrollPackage: PayrollPackage;
  parameters : Parameter[] = [];
  constructor(private route: ActivatedRoute, private router: Router,
    private payrollService: PayrollService) { }

  ngOnInit(): void {
    this.payroll = new Payroll();
    this.id = this.route.snapshot.params['id'];
    this.type = this.route.snapshot.params['type'];


    this.payrollService.createPayroll(this.id, this.type)
      .subscribe(data => {
        console.log(data)
        this.payrollPackage = JSON.parse(data); //dönen şey string
        this.parameters = this.payrollPackage.parameterList;
        console.log(this.payrollPackage);
      }, error => console.log(error));

  }
  backToList() {
    this.router.navigate(['employees']);
  }

}
