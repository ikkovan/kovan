import { Component, OnInit } from '@angular/core';
import { Rule } from 'src/app/Models/rule.model';
import { Router } from '@angular/router';
import { RuleService } from 'src/app/service/rule.service';

@Component({
  selector: 'app-create-rule',
  templateUrl: './create-rule.component.html',
  styleUrls: ['./create-rule.component.css']
})
export class CreateRuleComponent implements OnInit {
  rule: Rule = new Rule();
  ruleArray = new Array();
  
  submitted = false;
  constructor(private ruleService: RuleService,
    private router: Router) { }

  ngOnInit(): void {
    
  }
  save() {
    this.ruleService.createRule(this.rule)
      .subscribe(data => console.log(data), error => console.log(error));
    this.rule = new Rule();
    this.gotoList();
  }
  
  onSubmit() {
    this.submitted = true;
    this.save();   
    
    
   // this.ruleArray.push(this.rule);
    //console.log(this.ruleArray);
   // this.rule = new Rule();
   // this.gotoList();
  }

  gotoList() {
    this.router.navigate(['/commands']);
    
  }
}
