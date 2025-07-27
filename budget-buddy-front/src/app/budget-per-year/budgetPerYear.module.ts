import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';
import { BudgetPerYearComponent } from "./budget-per-year/budget-per-year.component";
import { BudgetBuddySelectComponentModule } from '../forms/budgetBuddySelectComponent.module'
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BudgetPerYearRoutingModule } from "./budgetPerYear-routing.module";
import { OverviewComponent } from './overview/overview.component';
import { MonthOverviewComponent } from './month-overview/month-overview.component';
import { GraphsComponent } from './graphics/graphs/graphs.component';
import {BudgetPerMonthModule} from "../budget-per-month/budgetPerMonth.module";
import { YearOverviewGraphComponent } from './graphics/year-overview-graph/year-overview-graph.component';
import {ProjectsModule} from "../projects/projects.module";
import {SavingsModule} from "../savings/savings.module";

@NgModule({
  declarations: [
    BudgetPerYearComponent,
    OverviewComponent,
    MonthOverviewComponent,
    GraphsComponent,
    YearOverviewGraphComponent
  ],
  imports: [
    CommonModule,
    BudgetPerYearRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BudgetBuddySelectComponentModule,
    BudgetPerMonthModule,
    ProjectsModule,
    SavingsModule
  ]
})

export class BudgetPerYearModule { }
