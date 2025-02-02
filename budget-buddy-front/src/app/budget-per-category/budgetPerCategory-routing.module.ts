import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BudgetPerCategoryComponent } from './budget-per-category/budget-per-category.component';

const routes: Routes = [
  { path: 'transaction/category', component: BudgetPerCategoryComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class BudgetPerCategoryRoutingModule { }
