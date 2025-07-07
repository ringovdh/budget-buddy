package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.dto.BudgetOverviewPerCategory;
import be.yorian.budgetbuddy.dto.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;

import java.util.List;

public interface BudgetService {
    MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year);

    List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(Long categoryId, int year);

    CategoricalBudgetOverview getBudgetOverviewByCategory(Long categoryId, int year);

    YearlyBudgetOverview getBudgetOverviewByYear(int year);

    YearlyBudgetOverview getBudgetOverviewPerYear(int year);

}
