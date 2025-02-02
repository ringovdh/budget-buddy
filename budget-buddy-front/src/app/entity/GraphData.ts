export interface GraphData {
  labels: number[];
  incomingAmounts: Map<number, number>;
  fixedCostAmounts: Map<number, number>;
  otherCostAmounts: Map<number, number>;
}
