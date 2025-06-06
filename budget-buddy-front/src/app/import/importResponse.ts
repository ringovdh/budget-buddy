import { Transaction } from "../admin/transaction/transaction";
import {BudgetPerCategory} from "../entity/BudgetPerCategory";

export interface ImportResponse {
  month: number;
  year: number;
  newTransactions: Transaction[];
  existingTransactions: BudgetPerCategory[];



}
