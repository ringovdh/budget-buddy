import { Component, forwardRef, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";

interface YearOption {
  value: number;
  year: string;
}

@Component({
  selector: 'app-select-year',
  templateUrl: './select-year.component.html',
  styleUrls: ['./select-year.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectYearComponent),
      multi: true,
    },
  ]
})
export class SelectYearComponent implements ControlValueAccessor, OnInit {

  years: YearOption[];
  value: number | null = null;
  disabled: boolean = false;

  onChange: (value: number | null) => void = () => {};
  onTouched:  () => void = () => {};

  constructor() { }

  ngOnInit(): void {
    this.years = this.prepareYears();
  }

  writeValue(value: number | null ): void {
    this.value = value;
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

  private prepareYears(): YearOption[] {
    const startYear: number = 2016;
    const currentYear: number = new Date().getFullYear()
    const years: YearOption[] = [];
    for (let year = startYear; year <= currentYear + 1; year++) {
      years.push({ value: year, year: year.toString() });
    }
    console.log('years filled: ', years);
    return years;
  }
}
