import { Transaction } from "../admin/transaction/transaction";
import {BudgetPerCategory} from "../entity/BudgetPerCategory";

export interface ImportResponse {
  newTransactions: Transaction[];
  existingTransactions: BudgetPerCategory[];



}
