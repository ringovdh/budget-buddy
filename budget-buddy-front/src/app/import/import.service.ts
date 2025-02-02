import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent} from "@angular/common/http";
import {Observable} from "rxjs";
import {CustomHttpResponse} from "../entity/customHttpResponse";
import {ImportResponse} from "./importResponse";

@Injectable({
  providedIn: 'root'
})
export class ImportService {

  private readonly importURL: string = 'http://localhost:8080/importTransactions';

  constructor(private httpClient: HttpClient) { }

  uploadTransactions(file: File): Observable<HttpEvent<CustomHttpResponse<ImportResponse>>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.httpClient.post<HttpEvent<any>>(this.importURL, formData, {
      reportProgress: true,
      responseType: 'json'
    });
  }

}
