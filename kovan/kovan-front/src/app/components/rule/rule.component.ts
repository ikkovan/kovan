import { Component, OnInit, ViewChild } from '@angular/core';
import {Rule} from '../../Models/rule.model'
import {RuleService} from '../../service/rule.service'
;import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { CreateRuleComponent } from './create-rule/create-rule.component';
import { CommandPackage } from 'src/app/Models/command-package.model';
import { Parameter } from 'src/app/Models/parameter.model';
import { MatAccordion } from '@angular/material/expansion';
@Component({
  selector: 'app-rule',
  templateUrl: './rule.component.html',
  styleUrls: ['./rule.component.css']
})
export class RuleComponent implements OnInit {
  rules: CommandPackage[];
  @ViewChild(MatAccordion) accordion: MatAccordion;
  constructor(private ruleService: RuleService, 
    private router: Router) { }
  private createRule: CreateRuleComponent;
  ngOnInit(): void {
    this.reloadRules();
  }
  reloadRules() {
   this.ruleService.getRulesList().subscribe(rules =>{
     this.rules = rules;
     console.log(rules);
    
   });
    
  }
  updateRule(commandName: string){
    this.router.navigate(['commandDetails', commandName]);
  }
 
  deleteRule(commandName: string) {
    this.ruleService.deleteRule(commandName)
      .subscribe(
        data => {
          console.log(data);
          this.reloadRules();
        },
        error => console.log(error));
  }

 

}
