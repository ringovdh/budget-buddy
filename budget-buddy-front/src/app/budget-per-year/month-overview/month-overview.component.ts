import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { BudgetPerMonth } from 'src/app/entity/BudgetPerMonth';
import {MonthOverview} from "../../entity/MonthOverview";
import {ResumeData} from "../../entity/ResumeData";

@Component({
  selector: 'app-month-overview',
  templateUrl: './month-overview.component.html',
  styleUrls: ['./month-overview.component.css',
    '../../../assets/panel_layout.css']
})
export class MonthOverviewComponent implements OnChanges {

  @Input() budgetsPerMonth: BudgetPerMonth[];
  monthOverviews: MonthOverview[];

  constructor() { }

  ngOnChanges(changes: SimpleChanges) {
      this.mapToMonthOverviews();
  }

  mapToMonthOverviews() {
    this.monthOverviews = [];
    this.budgetsPerMonth.forEach(bpm => {
      this.monthOverviews.push(
        {
          month: this.mapMonth(bpm.month),
          resumeData: {
            totalIncoming: bpm.totalIncomingBudget,
            totalFixedOutgoing: bpm.totalFixedOutgoingBudget,
            totalOutgoing: bpm.totalOutgoingBudget,
            totalSavings: (bpm.totalSavings * -1), // als inverse tonen
            rest: bpm.totalIncomingBudget + bpm.totalFixedOutgoingBudget + bpm.totalOutgoingBudget
          } as ResumeData
        } as MonthOverview)
    });
  }

  mapMonth(monthNumber: number) {
    switch (monthNumber) {
      case 1:
        return 'januari'
      case 2:
        return 'februari'
      case 3:
        return 'maart'
      case 4:
        return 'april'
      case 5:
        return 'mei'
      case 6:
        return 'juni'
      case 7:
        return 'juli'
      case 8:
        return 'augustus'
      case 9:
        return 'september'
      case 10:
        return 'oktober'
      case 11:
        return 'november'
      case 12:
        return 'december'
      default:
        return ''
    }
  }
}
