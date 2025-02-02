import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { CategoryService } from "../category.service";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css',
    '../../../../assets/modal_form_layout.css']
})
export class CreateComponent implements OnInit {

  createCategoryForm!: FormGroup;

  constructor(public categoryService: CategoryService,
              public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.createCategoryForm = new FormGroup({
      icon: new FormControl('', Validators.required),
      label: new FormControl('', Validators.required),
      fixedcost: new FormControl(false),
      indetails: new FormControl(false),
      inmonitor: new FormControl(false),
      revenue: new FormControl(false),
      limitamount: new FormControl(0)
    });
  }

  get f(){
    return this.createCategoryForm.controls;
  }

  submit(){
    this.categoryService.create(this.createCategoryForm.value).subscribe((res:any) => {
      this.ngbActiveModal.close('closed');
      console.log('Category created successfully!');
    });
  }

  close() {
    this.ngbActiveModal.close('closed');
  }
}
