import {Category} from "../category/category";

export interface Comment {
  id: number;
  searchterm: string;
  replacement: string;
  category: Category;
}
