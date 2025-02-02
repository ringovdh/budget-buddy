import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { MonthlyBudgetOverview } from '../entity/MonthlyBudgetOverview';

@Injectable({
  providedIn: 'root'
})
export class BudgetPerMonthService {

  private apiURL: String = 'http://localhost:8080/budgets/period';

  constructor(private httpClient: HttpClient) { }

  getMonthlyBudgetOverview(month: number, year: number): Observable<MonthlyBudgetOverview> {
    return this.httpClient.get<MonthlyBudgetOverview>(`${this.apiURL}?month=${month}&year=${year}`);
  }

}
