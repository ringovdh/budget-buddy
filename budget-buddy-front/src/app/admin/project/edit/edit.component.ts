import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Project } from '../../../projects/project';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css',
    '../../../../assets/modal_form_layout.css']
})
export class EditComponent implements OnInit {

  project!: Project;
  editProjectForm!: FormGroup;

  constructor(public projectService: ProjectService,
              public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.editProjectForm = new FormGroup({
      projectname: new FormControl(this.project.projectname, Validators.required),
      description: new FormControl(this.project.description),
      startdate: new FormControl(this.project.startdate, Validators.required),
      enddate: new FormControl(this.project.enddate),
      icon: new FormControl(this.project.icon, Validators.required)
    });
  }

  get f(){
    return this.editProjectForm.controls;
  }

  submit() {
    this.projectService.update(this.project.id, this.editProjectForm.value).subscribe((res:any) => {
      this.ngbActiveModal.close('closed');
      console.log('Project updated successfully!');
    })
  }

  close() {
    this.ngbActiveModal.close('closed');
  }

}
