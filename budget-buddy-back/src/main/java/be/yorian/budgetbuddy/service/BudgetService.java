package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.dto.category.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;

import java.util.Optional;

public interface BudgetService {
    MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year);

    CategoricalBudgetOverview getBudgetOverviewByCategoryAndYear(long categoryId, Optional<Integer> year);

    YearlyBudgetOverview getBudgetOverviewByYear(int year);

}
