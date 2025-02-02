import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BudgetPerYearComponent } from './budget-per-year/budget-per-year.component';

const routes: Routes = [
  { path: 'transaction/year', component: BudgetPerYearComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class BudgetPerYearRoutingModule { }
