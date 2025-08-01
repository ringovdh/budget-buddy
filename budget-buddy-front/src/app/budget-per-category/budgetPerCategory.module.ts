import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';

import { BudgetPerCategoryRoutingModule } from './budgetPerCategory-routing.module';
import { BudgetBuddySelectComponentModule } from '../forms/budgetBuddySelectComponent.module'
import { BudgetPerCategoryComponent } from './budget-per-category/budget-per-category.component';
import { OverviewComponent } from './overview/overview.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { IndexComponent } from './index/index.component';
import { GraphsComponent } from './graphs/graphs.component';


@NgModule({
  declarations: [
    BudgetPerCategoryComponent,
    OverviewComponent,
    IndexComponent,
    GraphsComponent,
  ],
  imports: [
    CommonModule,
    BudgetPerCategoryRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BudgetBuddySelectComponentModule
  ]
})

export class BudgetPerCategoryModule { }
