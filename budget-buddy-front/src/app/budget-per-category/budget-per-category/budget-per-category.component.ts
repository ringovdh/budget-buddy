import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Category } from '../../admin/category/category';
import { BudgetPerCategoryService } from '../budgetPerCategory.service';
import { CategoricalBudgetOverview } from "../../entity/CategoricalBudgetOverview";
import { Subject, takeUntil } from "rxjs";

@Component({
  selector: 'app-transactions-per-category',
  templateUrl: './budget-per-category.component.html',
  styleUrls: ['../../../assets/panel_layout.css']
})
export class BudgetPerCategoryComponent implements OnInit, OnDestroy {

  categoricalBudgetOverview: CategoricalBudgetOverview;
  categories: Category[] = [];
  searchForm!: FormGroup;
  private readonly destroy$ = new Subject<void>();

  constructor(
      private budgetPerCategoryService: BudgetPerCategoryService,
      private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.createSearchForm();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  submit(): void {
    if (this.searchForm.invalid) {
      return;
    }
    const { category, year } = this.searchForm.value;
    this.budgetPerCategoryService.getBudgetOverviewByCategory(category, year)
        .pipe(takeUntil(this.destroy$))
        .subscribe(data => {
          this.categoricalBudgetOverview = data;
        });
  }

  private createSearchForm() {
    this.searchForm = this.formBuilder.group({
      category: [null, Validators.required],
      year: [null]
    })
  }

}
