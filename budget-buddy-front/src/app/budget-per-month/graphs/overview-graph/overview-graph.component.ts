import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Chart } from 'chart.js/auto';
import {GraphData} from "../../../entity/GraphData";
import {BudgetPerMonthService} from "../../budgetPerMonth.service";

@Component({
  selector: 'app-overview-graph',
  templateUrl: './overview-graph.component.html',
  styleUrls: ['./overview-graph.component.css',
    '../../../../assets/panel_layout.css',
    '../../../../assets/graph_layout.css']
})
export class OverviewGraphComponent implements OnChanges {

  labels: string[];
  fixedCosts: number[];
  otherCosts: number[];
  incoming: number[];
  overviewBudgetCharts: any;
  @Input() graphData: GraphData;

  constructor(public budgetService: BudgetPerMonthService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['graphData'] && this.graphData) {
      this.prepareGraphData();
      this.createMonthOverviewBudgetGraphs();
    }
  }

  prepareGraphData() {
    this.labels = this.graphData.labels.map(s => s.toString());
    this.fixedCosts = this.budgetService.mapCosts(this.graphData.fixedCostAmounts, '-');
    this.otherCosts = this.budgetService.mapCosts(this.graphData.otherCostAmounts, '-');
    this.incoming = this.budgetService.mapCosts(this.graphData.incomingAmounts, '+');
  }

  createMonthOverviewBudgetGraphs() {
    if (this.overviewBudgetCharts != null) {
      this.overviewBudgetCharts.destroy();
    }

    this.overviewBudgetCharts = this.budgetService.createOverviewBudgetGraphs(
        'chartsOverviewBudget',
        this.labels,
        this.fixedCosts,
        this.otherCosts,
        this.incoming
    );
  }
}
