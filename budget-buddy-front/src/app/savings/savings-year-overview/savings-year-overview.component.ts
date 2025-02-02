import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {SavingsData} from "../../entity/SavingsData";
import {Chart} from "chart.js";

@Component({
  selector: 'app-savings-year-overview',
  templateUrl: './savings-year-overview.component.html',
  styleUrls: ['./savings-year-overview.component.css',
    '../../../assets/panel_layout.css',
    '../../../assets/graph_layout.css']
})
export class SavingsYearOverviewComponent implements OnChanges {

  @Input() savingsData: SavingsData;
  yearOverviewSavingsCharts: any;

  ngOnChanges(changes: SimpleChanges): void {
    this.createYearOverviewSavingsGraphs();
  }

  createYearOverviewSavingsGraphs = () => {
    let amounts: any[] = [];
    Object.entries(this.savingsData.savingAmounts)
      .forEach((value,key)  => {
        amounts.push(-value[1]);
    })

    if (this.yearOverviewSavingsCharts != null) {
      this.yearOverviewSavingsCharts.destroy();
    }

    this.yearOverviewSavingsCharts = new Chart('lineChartYearOverviewSavings', {
      type: 'line',
      data: {
        labels: this.savingsData.labels,
        datasets: [
          {
            label: 'Sparen',
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
              color: (ctx) => {
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
}
