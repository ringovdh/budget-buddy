import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Chart } from 'chart.js';
import { BudgetPerCategory } from 'src/app/entity/BudgetPerCategory';
import { getPeriodLabel } from 'src/app/common/utils/dateUtils'

@Component({
  selector: 'app-graphs',
  templateUrl: './graphs.component.html',
  styleUrls: ['./graphs.component.css',
    '../../../assets/panel_layout.css']
})
export class GraphsComponent implements OnChanges {

  budgetPerCategorylineChart: any;
  txPerYearBarChart: any;
  amountPerYearBarChart: any;
  @Input() budgetOverview: BudgetPerCategory[] = [];
  budgetOverwiewByMonth: { label: string, total: number }[] = [];

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.budgetOverview != null) {
      this.createTxPerYearBarChart();
      this.createAmountPerYearBarChart();
      this.createBudgetPerCategoryGraph();
    }
  }

  private createBudgetPerCategoryGraph() {
      let periods: string[] = [];
      let amounts: number[] = [];
      let myDataset = this.groupBudgetOverviewByMonth();
      myDataset.forEach((value: number, key: string) => {
        periods.push(key);
        amounts.push(value);
      });

      if(this.budgetPerCategorylineChart != null) {
        this.budgetPerCategorylineChart.destroy();
      }

      this.budgetPerCategorylineChart = new Chart('lineChartBudgetPerCategory', {
        type: 'line',
        data: {
          labels: periods,
          datasets: [
            {
              label: 'Totaal besteed bedrag per maand',
              data: amounts,
              borderColor: '#738FD4',
              tension: 0.2
            }]
        },
        options: {
          responsive: true
        }
      });
  }

  private createAmountPerYearBarChart() {
      let periods: number[] = [];
      let amounts: number[] = [];
      this.budgetOverview.forEach(value => {
        periods.push(value.year);
        amounts.push(value.total)
      });

      if(this.amountPerYearBarChart != null) {
        this.amountPerYearBarChart.destroy();
      }

      this.amountPerYearBarChart = new Chart('barChartAmountPerYear', {
        type: 'bar',
        data: {
          labels: periods,
          datasets: [
            {
              label: 'Totaal besteed bedrag per jaar',
              data: amounts,
              backgroundColor: '#738FD4',
            }]
        },
        options: {
          responsive: true
        }
      });
  }

  private createTxPerYearBarChart() {
      let periods: number[] = [];
      let numbers: number[] = [];
      this.budgetOverview.forEach(value => {
        periods.push(value.year);
        numbers.push(value.transactions.length)
      });

      if (this.txPerYearBarChart != null) {
        this.txPerYearBarChart.destroy();
      }

      this.txPerYearBarChart = new Chart('barChartTxPerYear', {
        type: 'bar',
        data: {
          labels: periods,
          datasets: [
            {
              label: 'Aantal transacties per jaar',
              data: numbers,
              backgroundColor: '#9ae9db',
            }
          ]
        },
        options: {
          responsive: true
        }
      });
  }

  private groupBudgetOverviewByMonth() {
    let groups: Map<string, number> = new Map<string, number>();
    this.budgetOverview.forEach (
      o => o.transactions.forEach (
        tx => {
          let _period = getPeriodLabel(tx.date);
          if (groups.has(_period)) {
            if (tx.sign === '+') {
              let _total = groups.get(_period) + tx.amount;
              groups.set(_period, _total);
            } else {
              let _total = groups.get(_period) - tx.amount;
              groups.set(_period, _total);
            }

          } else {
            if (tx.sign === '+') {
              groups.set(_period, 0 + tx.amount);
            } else {
              groups.set(_period, 0 - tx.amount);
            }
          }
        })
    )
    return new Map([...groups.entries()].sort());
  }

}
