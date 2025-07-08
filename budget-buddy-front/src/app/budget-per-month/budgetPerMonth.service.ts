import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { MonthlyBudgetOverview } from '../entity/MonthlyBudgetOverview';
import {Chart} from "chart.js";

@Injectable({
  providedIn: 'root'
})
export class BudgetPerMonthService {

  private apiURL: String = 'http://localhost:8080/budgets/period';

  constructor(private httpClient: HttpClient) { }

  getMonthlyBudgetOverview(month: number, year: number): Observable<MonthlyBudgetOverview> {
    return this.httpClient.get<MonthlyBudgetOverview>(`${this.apiURL}?month=${month}&year=${year}`);
  }

   mapCosts(costs: Map<number, number>, originalSign: String) : number[] {
    let mapedCosts: number[] = [];
    for (let [index, value] of Object.entries(costs)) {
      if (originalSign === '-') {
        mapedCosts.push(value * -1)
      } else {
        mapedCosts.push(value)
      }
    }
    return mapedCosts;
  }

  createOverviewBudgetGraphs(chartName: string,
                             labels: string[],
                             fixedCosts: number[],
                             otherCosts: number[],
                             incoming: number[]): any {

    return new Chart(chartName, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Vaste kosten',
            data:fixedCosts,
            backgroundColor: '#b4d9ec',
            stack: 'combined'
          },
          {
            label: 'Algemene kosten',
            data: otherCosts,
            backgroundColor: '#0d97dc',
            stack: 'combined'
          },
          {
            type: 'line',
            label: 'Inkomsten',
            data: incoming,
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
