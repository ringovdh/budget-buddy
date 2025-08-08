import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { CommentService } from "../comment.service";
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Category } from "../../category/category";
import { CategoryService } from "../../category/category.service";
import {Subject, takeUntil} from "rxjs";

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css',
    '../../../../assets/modal_form_layout.css']
})
export class CreateComponent implements OnInit {

  createCommentForm!: FormGroup;
  categories!: Category[];
  private readonly destroy$ = new Subject<void>();


  constructor(public commentService: CommentService,
              public categoryService: CategoryService,
              public ngbActiveModal: NgbActiveModal,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
    this.loadCategories();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  get formControls(){
    return this.createCommentForm.controls;
  }

  submit():void {
    if (this.createCommentForm.invalid) {
      this.createCommentForm.markAllAsTouched();
      return;
    }

    const formValue = this.createCommentForm.value;
    const payload = {
      ...formValue,
      categoryId: formValue.category.id
    }
    this.commentService.create(payload)
        .pipe(takeUntil(this.destroy$))
        .subscribe({next: (createdComment) => {
            console.log('Comment created successfully!');
            this.ngbActiveModal.close(createdComment);
          }, error: (err) => {
            console.error('Failed to create comment', err);
          }
    });
  }

  private initForm(): void {
    this.createCommentForm = this.fb.group({
      searchterm: new FormControl('', Validators.required),
      replacement: new FormControl('', Validators.required),
      category: new FormControl('',Validators.required)
    });
  }

  private loadCategories() {
    this.categoryService.getAll()
        .pipe(takeUntil(this.destroy$))
        .subscribe(data => {
          this.categories = data;
        });
  }

  close() {
    this.ngbActiveModal.close('closed');
    this.ngbActiveModal.dismiss('Modal closed by user');
  }
}
