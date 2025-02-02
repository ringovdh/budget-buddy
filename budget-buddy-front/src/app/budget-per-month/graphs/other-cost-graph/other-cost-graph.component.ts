import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Chart } from 'chart.js/auto';
import { BudgetOverviewPerMonth } from 'src/app/entity/BudgetOverviewPerMonth';

@Component({
  selector: 'app-other-cost-graph',
  templateUrl: './other-cost-graph.component.html',
  styleUrls: ['./other-cost-graph.component.css',
    '../../../../assets/panel_layout.css',
    '../../../../assets/graph_layout.css']
})
export class OtherCostGraphComponent implements OnChanges {

  outgoingBudgetPieChart: any;
  @Input() fixedOutgoingBudget: BudgetOverviewPerMonth[] = [];
  @Input() outgoingBudget: BudgetOverviewPerMonth[] = [];

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    this.createOutgoingBudgetGraph();
  }

  private createOutgoingBudgetGraph() {
    if (this.outgoingBudget != null) {
      let categories: string[] = [];
      let amounts: number[] = [];
      let backgroundColors: string[] = ['#BFD7ED', '#60A3D9', '#0074B7', '#003B73', '#0000FF', '#4B7BF5', '#79A9F5', '#C2E2F5'];
      this.outgoingBudget.forEach(value => {
        categories.push(value.category.label);
        amounts.push(value.total);
      });

      if (this.outgoingBudgetPieChart != null) {
        this.outgoingBudgetPieChart.destroy();
      }

      this.outgoingBudgetPieChart = new Chart('pieChartOutgoingBudget', {
        type: 'pie',
        data: {
          labels: categories,
          datasets: [
            {
              data: amounts,
              borderColor: '#303e45',
              backgroundColor: backgroundColors,
              hoverOffset: 4
            }
          ]
        },
        options: {
          responsive: true
        }
      });
    }
  }
}
