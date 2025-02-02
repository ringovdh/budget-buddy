import {NgModule} from "@angular/core";
import { CommonModule } from '@angular/common';
import { SelectYearComponent } from "./select-year/select-year.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

@NgModule({
  declarations: [
    SelectYearComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    SelectYearComponent
  ]
})

export class SelectYearComponentModule {}
