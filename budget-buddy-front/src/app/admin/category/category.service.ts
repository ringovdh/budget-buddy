import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Category} from "./category";
import {CustomHttpResponse} from "../../entity/customHttpResponse";
import {Page} from "../../entity/page";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private apiURL = 'http://localhost:8080/categories/';

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

  categories$ = (label: string = '', page: number = 0, size: number = 10): Observable<CustomHttpResponse<Page<Category>>> =>
    this.httpClient.get<any>(`${this.apiURL}label?label=${label}&page=${page}&size=${size}`);

  delete(id: number) {
    return this.httpClient.delete<Category>(this.apiURL + id, this.httpOptions)
  }

  create(category: Category): Observable<Category> {
    return this.httpClient.post<Category>(this.apiURL, JSON.stringify(category), this.httpOptions)
  }

  update(id: number, category: Category): Observable<Category> {
    return this.httpClient.put<Category>(this.apiURL + id, JSON.stringify(category), this.httpOptions)
  }
}
