package be.yorian.budgetbuddy.mother;

import be.yorian.budgetbuddy.dto.category.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.GraphData;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.SavingsData;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;
import be.yorian.budgetbuddy.entity.Category;

import java.util.List;
import java.util.Map;

public class BudgetOverviewMother {

    public static YearlyBudgetOverview emptyYearlyBudgetOverview() {
        return new YearlyBudgetOverview(
                List.of(),
                new GraphData(List.of(), Map.of(), Map.of(), Map.of()),
                new SavingsData(List.of(), Map.of()),
                List.of()
        );
    }

    public static CategoricalBudgetOverview emptyCategoricalBudgetOverview(Category category) {
        return new CategoricalBudgetOverview(
                category,
                List.of()
        );
    }

    public static MonthlyBudgetOverview emptyMonthlyBudgetOverview(String month) {
        return new MonthlyBudgetOverview(
                month,
                List.of(),
                new GraphData(List.of(), Map.of(), Map.of(), Map.of()),
                List.of());
    }
}
