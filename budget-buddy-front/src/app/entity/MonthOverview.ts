import {BudgetPerMonth} from "./BudgetPerMonth";
import {GraphData} from "./GraphData";
import {ResumeComponent} from "../budget-per-month/resume/resume.component";
import {ResumeData} from "./ResumeData";

export interface MonthOverview {
  month: String
  resumeData: ResumeData
}
