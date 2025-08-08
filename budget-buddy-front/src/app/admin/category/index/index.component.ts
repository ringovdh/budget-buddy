import { Component, OnInit } from '@angular/core';
import { CategoryService } from "../category.service";
import { Category } from "../category";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { EditComponent } from "../edit/edit.component";
import { CreateComponent } from "../create/create.component";
import { BehaviorSubject, catchError, map, Observable, of, startWith, switchMap } from "rxjs";
import { Page } from "../../../entity/page";
import { HttpErrorResponse } from "@angular/common/http";
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

  categoryState$: Observable<{ appState: string, appData?:Page<Category>, error?:HttpErrorResponse}>;
  private pageRequestSubject = new BehaviorSubject<{ label: string, page: number }>({ label: '', page: 0 });
  private currentPageSubject = new BehaviorSubject<number>(0);
  currentPage$ = this.currentPageSubject.asObservable();

  constructor(
      private categoryService: CategoryService,
      private modalService: NgbModal) { }

  ngOnInit(): void {
    this.categoryState$ = this.pageRequestSubject.pipe(
      switchMap(({ label, page }) => {
        this.currentPageSubject.next(page);
        return this.categoryService.getCategoriesPage(label, page).pipe(
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

  createCategory(): void {
    const modalRef = this.modalService.open(CreateComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.goToPage();
      }
    });
  }

  editCategory(category:Category): void {
    const modalRef = this.modalService.open(EditComponent);
    modalRef.componentInstance.category = category;
    modalRef.result.then((result) => {
      if (result) {
        this.refreshCurrentPage();
      }
    });
  }

  deleteCategory(category: Category): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result) => {
      if (result === 'confirmed') {
        this.categoryService.delete(category.id).subscribe({
          next: () => {
            console.log('Category deleted successfully!');
            this.refreshCurrentPage();
          },
          error: (err) => console.error('Failed to delete category', err)
        });
      }
    });
  }

  private refreshCurrentPage(): void {
    this.pageRequestSubject.next(this.pageRequestSubject.value);
  }

}
