import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css',
    '../../../../assets/modal_form_layout.css']
})
export class CreateComponent implements OnInit {

  createProjectForm!: FormGroup;

  constructor(public projectService: ProjectService,
              public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.createProjectForm = new FormGroup({
      projectname: new FormControl('', Validators.required)
    });
  }

  get f(){
    return this.createProjectForm.controls;
  }

  submit(){
    this.projectService.create(this.createProjectForm.value).subscribe((res:any) => {
      this.ngbActiveModal.close('closed');
      console.log('Project created successfully!');
    });
  }

  close() {
    this.ngbActiveModal.close('closed');
  }

}
