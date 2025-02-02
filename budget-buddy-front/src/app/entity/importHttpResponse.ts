import {ImportResponse} from "../import/importResponse";

export interface ImportHttpResponse {
  statusCode: number;
  status: string;
  message: string;
  data: { importResponse: ImportResponse };
}
