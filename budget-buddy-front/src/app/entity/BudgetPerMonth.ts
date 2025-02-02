import { Transaction } from "../admin/transaction/transaction";

export interface BudgetPerMonth {
  month: number,
  totalIncomingBudget: number,
  totalFixedOutgoingBudget: number,
  totalOutgoingBudget: number,
  totalSavings: number,
  transactions: Transaction[];
}
