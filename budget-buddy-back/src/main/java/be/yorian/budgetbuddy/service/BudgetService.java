package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.dto.category.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;

public interface BudgetService {
    MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year);

    CategoricalBudgetOverview getBudgetOverviewByCategory(long categoryId);

    CategoricalBudgetOverview getBudgetOverviewByCategoryAndYear(long categoryId, int year);

    YearlyBudgetOverview getBudgetOverviewByYear(int year);

}
