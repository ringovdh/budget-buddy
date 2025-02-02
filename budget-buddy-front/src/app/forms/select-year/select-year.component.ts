import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, ControlValueAccessor, NG_VALUE_ACCESSOR, FormBuilder, SelectControlValueAccessor } from "@angular/forms";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-select-year',
  templateUrl: './select-year.component.html',
  styleUrls: ['./select-year.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: SelectYearComponent,
      multi: true,
    },
  ],
  host: {
    "[id]": "id",
  },
})
export class SelectYearComponent implements ControlValueAccessor {

  years: {value:number, year: string}[];
  onChangeSubs: Subscription[] = [];
  onTouched: Function = () => {};
  touched = false;
  disabled = false;


  constructor() {
  }

  searchYearForm = new FormGroup({
    year: new FormControl('', Validators.required)
  })

  writeValue(value: any) {
    if (value) {
      this.searchYearForm.setValue(value, {emitEvent: false});
    }
  }

  registerOnChange(onChange: any) {
    const sub = this.searchYearForm.controls.year.valueChanges.subscribe(onChange);
    this.onChangeSubs.push(sub);
  }

  registerOnTouched(onTouched: Function){
    this.onTouched = onTouched;
  }
  setDisabledState(disabled: boolean) {
    this.disabled = disabled;
  }

  ngOnInit(): void {
    this.prepareYears();
  }

  private prepareYears() {
    this.years = [
      { value: 2016, year: '2016' },
      { value: 2017, year: '2017' },
      { value: 2018, year: '2018' },
      { value: 2019, year: '2019' },
      { value: 2020, year: '2020' },
      { value: 2021, year: '2021' },
      { value: 2022, year: '2022' },
      { value: 2023, year: '2023' },
      { value: 2024, year: '2024' },
      { value: 2025, year: '2025' }
    ];
  }
}
