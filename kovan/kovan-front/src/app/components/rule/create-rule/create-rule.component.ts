import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Rule } from 'src/app/Models/rule.model';
import { Router } from '@angular/router';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { RuleService } from 'src/app/service/rule.service';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

import { Observable } from 'rxjs';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { startWith, map } from 'rxjs/operators';
import { MatChipInputEvent } from '@angular/material/chips';
import { Variable } from 'src/app/Models/variable.model';
import { Statement } from 'src/app/Models/statement.model';
import { CommandPackage } from 'src/app/Models/command-package.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-rule',
  templateUrl: './create-rule.component.html',
  styleUrls: ['./create-rule.component.css']
})
export class CreateRuleComponent implements OnInit {
  rule: Rule = new Rule();
  commandPackage: CommandPackage = new CommandPackage();

  statements: Statement[] = [];
  variables: Variable[] = [];
  // stepper formsGroups
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  //chips variables
  selectable = true;
  visible: boolean = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  parameterCtrl = new FormControl();
  filteredParameters: Observable<string[]>;
  parameters: string[] = [];
  allParameters: string[] = [];

  @ViewChild('paramInput') paramInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  //step 1 function selection list
  commands: any[] = [
    { name: 'AGI' },
    { name: 'DMG-VERGİ' },
    { name: 'K_Matrah' },

  ];
  constructor(private ruleService: RuleService,
    private router: Router, private _formBuilder: FormBuilder,
    private _snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['',]
    });
    this.thirdFormGroup = this._formBuilder.group({
      statementCtrl: ['',]
    });
    // get command variables 
    this.ruleService.getParameterList().subscribe(params => {

      params.forEach(param => {
        this.allParameters.push(param);
      })

      this.filteredParameters = this.parameterCtrl.valueChanges.pipe(
        startWith(null),
        map((param: string | null) => param ? this._filter(param) : this.allParameters.slice()));

    })


  }
  // saves the rule
  save() {
    this.ruleService.createRule(this.commandPackage)
      .subscribe(data => {
        console.log(data)
        this._snackBar.open("Başarıyla eklendi!", "Tamam", { duration: 3000, });
      }, error => {
        console.log(error)
        this._snackBar.open("Hata!", "Tamam", { duration: 3000, });
      })
    this.commandPackage = new CommandPackage();
    this.reset();
    //this.gotoList();
  }

  //Currently,it creates Rule model and fills areas. no posting
  onSubmit() {
    //command
    this.rule.commandName = this.firstFormGroup.value.firstCtrl;
    this.commandPackage.command = this.rule;
    //variables
    if (this.parameters) {
      this.parameters.forEach(combinedWord => {
        let variable = new Variable();
        variable.locatedTable = combinedWord.split("/")[0];
        variable.locatedColumn = combinedWord.split("/")[1];
        this.variables.push(variable);

      });

      this.commandPackage.variables = this.variables;
    }

    //statements
    if (this.thirdFormGroup.value.statementCtrl != '') {
      this.thirdFormGroup.value.statementCtrl.split("\n").forEach(statement => {
        let state = new Statement();
        state.line = statement;
        this.statements.push(state);
      })
      this.commandPackage.statements = this.statements;
    }

    console.log(this.commandPackage);
    this.save();


  }

  gotoList() {
    this.router.navigate(['/commands']);

  }

  //chip material functions and events
  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our param
    if ((value || '').trim()) {
      this.parameters.push(value.trim());
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.parameterCtrl.setValue(null);
  }

  remove(param: string): void {
    const index = this.parameters.indexOf(param);

    if (index >= 0) {
      this.parameters.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.parameters.push(event.option.viewValue);
    this.paramInput.nativeElement.value = '';
    this.parameterCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allParameters.filter(param => param.toLowerCase().indexOf(filterValue) === 0);
  }

  //reset forms 
  reset() {
    this.variables = [];
    this.commandPackage.variables = [];
    this.commandPackage.statements = [];
    this.parameters = [];
    this.statements = [];
  }

}
