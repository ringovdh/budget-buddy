import { Component, OnInit } from '@angular/core';
import {
  ProjectTransactionsModalComponent
} from "../../modal/project-transactions-modal/project-transactions-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import { ProjectService } from 'src/app/admin/project/project.service';
import { ProjectOverview } from 'src/app/entity/ProjectOverview';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css',
    '../../../assets/panel_layout.css']
})
export class IndexComponent implements OnInit {

  projects: ProjectOverview[] = [];

  constructor(private projectService: ProjectService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects() {
    this.projectService.getProjectOverview()
        .subscribe((data) => {
          this.projects = data;
        });
  }

  openTransactionsModal(project: ProjectOverview) {
    const modalRef = this.modalService.open(ProjectTransactionsModalComponent)
    modalRef.componentInstance.project = project;
  }

}

