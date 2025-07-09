import {Category} from "../admin/category/category";
import {Transaction} from "../admin/transaction/transaction";
import {BudgetPerMonthPerCategory} from "./BudgetPerMonthPerCategory";

export interface CategoricalBudgetOverview {
  category: Category;
  budgetsPerMonth: BudgetPerMonthPerCategory[];
  transactions: Transaction[];
  total: number;
}
