import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Chart} from "chart.js/auto";
import {YearlyBudgetOverview} from "../../../entity/YearlyBudgetOverview";
import {GraphData} from "../../../entity/GraphData";

@Component({
  selector: 'app-year-overview-graph',
  templateUrl: './year-overview-graph.component.html',
  styleUrls: ['./year-overview-graph.component.css',
    '../../../../assets/panel_layout.css',
    '../../../../assets/graph_layout.css']
})
export class YearOverviewGraphComponent implements OnChanges {

  yearOverviewBudgetCharts: any;
  @Input() graphData: GraphData;

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    this.createYearOverviewBudgetGraphs();
  }

  createYearOverviewBudgetGraphs() {
    if (this.yearOverviewBudgetCharts != null) {
      this.yearOverviewBudgetCharts.destroy();
    }

    this.yearOverviewBudgetCharts = new Chart('chartsYearOverviewBudget', {
      type: 'bar',
      data: {
        datasets: [
          {
            label: 'Vaste kosten',
            data: this.graphData.fixedCostAmounts,
            backgroundColor: '#b4d9ec',
            stack: 'combined'
          },
          {
            label: 'Algemene kosten',
            data: this.graphData.otherCostAmounts,
            backgroundColor: '#0d97dc',
            stack: 'combined'
          },
          {
            type: 'line',
            label: 'Inkomsten',
            data: this.graphData.incomingAmounts,
            borderColor: '#d473aa',
            tension: 0.2,
            order: 1
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          }
        },
        scales: {
          y: {
            stacked: true
          }
        }
      }
    });
  }
}
