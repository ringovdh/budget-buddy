import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { Comment } from "./comment";
import { Page } from "../../entity/page";
import { CustomHttpResponse } from "../../entity/customHttpResponse";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private readonly apiURL: string = 'http://localhost:8080/comments/';

  private httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(this.apiURL)
  }

  comments$ = (searchterm: string = '', page: number = 0, size: number = 10): Observable<CustomHttpResponse<Page<Comment>>> =>
    this.httpClient.get<CustomHttpResponse<Page<Comment>>>(`${this.apiURL}searchterm?searchterm=${searchterm}&page=${page}&size=${size}`);

  find(id: number): Observable<Comment> {
    return this.httpClient.get<Comment>(this.apiURL+ id)
  }

  delete(id: number) {
    return this.httpClient.delete<Comment>(this.apiURL + id, this.httpOptions)
  }

  create(comment: Comment): Observable<Comment> {
    return this.httpClient.post<Comment>(this.apiURL, JSON.stringify(comment), this.httpOptions)
  }

  update(id: number, comment: Comment): Observable<Comment> {
    return this.httpClient.put<Comment>(this.apiURL + id, JSON.stringify(comment), this.httpOptions)
  }
}
