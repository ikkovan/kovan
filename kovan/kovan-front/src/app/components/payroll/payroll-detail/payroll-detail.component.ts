import { Component, OnInit } from '@angular/core';
import { Payroll } from 'src/app/Models/payroll.model';
import { ActivatedRoute, Router } from '@angular/router';
import { PayrollService } from 'src/app/service/payroll.service';

@Component({
  selector: 'app-payroll-detail',
  templateUrl: './payroll-detail.component.html',
  styleUrls: ['./payroll-detail.component.css']
})
export class PayrollDetailComponent implements OnInit {

  id: number;
  type :number;
  payroll : Payroll;
  constructor(private route: ActivatedRoute,private router: Router,
    private payrollService: PayrollService) { }
 
  ngOnInit(): void {
    this.payroll= new Payroll();
    this.id = this.route.snapshot.params['id'];
    this.type = this.route.snapshot.params['type'];
  

    this.payrollService.createPayroll(this.id,this.type)
      .subscribe(data => {
        console.log(data)
        this.payroll = JSON.parse(data); //dönen şey string
       
      }, error => console.log(error));

  }
  backToList(){
        this.router.navigate(['employees']);
  }

}
