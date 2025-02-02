import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {MonthlyBudgetOverview} from 'src/app/entity/MonthlyBudgetOverview';
import {BudgetOverviewPerMonth} from "../../entity/BudgetOverviewPerMonth";
import {ResumeData} from "../../entity/ResumeData";
import {GraphData} from "../../entity/GraphData";
import {ProjectData} from "../../entity/ProjectData";

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css',
    '../../../assets/panel_layout.css',
    '../../../assets/resume.layout.css']
})
export class OverviewComponent implements OnChanges {

  chart: any;
  @Input() monthlyBudgetOverview: MonthlyBudgetOverview;

  month: string = '';
  incomingBudget: BudgetOverviewPerMonth[] = [];
  outgoingBudget: BudgetOverviewPerMonth[] = [];
  fixedOutgoingBudget: BudgetOverviewPerMonth[] = [];
  savings: BudgetOverviewPerMonth[] = [];
  totalIncome: number = 0;
  totalFixedCost: number = 0;
  totalOutgoing: number = 0;
  totalSavings: number = 0;
  totalAmount: number = 0;

  showOverview: boolean = false;
  graphData: GraphData;
  projectsData: ProjectData[];
  monthResumeData: ResumeData;

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges) {
    const change = changes['monthlyBudgetOverview'];
    if (change && change.currentValue != undefined) {
      const monthlyBudgetOverview: MonthlyBudgetOverview = change.currentValue;
      this.month = monthlyBudgetOverview.month;
      this.graphData = monthlyBudgetOverview.graphData;
      this.projectsData = change.currentValue.projectsData;
      this.showOverview = monthlyBudgetOverview.budgetsPerCategory.length > 0;
      this.filterCategories(monthlyBudgetOverview);
      this.countTotals();
      this.createResumeData();
    }
  }

  filterCategories = (monthlyBudgetOverview: MonthlyBudgetOverview) => {
    this.fixedOutgoingBudget = monthlyBudgetOverview.budgetsPerCategory
      .filter(o => o.category.fixedcost);
    this.incomingBudget = monthlyBudgetOverview.budgetsPerCategory
      .filter(o => o.category.revenue);
    this.savings = monthlyBudgetOverview.budgetsPerCategory
      .filter(o => o.category.saving);
    this.outgoingBudget = monthlyBudgetOverview.budgetsPerCategory
      .filter(o => (!o.category.fixedcost && !o.category.revenue && !o.category.saving));
  }

  countTotals = () => {
    this.resetTotals();
    this.totalIncome = this.calculateTotal(this.incomingBudget);
    this.totalFixedCost = this.calculateTotal(this.fixedOutgoingBudget);
    this.totalOutgoing = this.calculateTotal(this.outgoingBudget);
    this.totalSavings = -this.calculateTotal(this.savings);
    this.totalAmount = this.totalIncome + this.totalFixedCost + this.totalOutgoing;
  }

  calculateTotal = (transactions: BudgetOverviewPerMonth[]) => {
    return transactions.map(o => o.total)
      .reduce((a, c) => {
        return a + c
      }, 0);
  }

  createResumeData = () => {
    this.monthResumeData = {
      totalIncoming: this.totalIncome,
      totalFixedOutgoing: this.totalFixedCost,
      totalOutgoing: this.totalOutgoing,
      totalSavings: this.totalSavings,
      rest: this.totalAmount
    } as ResumeData
  }

  resetTotals = () => {
    this.totalIncome = 0;
    this.totalOutgoing = 0;
    this.totalFixedCost = 0;
    this.totalSavings = 0;
    this.totalAmount = 0;
  }

}
