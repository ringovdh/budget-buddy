package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Transaction;

import java.util.List;

public record BudgetPerCategory(
        int year,
        Category category,
        List<Transaction> transactions,
        double total) { }
