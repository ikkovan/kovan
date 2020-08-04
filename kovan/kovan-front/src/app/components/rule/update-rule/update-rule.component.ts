import { Component, OnInit } from '@angular/core';
import { Rule } from 'src/app/Models/rule.model';
import { ActivatedRoute, Router } from '@angular/router';
import { RuleService } from 'src/app/service/rule.service';
import { CommandPackage } from 'src/app/Models/command-package.model';

@Component({
  selector: 'app-update-rule',
  templateUrl: './update-rule.component.html',
  styleUrls: ['./update-rule.component.css']
})
export class UpdateRuleComponent implements OnInit {
  commandName: string;
  rule: CommandPackage;
  constructor(private route: ActivatedRoute, private router: Router,
    private ruleService: RuleService) { }

  ngOnInit(): void {
    this.rule = new CommandPackage();

    this.commandName = this.route.snapshot.params['commandName'];

    this.ruleService.getRule(this.commandName)
      .subscribe(data => {
        console.log(data)
        this.rule = data;
      }, error => console.log(error));
  }
  updateRule() {
    this.ruleService.updateRule(this.commandName, this.rule)
      .subscribe(data => console.log(data), error => console.log(error));
    this.rule = new CommandPackage();
    this.backToList();
  }
  backToList() {
    this.router.navigate(['commands']);
  }

  onSubmit() {
    this.updateRule();
  }


}
