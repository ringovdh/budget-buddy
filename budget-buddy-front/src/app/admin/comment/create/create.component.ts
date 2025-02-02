import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { CommentService } from "../comment.service";
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {Category} from "../../category/category";
import {CategoryService} from "../../category/category.service";

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css',
    '../../../../assets/modal_form_layout.css']
})
export class CreateComponent implements OnInit {

  createCommentForm!: FormGroup;
  categories!: Category[];

  constructor(public commentService: CommentService,
              public categoryService: CategoryService,
              public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.categoryService.getAll().subscribe(data => {
      this.categories = data;
    });
    this.createCommentForm = new FormGroup({
      searchterm: new FormControl('', Validators.required),
      replacement: new FormControl('', Validators.required),
      category: new FormControl('',Validators.required)
    });
  }

  get f(){
    return this.createCommentForm.controls;
  }

  submit(){
    this.commentService.create(this.createCommentForm.value).subscribe((res:any) => {
      this.ngbActiveModal.close('closed');
      console.log('Comment created successfully!');
    });
  }

  close() {
    this.ngbActiveModal.close('closed');
  }
}
