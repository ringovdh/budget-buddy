package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.dto.BudgetOverviewPerCategory;
import be.yorian.budgetbuddy.dto.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BudgetController {

    ResponseEntity<MonthlyBudgetOverview> getBudgetOverviewPerMonth(Optional<Integer> month,
                                                                    Optional<Integer> year);

    ResponseEntity<List<BudgetOverviewPerCategory>> getBudgetOverviewPerCategory(Optional<Long> categoryId,
                                                                                 Optional<Integer> year);

    ResponseEntity<CategoricalBudgetOverview> getBudgetOverviewByCategory(Optional<Long> categoryId,
                                                                           Optional<Integer> year);

    ResponseEntity<YearlyBudgetOverview> getBudgetOverviewPerYear(Optional<Integer> year);

}
