import {BudgetPerMonth} from "./BudgetPerMonth";
import {GraphData} from "./GraphData";
import {ProjectData} from "./ProjectData";
import {SavingsData} from "./SavingsData";

export interface YearlyBudgetOverview {
  budgetsPerMonth: BudgetPerMonth[],
  graphData: GraphData,
  savingsData: SavingsData,
  projectsData: ProjectData[]
}
