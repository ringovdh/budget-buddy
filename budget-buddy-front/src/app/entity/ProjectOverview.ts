import {Project} from "../projects/project";
import {Transaction} from "../admin/transaction/transaction";

export interface ProjectOverview {
  project: Project
  transactions: Transaction[]
  projectTotal: number
}
