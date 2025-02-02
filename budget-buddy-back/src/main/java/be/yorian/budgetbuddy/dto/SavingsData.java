package be.yorian.budgetbuddy.dto;

import java.util.List;
import java.util.Map;

public record SavingsData(
        List<Integer> labels,
        Map<Integer, Double> savingAmounts) { }
