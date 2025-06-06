import {Component, EventEmitter, Input, Output} from '@angular/core';
import {DecimalPipe} from "@angular/common";
import {BudgetOverviewPerMonth} from "../../entity/BudgetOverviewPerMonth";

@Component({
  selector: 'app-budget-overview-column',
  standalone: true,
  imports: [
    DecimalPipe
  ],
  templateUrl: './budget-overview-column.component.html',
  styleUrls: ['./budget-overview-column.component.css',
    '../../../assets/panel_layout.css']
})
export class BudgetOverviewColumnComponent {
  @Input() items: BudgetOverviewPerMonth[] = [];
  @Output() itemClick = new EventEmitter<BudgetOverviewPerMonth>();

  onCardClick(item: BudgetOverviewPerMonth): void {
    this.itemClick.emit(item);
  }
}
