import { Component, OnInit } from '@angular/core';
import { ProjectService } from "../project.service"
import { Page } from "../../../entity/page";
import { Project } from '../../../projects/project';
import {BehaviorSubject, catchError, map, Observable, of, startWith } from 'rxjs';
import { CustomHttpResponse } from 'src/app/entity/customHttpResponse';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateComponent } from '../create/create.component';
import { EditComponent } from '../edit/edit.component';
import { ConfirmationModalComponent } from 'src/app/modal/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css',
    '../../../../assets/modal_form_layout.css',
    '../../../../assets/panel_layout.css',
    '../../../../assets/table_layout.css',
    '../../../../assets/pagination_layout.css']
})
export class IndexComponent implements OnInit {

  projectState$: Observable<{appState: string, appData?:CustomHttpResponse<Page<Project>>, error?:HttpErrorResponse}>;
  responseSubject = new BehaviorSubject<CustomHttpResponse<Page<Project>>>(null);
  private currentPageSubject = new BehaviorSubject<number>(0);
  currentPage$ = this.currentPageSubject.asObservable();
  projects: Project[] = [];
  p: number = 1;

  constructor(public projectService: ProjectService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadProjects();

  }

  goToPage(searchterm?: string, pageNumber: number = 0): void {
    this.projectState$ = this.projectService.projects$(searchterm, pageNumber).pipe(
      map((response: CustomHttpResponse<Page<Project>>) => {
        this.responseSubject.next(response);
        this.currentPageSubject.next(pageNumber);
        return { appState: 'APP_LOADED', appData: response }
      }),
      startWith({appState: 'APP_LOADED', appData: this.responseSubject.value}),
      catchError((error: HttpErrorResponse) => of({ appState: 'APP_ERROR', error })),
    )
  }

  goToNextOrPreviousPage(direction?: string, searchterm?: string): void {
    this.goToPage(searchterm, direction === 'forward' ? this.currentPageSubject.value + 1 : this.currentPageSubject.value - 1);
  }

  createProject() {
    const modalRef = this.modalService.open(CreateComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.loadProjects();
      }
    });
  }

  editProject(project: Project) {
    const modalRef = this.modalService.open(EditComponent);
    modalRef.componentInstance.project = project;
    modalRef.result.then((result) => {
      if (result) {
        this.loadProjects();
      }
    });
  }

  deleteProject(id: number) {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result) => {
      if (result === 'confirmed') {
        this.projectService.delete(id).subscribe(() => {
          this.projects = this.projects.filter(item => item.id !== id);
          console.log('Project deleted successfully!');
          this.loadProjects();
        })
      }
    });
  }
  loadProjects() {
    this.projectState$ = this.projectService.projects$().pipe(
      map((response: CustomHttpResponse<Page<Project>>) => {
        this.responseSubject.next(response);
        this.currentPageSubject.next(response.data.page.number);
        return { appState: 'APP_LOADED', appData: response }
      }),
      startWith({appState: 'APP_LOADING'}),
      catchError((error: HttpErrorResponse) => of({ appState: 'APP_ERROR', error })),
    )
  }

}
