import {NgModule} from "@angular/core";
import {SavingsYearOverviewComponent} from "./savings-year-overview/savings-year-overview.component";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    SavingsYearOverviewComponent
  ],
  exports: [SavingsYearOverviewComponent
  ],
  imports: [
    CommonModule]
})
export class SavingsModule { }
