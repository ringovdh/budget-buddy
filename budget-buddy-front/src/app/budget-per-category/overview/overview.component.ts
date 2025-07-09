import { Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {CategoricalBudgetOverview} from "../../entity/CategoricalBudgetOverview";

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css',
    '../../../assets/panel_layout.css']
})
export class OverviewComponent implements OnChanges {

  @Input() categoricalBudgetOverview: CategoricalBudgetOverview;

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {

  }

}
