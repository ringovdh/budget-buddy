import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BudgetPerMonthRoutingModule } from './budgetPerMonth-routing.module';
import { IndexComponent } from "./index/index.component";
import { OverviewComponent } from './overview/overview.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BudgetPerMonthComponent } from './budget-per-month/budget-per-month.component';
import { SelectYearComponent } from '../forms/select-year/select-year.component'
import { SelectYearComponentModule } from '../forms/selectYearComponent.module';
import { OverviewGraphComponent } from './graphs/overview-graph/overview-graph.component';
import { FixedCostGraphComponent } from './graphs/fixed-cost-graph/fixed-cost-graph.component';
import { OtherCostGraphComponent } from './graphs/other-cost-graph/other-cost-graph.component';
import { ResumeComponent } from './resume/resume.component'
import {ProjectsModule} from "../projects/projects.module";

@NgModule({
  declarations: [
    IndexComponent,
    OverviewComponent,
    BudgetPerMonthComponent,
    OverviewGraphComponent,
    FixedCostGraphComponent,
    OtherCostGraphComponent,
    ResumeComponent],
  exports: [
    IndexComponent,
    OverviewComponent,
    ResumeComponent
  ],
    imports: [
        CommonModule,
        BudgetPerMonthRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        SelectYearComponentModule,
        ProjectsModule
    ]
})
export class BudgetPerMonthModule { }
