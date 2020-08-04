import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Payroll } from '../../Models/payroll.model';
import { PayrollService } from 'src/app/service/payroll.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payroll',
  templateUrl: './payroll.component.html',
  styleUrls: ['./payroll.component.css']
})
export class PayrollComponent implements OnInit {
  payroll: Payroll[] = [];
  constructor(private payrollService: PayrollService,
    private router: Router) { }

  ngOnInit(): void {
    this.reloadPayroll();
  }
  reloadPayroll() {
    this.payrollService.getPayrollList()
      .subscribe(data => {
        this.payroll = data;
        console.log(this.payroll);
      });

  }
}
