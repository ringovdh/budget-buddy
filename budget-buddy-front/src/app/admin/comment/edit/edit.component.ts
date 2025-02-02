import { Component, OnInit } from '@angular/core';
import { Comment } from "../comment";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import { CommentService } from "../comment.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Category} from "../../category/category";
import {CategoryService} from "../../category/category.service";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css',
    '../../../../assets/modal_form_layout.css']
})
export class EditComponent implements OnInit {

  comment!: Comment;
  categories!: Category[];
  form!: FormGroup;

  constructor(public commentService: CommentService,
              public categoryService: CategoryService,
              public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.categoryService.getAll().subscribe(data => {
      this.categories = data;
    });
    this.form = new FormGroup({
      searchterm: new FormControl(this.comment.searchterm, Validators.required),
      replacement: new FormControl(this.comment.replacement, Validators.required),
      category: new FormControl(this.comment.category, Validators.required)
    });
  }

  compare(cat1: Category, cat2: Category) {
    return cat1.id === cat2.id;
  }

  get f(){
    return this.form.controls;
  }

  submit() {
    this.commentService.update(this.comment.id, this.form.value).subscribe((res:any) => {
      this.ngbActiveModal.close('closed');
      console.log('Comment updated successfully!');
    })
  }

  close() {
    this.ngbActiveModal.close('closed');
  }

}
