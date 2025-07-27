import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Category } from '../../admin/category/category';
import { CategoryService } from '../../admin/category/category.service';
import { BudgetPerCategoryService } from '../budgetPerCategory.service';
import {CategoricalBudgetOverview} from "../../entity/CategoricalBudgetOverview";

@Component({
  selector: 'app-transactions-per-category',
  templateUrl: './budget-per-category.component.html',
  styleUrls: ['./budget-per-category.component.css',
    '../../../assets/panel_layout.css']
})
export class BudgetPerCategoryComponent implements OnInit {

  categoricalBudgetOverview: CategoricalBudgetOverview;
  categories: Category[] = [];
  searchForm!: FormGroup;

  constructor(public budgetPerCategoryService: BudgetPerCategoryService,
              public categoryService: CategoryService) { }

  ngOnInit(): void {
    this.prepareCategories();
    this.createSearchForm();
  }

  submit() {
    this.budgetPerCategoryService.getBudgetOverviewByCategory(this.searchForm.get("category").value, this.searchForm.get("year").value).subscribe(data => {
      this.categoricalBudgetOverview = data;
    });
  }

  private createSearchForm() {
    this.searchForm = new FormGroup({
      category: new FormControl('', Validators.required),
      year: new FormControl('')
    })
  }

  get f(){
    return this.searchForm.controls
  }

  private prepareCategories() {
    this.categoryService.getAll().subscribe(data => {
      this.categories = data;
    });
  }

}
