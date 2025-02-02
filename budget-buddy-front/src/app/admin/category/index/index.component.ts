import { Component, OnInit } from '@angular/core';
import {CategoryService} from "../category.service";
import {Category} from "../category";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {EditComponent} from "../edit/edit.component";
import {CreateComponent} from "../create/create.component";
import {BehaviorSubject, catchError, map, Observable, of, startWith} from "rxjs";
import {CustomHttpResponse} from "../../../entity/customHttpResponse";
import {Page} from "../../../entity/page";
import {HttpErrorResponse} from "@angular/common/http";
import {ConfirmationModalComponent} from "../../../modal/confirmation-modal/confirmation-modal.component";

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

  categoryState$: Observable<{appState: string, appData?:CustomHttpResponse<Page<Category>>, error?:HttpErrorResponse}>;
  responseSubject = new BehaviorSubject<CustomHttpResponse<Page<Category>>>(null);
  private currentPageSubject = new BehaviorSubject<number>(0);
  currentPage$ = this.currentPageSubject.asObservable();
  categories: Category[] = [];
  totalCategories: number = 0;

  constructor(public categoryService: CategoryService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.categoryState$ = this.categoryService.categories$().pipe(
      map((response: CustomHttpResponse<Page<Category>>) => {
        this.responseSubject.next(response);
        this.currentPageSubject.next(response.data.page.number);
        return { appState: 'APP_LOADED', appData: response }
      }),
      startWith({appState: 'APP_LOADING'}),
      catchError((error: HttpErrorResponse) => of({ appState: 'APP_ERROR', error })),
    )
  }

  goToPage(label?: string, pageNumber?: number): void {
    this.categoryState$ = this.categoryService.categories$(label, pageNumber).pipe(
      map((response: CustomHttpResponse<Page<Category>>) => {
        this.responseSubject.next(response);
        this.currentPageSubject.next(pageNumber);
        return { appState: 'APP_LOADED', appData: response }
      }),
      startWith({appState: 'APP_LOADED', appData: this.responseSubject.value}),
      catchError((error: HttpErrorResponse) => of({ appState: 'APP_ERROR', error })),
    )
  }

  goToNextOrPreviousPage(direction?: string, label?: string): void {
    this.goToPage(label, direction === 'forward' ? this.currentPageSubject.value + 1 : this.currentPageSubject.value - 1);
  }

  deleteCategory(id:number) {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result) => {
      if (result === 'confirmed') {
        this.categoryService.delete(id).subscribe(() => {
          this.categories = this.categories.filter(item => item.id !== id);
          console.log('Category deleted successfully!');
        })
      }
    });
  }

  editCategory(category:Category) {
    const modalRef = this.modalService.open(EditComponent);
    modalRef.componentInstance.category = category;
    modalRef.result.then((result) => {
      if (result) {
        this.loadCategories();
      }
    });
  }

  createCategory() {
    const modalRef = this.modalService.open(CreateComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.loadCategories();
      }
    });
  }

  loadCategories() {
    this.categoryService.getAll().subscribe((data: Category[]) => {
      this.categories = data;
      this.totalCategories = this.categories.length;
    });
  }

}
