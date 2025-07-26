package be.yorian.budgetbuddy.dto.category;

import be.yorian.budgetbuddy.entity.Category;

import java.util.List;

public record CategoricalBudgetOverview(
        Category category,
        List<BudgetPerMonthPerCategory> budgetsPerMonth) {
}
