package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.repository.CategoryRepository;

import java.util.List;

public record CategoricalBudgetOverview(
        Category category,
        List<BudgetPerMonthPerCategory> budgetsPerMonth) {
}
