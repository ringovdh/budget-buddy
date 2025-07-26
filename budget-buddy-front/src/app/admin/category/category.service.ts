import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Category} from "./category";
import {Page} from "../../entity/page";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private readonly apiURL = 'http://localhost:8080/categories/';

  httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.apiURL)
  }

  getCategoriesPage(label: string, page: number = 0, size: number = 10): Observable<Page<Category>> {
    const params = new HttpParams()
        .set('label', label)
        .set('page', page)
        .set('size', size);

    return this.httpClient.get<Page<Category>>(this.apiURL + 'label', { params });
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiURL}${id}`, this.httpOptions)
  }

  create(category: Category): Observable<Category> {
    return this.httpClient.post<Category>(this.apiURL, JSON.stringify(category), this.httpOptions)
  }

  update(id: number, category: Category): Observable<Category> {
    return this.httpClient.put<Category>(`${this.apiURL}${id}`, JSON.stringify(category), this.httpOptions)
  }
}
