export interface CustomHttpResponse<T> {

  statusCode: number;
  status: string;
  message: string;
  data: { page: T, budget: T };
}
