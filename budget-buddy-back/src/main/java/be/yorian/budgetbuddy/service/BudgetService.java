package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.dto.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;

public interface BudgetService {
    MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year);

    CategoricalBudgetOverview getBudgetOverviewByCategory(Long categoryId, int year);

    YearlyBudgetOverview getBudgetOverviewByYear(int year);

}
