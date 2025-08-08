import { Component, OnInit } from '@angular/core';
import { CommentService } from "../comment.service";
import { Comment } from "../comment";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateComponent } from "../create/create.component";
import { EditComponent } from "../edit/edit.component";
import { BehaviorSubject, catchError, map, Observable, of, startWith, switchMap } from "rxjs";
import { HttpErrorResponse } from "@angular/common/http";
import { Page } from "../../../entity/page";
import { ConfirmationModalComponent } from "../../../modal/confirmation-modal/confirmation-modal.component";

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

  commentsState$: Observable<{ appState: string, appData?:Page<Comment>, error?:HttpErrorResponse}>;
  private pageRequestSubject =
      new BehaviorSubject<{ label: string, page: number }>(
          { label: '', page: 0 }
      );
  private currentPageSubject = new BehaviorSubject<number>(0);
  currentPage$ = this.currentPageSubject.asObservable();


  constructor(
      private commentService: CommentService,
      private modalService: NgbModal) { }

  ngOnInit(): void {
    this.commentsState$ = this.pageRequestSubject.pipe(
        switchMap(({ label, page }) => {
          this.currentPageSubject.next(page);
          return this.commentService.getCommentsPage(label, page).pipe(
              map(response => ({appState: 'APP_LOADED', appData: response})),
              startWith({appState: 'APP_LOADING'}),
              catchError((error: HttpErrorResponse) => of({appState: 'APP_ERROR', error}))
          );
        })
    );
  }

  goToPage(label: string = '', pageNumber: number = 0): void {
    this.pageRequestSubject.next({ label, page: pageNumber });
  }

  goToNextOrPreviousPage(direction: 'forward' | 'backward', label: string = ''): void {
    const newPage = direction === 'forward'
        ? this.currentPageSubject.value + 1
        : this.currentPageSubject.value - 1;
    this.goToPage(label, newPage);
  }

  createComment(): void {
    const modalRef = this.modalService.open(CreateComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.goToPage();
      }
    });
  }

  editComment(comment: Comment): void {
    const modalRef = this.modalService.open(EditComponent);
    modalRef.componentInstance.comment = comment;
    modalRef.result.then((result) => {
      if (result) {
        this.refreshCurrentPage();
      }
    });
  }

  deleteComment(comment: Comment): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result) => {
      if (result === 'confirmed') {
        this.commentService.delete(comment.id).subscribe({
          next: () => {
            console.log('Comment deleted successfully!');
            this.refreshCurrentPage();
            },
          error: (err) => console.error('Failed to delete comment', err)
       });
      }
    });
  }

  private refreshCurrentPage(): void {
    this.pageRequestSubject.next(this.pageRequestSubject.value);
  }

}
