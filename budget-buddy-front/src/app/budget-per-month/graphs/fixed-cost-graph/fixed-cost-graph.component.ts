import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Chart } from 'chart.js/auto';
import { BudgetOverviewPerMonth } from 'src/app/entity/BudgetOverviewPerMonth';

@Component({
  selector: 'app-fixed-cost-graph',
  templateUrl: './fixed-cost-graph.component.html',
  styleUrls: ['./fixed-cost-graph.component.css',
    '../../../../assets/panel_layout.css',
    '../../../../assets/graph_layout.css']
})
export class FixedCostGraphComponent implements OnChanges {

  fixedOutgoingBudgetPieChart: any;
  @Input() fixedOutgoingBudget: BudgetOverviewPerMonth[] = [];

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    this.createFixedOutgoingBudgetGraph();
  }

  private createFixedOutgoingBudgetGraph() {
    if (this.fixedOutgoingBudget != null) {
      let categories: string[] = [];
      let amounts: number[] = [];
      let backgroundColors: string[] = ['#05445E', '#189AB4', '#75E6DA', '#D4F1F4', '#145DA0', '#0C2D48', '#2E8BC0', '#B1D4E0'];
      this.fixedOutgoingBudget.forEach(value => {
        categories.push(value.category.label);
        amounts.push(value.total);
      });

      if (this.fixedOutgoingBudgetPieChart != null) {
        this.fixedOutgoingBudgetPieChart.destroy();
      }

      this.fixedOutgoingBudgetPieChart = new Chart('pieChartFixedOutgoingBudget', {
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
