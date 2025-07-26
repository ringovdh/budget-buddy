import {Component, OnInit} from '@angular/core';
import { CommentService } from "../comment.service";
import { Comment } from "../comment";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {CreateComponent} from "../create/create.component";
import {EditComponent} from "../edit/edit.component";
import {BehaviorSubject, catchError, map, Observable, of, startWith} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {Page} from "../../../entity/page";
import {CustomHttpResponse} from "../../../entity/customHttpResponse";
import {ConfirmationModalComponent} from "../../../modal/confirmation-modal/confirmation-modal.component";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";

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

  commentsState$: Observable<{appState: string, appData?:CustomHttpResponse<Page<Comment>>, error?:HttpErrorResponse}>;
  responseSubject = new BehaviorSubject<CustomHttpResponse<Page<Comment>>>(null);
  private currentPageSubject = new BehaviorSubject<number>(0);
  currentPage$ = this.currentPageSubject.asObservable();
  comments: Comment[] = [];
  p: number = 1;
  totalComments: number = 0;

  constructor(public commentService: CommentService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.commentsState$ = this.commentService.comments$().pipe(
      map((response: CustomHttpResponse<Page<Comment>>) => {
        console.log('response', response)
        this.responseSubject.next(response);
        this.currentPageSubject.next(response.data.page.number);
        return { appState: 'APP_LOADED', appData: response }
      }),
      startWith({appState: 'APP_LOADING'}),
      catchError((error: HttpErrorResponse) => of({ appState: 'APP_ERROR', error })),
    )
  }

  goToPage(searchterm?: string, pageNumber: number = 0): void {
    this.commentsState$ = this.commentService.comments$(searchterm, pageNumber).pipe(
      map((response: CustomHttpResponse<Page<Comment>>) => {
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

  deleteComment(id: number) {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result) => {
      if (result === 'confirmed') {
       this.commentService.delete(id).subscribe(() => {
          this.comments = this.comments.filter(item => item.id !== id);
          console.log('Comment deleted successfully!');
        })
      }
    });
  }

  editComment(comment: Comment) {
    const modalRef = this.modalService.open(EditComponent);
    modalRef.componentInstance.comment = comment;
    modalRef.result.then((result) => {
      if (result) {
        this.loadComments();
      }
    });
  }

  createComment() {
    const modalRef = this.modalService.open(CreateComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.loadComments();
      }
    });
  }

  loadComments() {
    this.commentService.getAll().subscribe((data: Comment[]) => {
      this.comments = data;
      this.totalComments = this.comments.length;
    });
  }
}
