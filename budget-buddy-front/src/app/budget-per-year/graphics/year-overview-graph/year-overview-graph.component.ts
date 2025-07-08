import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Chart} from "chart.js/auto";
import {YearlyBudgetOverview} from "../../../entity/YearlyBudgetOverview";
import {GraphData} from "../../../entity/GraphData";
import {BudgetPerMonthService} from "../../../budget-per-month/budgetPerMonth.service";

@Component({
  selector: 'app-year-overview-graph',
  templateUrl: './year-overview-graph.component.html',
  styleUrls: ['./year-overview-graph.component.css',
    '../../../../assets/panel_layout.css',
    '../../../../assets/graph_layout.css']
})
export class YearOverviewGraphComponent implements OnChanges {

  labels: string[];
  fixedCosts: number[];
  otherCosts: number[];
  incoming: number[];
  yearOverviewBudgetCharts: any;
  @Input() graphData: GraphData;

  constructor(public budgetService: BudgetPerMonthService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['graphData'] && this.graphData) {
      this.prepareGraphData();
      this.createYearOverviewBudgetGraphs();
    }
  }

  prepareGraphData() {
    this.labels = this.graphData.labels.map(s => s.toString());
    this.fixedCosts = this.budgetService.mapCosts(this.graphData.fixedCostAmounts, '-');
    this.otherCosts = this.budgetService.mapCosts(this.graphData.otherCostAmounts, '-');
    this.incoming = this.budgetService.mapCosts(this.graphData.incomingAmounts, '+');
  }

  createYearOverviewBudgetGraphs() {
    if (this.yearOverviewBudgetCharts != null) {
      this.yearOverviewBudgetCharts.destroy();
    }
    this.yearOverviewBudgetCharts = this.budgetService.createOverviewBudgetGraphs(
        'chartsYearOverviewBudget',
        this.labels,
        this.fixedCosts,
        this.otherCosts,
        this.incoming
    );
  }
}
