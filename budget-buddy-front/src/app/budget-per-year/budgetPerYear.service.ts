import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {YearlyBudgetOverview} from "../entity/YearlyBudgetOverview";

@Injectable({
  providedIn: 'root'
})
export class BudgetPerYearService {

  private apiURL: String = 'http://localhost:8080/budgets/year';

  constructor(private httpClient: HttpClient) { }

  getYearlyBudgetOverview(year: number): Observable<YearlyBudgetOverview> {
    return this.httpClient.get<YearlyBudgetOverview>(`${this.apiURL}?year=${year}`);
  }
}
