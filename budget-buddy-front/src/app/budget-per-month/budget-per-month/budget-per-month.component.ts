import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MonthlyBudgetOverview} from 'src/app/entity/MonthlyBudgetOverview';
import {BudgetPerMonthService} from "../budgetPerMonth.service";

@Component({
  selector: 'app-budget',
  templateUrl: './budget-per-month.component.html',
  styleUrls: ['./budget-per-month.component.css',
    '../../../assets/panel_layout.css']
})
export class BudgetPerMonthComponent implements OnInit {

  monthlyBudgetOverview: MonthlyBudgetOverview;
  months: { value: number, name: string }[];
  searchForm!: FormGroup;

  constructor(public budgetService: BudgetPerMonthService) {
  }

  ngOnInit(): void {
    this.prepareMonths();
    this.createSearchForm();
  }

  submit() {
    this.budgetService.getMonthlyBudgetOverview(this.searchForm.get("month").value, this.searchForm.get("year").value)
      .subscribe((data) => {
        this.monthlyBudgetOverview = data;
      });
  }

  private createSearchForm() {
    this.searchForm = new FormGroup({
      month: new FormControl('', Validators.required),
      year: new FormControl('')
    })
  }

  private prepareMonths() {
    this.months = [
      {value: 1, name: 'januari'},
      {value: 2, name: 'februari'},
      {value: 3, name: 'maart'},
      {value: 4, name: 'april'},
      {value: 5, name: 'mei'},
      {value: 6, name: 'juni'},
      {value: 7, name: 'juli'},
      {value: 8, name: 'augustus'},
      {value: 9, name: 'september'},
      {value: 10, name: 'oktober'},
      {value: 11, name: 'november'},
      {value: 12, name: 'december'},
    ];
  }

}
