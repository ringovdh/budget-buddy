import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';
import { SelectYearComponent } from "./select-year/select-year.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SelectCategoryComponent } from "./select-category/select-category.component";

@NgModule({
  declarations: [
      SelectYearComponent,
      SelectCategoryComponent
  ],
  imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule
  ],
  exports: [
      SelectYearComponent,
      SelectCategoryComponent
  ]
})

export class BudgetBuddySelectComponentModule {}
