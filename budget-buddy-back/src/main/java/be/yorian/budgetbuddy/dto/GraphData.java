package be.yorian.budgetbuddy.dto;

import java.util.List;
import java.util.Map;

public record GraphData(
        List<Integer> labels,
        Map<Integer, Double> incomingAmounts,
        Map<Integer, Double> fixedCostAmounts,
        Map<Integer, Double> otherCostAmounts) { }
