import { Component, OnInit } from '@angular/core';
import { Rule } from 'src/app/Models/rule.model';
import { ActivatedRoute, Router } from '@angular/router';
import { RuleService } from 'src/app/service/rule.service';

@Component({
  selector: 'app-update-rule',
  templateUrl: './update-rule.component.html',
  styleUrls: ['./update-rule.component.css']
})
export class UpdateRuleComponent implements OnInit {
  id: number;
  rule: Rule;
  constructor(private route: ActivatedRoute, private router: Router,
    private ruleService: RuleService) { }

  ngOnInit(): void {
    this.rule = new Rule();

    this.id = this.route.snapshot.params['id'];

    this.ruleService.getRule(this.id)
      .subscribe(data => {
        console.log(data)
        this.rule = data;
      }, error => console.log(error));
  }
  updateRule() {
    this.ruleService.updateRule(this.id, this.rule)
      .subscribe(data => console.log(data), error => console.log(error));
    this.rule = new Rule();
    this.backToList();
  }
  backToList() {
    this.router.navigate(['commands']);
  }

  onSubmit() {
    this.updateRule();
  }


}
