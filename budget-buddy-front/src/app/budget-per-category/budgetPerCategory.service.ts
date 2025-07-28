import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { CategoricalBudgetOverview } from "../entity/CategoricalBudgetOverview";

@Injectable({
  providedIn: 'root'
})
export class BudgetPerCategoryService {

  private apiURL: String = 'http://localhost:8080/budgets/budget-by-category';

  constructor(private httpClient: HttpClient) { }

  getBudgetOverviewByCategory(category: number, year: string): Observable<CategoricalBudgetOverview> {
    let params = new HttpParams();
    if (year) {
      params = params.set('year', year);
    }
    return this.httpClient.get<CategoricalBudgetOverview>(`${this.apiURL}/${category}`, { params });
  }

}
