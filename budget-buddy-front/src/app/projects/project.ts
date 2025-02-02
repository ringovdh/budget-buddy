import { Transaction } from "../admin/transaction/transaction";

export interface Project {
  id: number;
  projectname: string;
  description: string;
  startdate: Date;
  enddate: Date;
  icon: string
  transactions: Transaction[];
  projectTotal: number;
}
