import { Component, OnInit } from '@angular/core';
import {Rule} from '../../Models/rule.model'
import {RuleService} from '../../service/rule.service'
;import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { CreateRuleComponent } from './create-rule/create-rule.component';
@Component({
  selector: 'app-rule',
  templateUrl: './rule.component.html',
  styleUrls: ['./rule.component.css']
})
export class RuleComponent implements OnInit {
  rules: Observable<Rule[]>;
  //rules: any[];
  constructor(private ruleService: RuleService, 
    private router: Router) { }
  private createRule: CreateRuleComponent;
  ngOnInit(): void {
    this.reloadRule();
  }
  reloadRule() {
   this.rules = this.ruleService.getRulesList();
    //this.rules = this.createRule.ruleArray;
  }
  deleteRule(id: number) {
    this.ruleService.deleteRule(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadRule();
        },
        error => console.log(error));
  }
 

}
