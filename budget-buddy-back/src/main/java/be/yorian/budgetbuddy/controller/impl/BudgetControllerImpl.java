package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.controller.BudgetController;
import be.yorian.budgetbuddy.dto.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;
import be.yorian.budgetbuddy.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static java.time.LocalDate.now;

@RestController
@RequestMapping("/budgets")
public class BudgetControllerImpl implements BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetControllerImpl(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Override
    @GetMapping(produces = "application/json", path="/budget-by-month")
    public ResponseEntity<MonthlyBudgetOverview> getBudgetOverviewPerMonth(@RequestParam Optional<Integer>month,
                                                                           @RequestParam Optional<Integer>year) {
        MonthlyBudgetOverview budgetOverviewPerMonth = budgetService.getBudgetOverviewPerMonth(month.orElse(now().getMonthValue()), year.orElse(now().getYear()));
        return ResponseEntity.ok(budgetOverviewPerMonth);
    }

    @Override
    @GetMapping(produces = "application/json", path="/budget-by-category/{categoryId}")
        public ResponseEntity<CategoricalBudgetOverview> getBudgetOverviewByCategory(@PathVariable long categoryId,
                                                                                 @RequestParam Optional<Integer>year) {
        CategoricalBudgetOverview overview;
        if (year.isEmpty()) {
            overview = budgetService.getBudgetOverviewByCategory(categoryId);
        } else {
            overview = budgetService.getBudgetOverviewByCategoryAndYear(categoryId, year.get());
        }
        return ResponseEntity.ok(overview);
    }

    @Override
    @GetMapping(produces = "application/json", path="/budget-by-year/{year}")
    public ResponseEntity<YearlyBudgetOverview> getBudgetOverviewByYear(@PathVariable int year) {
        return ResponseEntity.ok(budgetService.getBudgetOverviewByYear(year));
    }
}
