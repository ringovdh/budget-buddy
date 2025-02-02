import { Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import { BudgetPerCategory } from 'src/app/entity/BudgetPerCategory';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css',
    '../../../assets/panel_layout.css']
})
export class OverviewComponent implements OnChanges {

  @Input() budgetOverview: BudgetPerCategory[] = [];

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {

  }

}
