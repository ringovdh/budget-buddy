import {BudgetPerMonth} from "./BudgetPerMonth";

export interface BudgetOverviewPerYear {
  month: number;
  transactionsPerMonth: BudgetPerMonth[];
}
