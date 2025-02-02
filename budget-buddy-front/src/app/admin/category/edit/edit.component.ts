import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../category";
import {CategoryService} from "../category.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css',
    '../../../../assets/modal_form_layout.css']
})
export class EditComponent implements OnInit {

  category!: Category;
  editCategoryForm!: FormGroup;

  constructor(public categoryService: CategoryService,
              public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.editCategoryForm = new FormGroup({
      icon: new FormControl(this.category.icon, Validators.required),
      label: new FormControl(this.category.label, Validators.required),
      fixedcost: new FormControl(this.category.fixedcost),
      indetails: new FormControl(this.category.indetails),
      inmonitor: new FormControl(this.category.inmonitor),
      revenue: new FormControl(this.category.revenue),
      limitamount: new FormControl(this.category.limitamount)
    });
  }

  get f(){
    return this.editCategoryForm.controls;
  }

  submit(){
    this.categoryService.update(this.category.id, this.editCategoryForm.value).subscribe((res:any) => {
      this.ngbActiveModal.close('closed');
      console.log('Category updated successfully!');
    })

  }

  close() {
    this.ngbActiveModal.close('closed');
  }
}
