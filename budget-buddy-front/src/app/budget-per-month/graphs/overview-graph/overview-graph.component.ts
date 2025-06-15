import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Chart } from 'chart.js/auto';
import {GraphData} from "../../../entity/GraphData";

@Component({
  selector: 'app-overview-graph',
  templateUrl: './overview-graph.component.html',
  styleUrls: ['./overview-graph.component.css',
    '../../../../assets/panel_layout.css',
    '../../../../assets/graph_layout.css']
})
export class OverviewGraphComponent implements OnChanges {
  POSITIVE = '+';
  NEGATIVE = '-';
  overviewBudgetCharts: any;
  fixedCosts: number[];
  otherCosts: number[];
  incomming: number[];
  @Input() graphData: GraphData;

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['graphData'] && this.graphData) {
      this.prepareGraphData();
      this.createOverviewBudgetGraphs();
    }
  }

  prepareGraphData() {
    this.fixedCosts = this.mapCosts(this.graphData.fixedCostAmounts, this.NEGATIVE);
    this.otherCosts = this.mapCosts(this.graphData.otherCostAmounts, this.NEGATIVE);
    this.incomming = this.mapCosts(this.graphData.incomingAmounts, this.POSITIVE);
  }

  private mapCosts(costs: Map<number, number>, originalSign: String) : number[] {
    let mapedCosts: number[] = [];
    for (let [index, value] of Object.entries(costs)) {
      if (originalSign === this.NEGATIVE) {
        mapedCosts.push(value * -1)
      } else {
        mapedCosts.push(value)
      }
    }
    return mapedCosts;
  }

  createOverviewBudgetGraphs() {
    if (this.overviewBudgetCharts != null) {
      this.overviewBudgetCharts.destroy();
    }
    const labels = this.graphData.labels.map(s => s.toString());

    this.overviewBudgetCharts = new Chart('chartsOverviewBudget', {

      data: {
        labels: labels,
        datasets: [
          {
            type: 'bar',
            label: 'Vaste kosten',
            data: this.fixedCosts,
            backgroundColor: '#b4d9ec',
            stack: 'combined'
          },
          {
            type: 'bar',
            label: 'Algemene kosten',
            data: this.otherCosts,
            backgroundColor: '#0d97dc',
            stack: 'combined'
          },
          {
            type: 'line',
            label: 'Inkomsten',
            data: this.incomming,
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
