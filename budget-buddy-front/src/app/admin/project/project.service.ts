import {HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Page } from "../../entity/page";
import { Observable } from 'rxjs';
import { CustomHttpResponse } from '../../entity/customHttpResponse';
import { ProjectOverview } from '../../entity/ProjectOverview';
import { Project } from './../../projects/project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private readonly apiURL: string = 'http://localhost:8080/projects/';

  private httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Project[]> {
    return this.httpClient.get<Project[]>(this.apiURL)
  }

  projects$ = (projectname: string = '', page: number = 0, size: number = 10): Observable<CustomHttpResponse<Page<Project>>> =>
    this.httpClient.get<CustomHttpResponse<Page<Project>>>(`${this.apiURL}projectname?projectname=${projectname}&page=${page}&size=${size}`);

  update(id: number, project: Project): Observable<Project> {
    return this.httpClient.put<Project>(this.apiURL + id, JSON.stringify(project), this.httpOptions)
  }

  create(project: Project): Observable<Project> {
    return this.httpClient.post<Project>(this.apiURL, JSON.stringify(project), this.httpOptions)
  }

  delete(id: number) {
    return this.httpClient.delete<Project>(this.apiURL + id, this.httpOptions)
  }

  getProjectOverview(): Observable<ProjectOverview[]> {
    return this.httpClient.get<ProjectOverview[]>(this.apiURL + "overview")
  }

}
