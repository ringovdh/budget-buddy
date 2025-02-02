import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { BudgetPerMonth } from 'src/app/entity/BudgetPerMonth';
import { YearlyBudgetOverview } from "../../entity/YearlyBudgetOverview";
import { GraphData } from "../../entity/GraphData";
import { ResumeData } from "../../entity/ResumeData";
import { ProjectData } from "../../entity/ProjectData";
import {SavingsData} from "../../entity/SavingsData";

@Component({
  selector: 'app-year-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css',
    '../../../assets/panel_layout.css',
    '../../../assets/resume.layout.css']
})
export class OverviewComponent implements OnChanges {

  @Input() yearlyBudgetOverview: YearlyBudgetOverview;
  @Input() year: number;
  graphData: GraphData;
  savingsData: SavingsData;
  projectsData: ProjectData[];
  budgetsPerMonth: BudgetPerMonth[];
  yearResumeData: ResumeData;

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const change = changes['yearlyBudgetOverview'];
    if (change && change.currentValue != undefined) {
      console.log('sd2', change.currentValue)
      this.graphData = change.currentValue.graphData;
      this.savingsData = change.currentValue.savingsData;
      //this.savingsData.savingAmounts = new Map([...change.currentValue.savingsData.savingAmounts.entries()]);
      this.projectsData = change.currentValue.projectsData;
      this.yearResumeData = this.createResumeData(change.currentValue.budgetsPerMonth);
    }
    console.log('sd', this.savingsData)
  }

  createResumeData(budgetsPerMonth: BudgetPerMonth[]) {
    const totalIncoming = budgetsPerMonth
      .map(o => o.totalIncomingBudget)
      .reduce((a, c) => {
        return a + c
      }, 0);
    const totalFixedOutgoing = budgetsPerMonth
      .map(o => o.totalFixedOutgoingBudget)
      .reduce((a, c) => {
        return a + c
      }, 0);
    const totalOutgoing = budgetsPerMonth
      .map(o => o.totalOutgoingBudget)
      .reduce((a, c) => {
        return a + c
      }, 0);
    const totalSavings = budgetsPerMonth
      .map(o => o.totalSavings)
      .reduce((a, c) => {
        return a + c
      }, 0);

    return {
      totalIncoming: totalIncoming,
      totalFixedOutgoing: totalFixedOutgoing,
      totalOutgoing: totalOutgoing,
      totalSavings: totalSavings,
      rest: totalIncoming + totalFixedOutgoing + totalOutgoing
    };
  }

}
