export interface BudgetHttpResponse<T> {
  statusCode: number;
  status: string;
  message: string;
  data: { budget: T };
}
