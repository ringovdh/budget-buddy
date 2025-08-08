import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { Comment } from "./comment";
import { Page } from "../../entity/page";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private readonly apiURL: string = 'http://localhost:8080/comments';

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.apiURL}/all`);
  }

  getCommentsPage(searchterm: string, page: number = 0, size: number = 10): Observable<Page<Comment>> {
    const params = new HttpParams()
        .set('searchterm', searchterm)
        .set('page', page)
        .set('size', size);

    return this.httpClient.get<Page<Comment>>(`${this.apiURL}/searchterm`, { params });
  }

  delete(id: number) {
    return this.httpClient.delete<Comment>(`${this.apiURL}/${id}`)
  }

  create(comment: Comment): Observable<Comment> {
    console.log('ccc', comment)
    return this.httpClient.post<Comment>(this.apiURL, comment)
  }

  update(id: number, comment: Comment): Observable<Comment> {
    return this.httpClient.put<Comment>(`${this.apiURL}/${id}`, comment)
  }
}
