import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
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
  searchForm: FormGroup;
  title: string = 'Overzicht transacties per categorie'
  private readonly destroy$ = new Subject<void>();

  constructor(
      private budgetPerCategoryService: BudgetPerCategoryService,
      private formBuilder: FormBuilder) {
    this.searchForm = this.createSearchForm()
  }

  ngOnInit(): void {
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
          this.title = `Overzicht transacties: ${data.category.label}`;
        });
  }

  private createSearchForm() {
    return this.formBuilder.group({
      category: [null, Validators.required],
      year: [null]
    });
  }

}
