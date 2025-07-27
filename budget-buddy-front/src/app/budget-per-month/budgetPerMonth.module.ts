import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BudgetPerMonthRoutingModule } from './budgetPerMonth-routing.module';
import { IndexComponent } from "./index/index.component";
import { OverviewComponent } from './overview/overview.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BudgetPerMonthComponent } from './budget-per-month/budget-per-month.component';
import { BudgetBuddySelectComponentModule } from '../forms/budgetBuddySelectComponent.module';
import { OverviewGraphComponent } from './graphs/overview-graph/overview-graph.component';
import { FixedCostGraphComponent } from './graphs/fixed-cost-graph/fixed-cost-graph.component';
import { OtherCostGraphComponent } from './graphs/other-cost-graph/other-cost-graph.component';
import { ResumeComponent } from './resume/resume.component'
import { ProjectsModule } from "../projects/projects.module";
import { BudgetOverviewColumnComponent } from "./budget-overview-column/budget-overview-column.component";

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
        BudgetBuddySelectComponentModule,
        ProjectsModule,
        BudgetOverviewColumnComponent
    ]
})
export class BudgetPerMonthModule { }
