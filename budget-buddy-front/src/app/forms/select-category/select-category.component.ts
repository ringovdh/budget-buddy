import { Component, forwardRef, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";
import {CategoryService} from "../../admin/category/category.service";

interface CategoryOption {
  id: number;
  label: string;
}

@Component({
  selector: 'app-select-category',
  templateUrl: './select-category.component.html',
  styleUrls: ['./select-category.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectCategoryComponent),
      multi: true,
    },
  ]
})
export class SelectCategoryComponent implements ControlValueAccessor, OnInit {

  categories: CategoryOption[];
  categoryId: number | null = null;
  disabled: boolean = false;

  onChange: (value: number | null) => void = () => {};
  onTouched:  () => void = () => {};

  constructor(public categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categories = this.prepareCategories();
  }

  writeValue(value: number | null ): void {
    this.categoryId = value;
  }

  registerOnChange(fn: (value: number | null) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void{
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean) {
    this.disabled = isDisabled;
  }

  private prepareCategories() {
    const categories: CategoryOption[] = [];
    this.categoryService.getAll().subscribe(data => {
      data.map(c => categories.push({id: c.id, label: c.label}));
    });
    console.log('categories filled: ', categories);
    return categories;
  }

}
