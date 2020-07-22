import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TreeComponent} from './components/tree/tree.component';
import {EmployeesComponent} from './components/employees/employees.component';
import {CreateEmployeeComponent} from './components/employees/create-employee/create-employee.component';
import {UpdateEmployeeComponent} from './components/employees/update-employee/update-employee.component';
import {EmployeeDetailsComponent} from './components/employees/employee-details/employee-details.component';
import {CreateRuleComponent} from './components/rule/create-rule/create-rule.component';
import { RuleComponent } from './components/rule/rule.component';

const routes: Routes = [
  {path:'tree', component: TreeComponent},
  {path:'employees', component: EmployeesComponent},
  { path: 'add', component: CreateEmployeeComponent },
  { path: 'update/:id', component: UpdateEmployeeComponent },
  { path: 'details/:id', component: EmployeeDetailsComponent },
  
    
  { path: 'commands', component: RuleComponent },
  { path: 'addCommand', component: CreateRuleComponent },
  
  {path:'', component: EmployeesComponent},
  {path:'**', component: TreeComponent}
];

@NgModule({
  imports: [

    RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
