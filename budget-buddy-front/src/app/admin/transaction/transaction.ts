import {Category} from "../category/category";
import { Project } from "../../projects/project";

export interface Transaction {
  tx_id: number;
  number: string;
  amount: number;
  sign: string;
  date: Date;
  comment: string;
  originalComment: string;
  category: Category;
  project: Project;
}
