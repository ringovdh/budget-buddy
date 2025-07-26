package be.yorian.budgetbuddy.dto.category;

import be.yorian.budgetbuddy.entity.Transaction;

import java.util.List;

public record BudgetPerMonthPerCategory(
        String month,
        double total,
        List<Transaction> transactions) { }
