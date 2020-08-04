import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TreeComponent} from './components/tree/tree.component';
import {EmployeesComponent} from './components/employees/employees.component';
import {CreateEmployeeComponent} from './components/employees/create-employee/create-employee.component';
import {UpdateEmployeeComponent} from './components/employees/update-employee/update-employee.component';
import {EmployeeDetailsComponent} from './components/employees/employee-details/employee-details.component';
import {CreateRuleComponent} from './components/rule/create-rule/create-rule.component';

import {UpdateRuleComponent} from './components/rule/update-rule/update-rule.component';
import { RuleComponent } from './components/rule/rule.component';
import { PayrollComponent } from './components/payroll/payroll.component';
import { PayrollDetailComponent } from './components/payroll/payroll-detail/payroll-detail.component';

const routes: Routes = [
  {path:'tree', component: TreeComponent},
  {path:'employees', component: EmployeesComponent},
  { path: 'add', component: CreateEmployeeComponent },
  { path: 'update/:id', component: UpdateEmployeeComponent },
  { path: 'details/:id', component: EmployeeDetailsComponent },
  
    
  { path: 'commands', component: RuleComponent },
  { path: 'addCommand', component: CreateRuleComponent },
  { path: 'commandDetails/:commandName', component: UpdateRuleComponent },

  { path: 'payroll/payrolls', component: PayrollComponent },
  { path: 'payroll/show/new/:type/:id', component: PayrollDetailComponent },
  
  {path:'', component: EmployeesComponent},
  {path:'**', component: TreeComponent}
];

@NgModule({
  imports: [

    RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
