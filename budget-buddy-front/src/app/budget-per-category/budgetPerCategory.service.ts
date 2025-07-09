import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CategoricalBudgetOverview} from "../entity/CategoricalBudgetOverview";

@Injectable({
  providedIn: 'root'
})
export class BudgetPerCategoryService {

  private apiURL: String = 'http://localhost:8080/budgets/categories';

  constructor(private httpClient: HttpClient) { }

  getBudgetOverviewByCategory(category: number, year: string): Observable<CategoricalBudgetOverview> {
    return this.httpClient.get<CategoricalBudgetOverview>(`${this.apiURL}?categoryId=${category}&year=${year}`);
  }

}
