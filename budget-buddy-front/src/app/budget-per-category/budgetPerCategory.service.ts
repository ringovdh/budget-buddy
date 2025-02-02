import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { MonthlyBudgetOverview } from '../entity/MonthlyBudgetOverview';
import {YearlyBudgetOverview} from "../entity/YearlyBudgetOverview";
import {BudgetPerCategory} from "../entity/BudgetPerCategory";

@Injectable({
  providedIn: 'root'
})
export class BudgetPerCategoryService {

  private apiURL: String = 'http://localhost:8080/budgets/category';

  constructor(private httpClient: HttpClient) { }

  getBudgetOverviewByCategory(category: number, year: string): Observable<BudgetPerCategory[]> {
    return this.httpClient.get<BudgetPerCategory[]>(`${this.apiURL}?categoryId=${category}&year=${year}`);
  }

}
