import { Transaction } from "../admin/transaction/transaction";

export interface BudgetPerMonthPerCategory {
  month: string,
  total: number,
  transactions: Transaction[];
}
