import {Category} from "../admin/category/category";
import {Transaction} from "../admin/transaction/transaction";

export interface BudgetPerCategory {
  year: number;
  category: Category;
  transactions: Transaction[];
  total: number;
}
