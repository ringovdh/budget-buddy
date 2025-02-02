import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Chart } from 'chart.js';
import { BudgetPerMonth } from 'src/app/entity/BudgetPerMonth';

@Component({
  selector: 'app-graphs',
  templateUrl: './graphs.component.html',
  styleUrls: ['./graphs.component.css',
    '../../../../assets/panel_layout.css']
})
export class GraphsComponent implements OnChanges {

  @Input() budgetsPerMonth: BudgetPerMonth[];
  @Input() year: number;
  restBudgetPerMonthlineChart: any;
  totalRestBudget: number = 0;

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.budgetsPerMonth != null) {
      this.createRestBudgetPerYearGraph();
    }
  }

  private createRestBudgetPerYearGraph() {
    let periods: string[] = [];
    let amounts: number[] = [];
    let myDataset = this.calculateRestBudgetByMonth();
    console.log('ssss', myDataset);
    myDataset.forEach((value: number, key: string) => {
      periods.push(key);
      amounts.push(value);
    });

    if (this.restBudgetPerMonthlineChart != null) {
      this.restBudgetPerMonthlineChart.destroy();
    }

    this.restBudgetPerMonthlineChart = new Chart('lineChartRestBudgetPerMonth', {
      type: 'line',
      data: {
        labels: periods,
        datasets: [
          {
            label: 'Resterend budget per maand',
            data: amounts,
            borderColor: '#738FD4',
            tension: 0.2
          }
        ]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            display: true,
            grid: {
              color:  (ctx) => {
                if (ctx.tick.value == 0) {
                  return '#9ae9db';
                } else {
                  return ''
                }
              }
            }
          }
        }
      }
    });
  }

  private calculateRestBudgetByMonth() {
    let groups: Map<string, number> = new Map<string, number>();
    this.totalRestBudget = 0;
    this.budgetsPerMonth.forEach (
        r => {
          let _period = r.month;
          groups.set(_period+"", this.calculateTotalRestBudget(r));
        });

    return new Map([...groups.entries()]);
  }

  private calculateTotalRestBudget = (r: BudgetPerMonth) => {
    let result = this.totalRestBudget + (r.totalIncomingBudget
      + r.totalFixedOutgoingBudget
      + r.totalOutgoingBudget);
    this.totalRestBudget = result;

    return result;
  }

}
