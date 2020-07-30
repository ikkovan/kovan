import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule } from '@angular/common/http';
import {MatChipsModule} from '@angular/material/chips';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AddNodeComponent, NewNodeDialog } from './theme/add-node/add-node.component';
import { EditNodeComponent, EditNodeDialog } from './theme/edit-node/edit-node.component';
import { DeleteNodeComponent } from './theme/delete-node/delete-node.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatBadgeModule} from '@angular/material/badge';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import {MatButtonModule, } from '@angular/material/button';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { MatCheckboxModule, } from '@angular/material/checkbox';
import { MatCardModule, } from '@angular/material/card';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import {MatInputModule  } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import {MatMenuModule  } from '@angular/material/menu';
//import { MatNativeDateModule } from '@angular/material/';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import {MatProgressSpinnerModule  } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
//import {MatRippleModule  } from '@angular/material/';
import {MatSelectModule  } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSliderModule } from '@angular/material/slider';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import {MatSnackBarModule  } from '@angular/material/snack-bar';
import {  MatSortModule} from '@angular/material/sort';
import {MatStepperModule  } from '@angular/material/stepper';
import {MatTableModule  } from '@angular/material/table';
import {MatTabsModule  } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTreeModule } from '@angular/material/tree';

import { RouterModule,Routes } from '@angular/router';


import {DragDropModule} from '@angular/cdk/drag-drop';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {MatNativeDateModule} from '@angular/material/core';

import {MatDialogModule} from '@angular/material/dialog';
import { EmployeesComponent } from './components/employees/employees.component';
import { TreeComponent } from './components/tree/tree.component';
//import { EmployeeModel } from './Models/employee.model';
import { CreateEmployeeComponent } from './components/employees/create-employee/create-employee.component';
import { UpdateEmployeeComponent } from './components/employees/update-employee/update-employee.component';
import { EmployeeDetailsComponent } from './components/employees/employee-details/employee-details.component';

import { RuleComponent } from './components/rule/rule.component';
import { CreateRuleComponent } from './components/rule/create-rule/create-rule.component';

import { UpdateRuleComponent } from './components/rule/update-rule/update-rule.component';
import { SearchPipe } from './components/employees/search.pipe';
import { PayrollComponent } from './components/payroll/payroll.component';
import { PayrollDetailComponent } from './components/payroll/payroll-detail/payroll-detail.component';


@NgModule({
  entryComponents: [
    NewNodeDialog,
    EditNodeDialog
  ],
  declarations: [
    AppComponent,
   // AppRoutingModule,
    AddNodeComponent,
    NewNodeDialog,
    EditNodeComponent,
    EditNodeDialog,
    DeleteNodeComponent,
    EmployeesComponent,
    TreeComponent,
    //EmployeeModel,
    CreateEmployeeComponent,
    UpdateEmployeeComponent,
    EmployeeDetailsComponent,
  
    RuleComponent,
    CreateRuleComponent,
 
    UpdateRuleComponent,
 
    SearchPipe,
 
    PayrollComponent,
 
    PayrollDetailComponent,
 

  ],
  imports: [
    //RouterModule.forRoot([]),
    MatChipsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    MatAutocompleteModule,
    MatNativeDateModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatStepperModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    ScrollingModule,
    BrowserAnimationsModule,
    BrowserAnimationsModule,
    FormsModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,

    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatStepperModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    ScrollingModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
