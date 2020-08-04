import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { Payroll } from '../../Models/payroll.model';
import { PayrollService } from 'src/app/service/payroll.service';
import { Router } from '@angular/router';
import { MatAccordion } from '@angular/material/expansion';
import { PayrollPackage } from 'src/app/Models/payroll-package.model';
import { Parameter } from 'src/app/Models/parameter.model';

@Component({
  selector: 'app-payroll',
  templateUrl: './payroll.component.html',
  styleUrls: ['./payroll.component.css']
})
export class PayrollComponent implements OnInit {
  @ViewChild(MatAccordion) accordion: MatAccordion;
  payrolls: PayrollPackage[] = [];
  constructor(private payrollService: PayrollService,
    private router: Router) { }

  ngOnInit(): void {
    this.reloadPayroll();
  }
  reloadPayroll() {
    this.payrollService.getPayrollList()
      .subscribe(data => {
        this.payrolls = data;
      
        console.log(this.payrolls);
      });

  }
}
