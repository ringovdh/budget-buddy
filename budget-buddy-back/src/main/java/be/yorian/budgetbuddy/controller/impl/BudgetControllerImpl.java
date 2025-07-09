package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.controller.BudgetController;
import be.yorian.budgetbuddy.dto.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;
import be.yorian.budgetbuddy.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/budgets")
public class BudgetControllerImpl implements BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetControllerImpl(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Override
    @GetMapping(produces = "application/json", path="/period")
    public ResponseEntity<MonthlyBudgetOverview> getBudgetOverviewPerMonth(@RequestParam Optional<Integer>month,
                                                                           @RequestParam Optional<Integer>year) {
        MonthlyBudgetOverview budgetOverviewPerMonth = budgetService.getBudgetOverviewPerMonth(month.orElse(1), year.orElse(2025));
        return ResponseEntity.ok().body(budgetOverviewPerMonth);
    }

    @Override
    @GetMapping(produces = "application/json", path="/categories")
    public ResponseEntity<CategoricalBudgetOverview> getBudgetOverviewByCategory(@RequestParam Optional<Long>categoryId,
                                                                                 @RequestParam Optional<Integer>year) {
        return ResponseEntity.ok().body(budgetService.getBudgetOverviewByCategory(categoryId.orElse(0L), year.orElse(0)));
    }

    @Override
    @GetMapping(produces = "application/json", path="/year")
    public ResponseEntity<YearlyBudgetOverview> getBudgetOverviewByYear(@RequestParam Optional<Integer>year) {
        return ResponseEntity.ok().body(budgetService.getBudgetOverviewByYear(year.orElse(2025)));
    }
}
