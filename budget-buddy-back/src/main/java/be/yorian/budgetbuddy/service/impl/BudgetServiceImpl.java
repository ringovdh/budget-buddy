package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.dto.category.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;
import be.yorian.budgetbuddy.handler.OverviewPerCategoryHandler;
import be.yorian.budgetbuddy.handler.OverviewPerMonthHandler;
import be.yorian.budgetbuddy.handler.OverviewPerYearHandler;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.repository.TransactionRepository;
import be.yorian.budgetbuddy.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BudgetServiceImpl(TransactionRepository transactionRepository,
                             CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year) {
        OverviewPerMonthHandler overviewPerMonthHandler = new OverviewPerMonthHandler(
                transactionRepository,
                month,
                year);
        return overviewPerMonthHandler.createMonthlyBudgetOverview();
    }

    @Override
    public CategoricalBudgetOverview getBudgetOverviewByCategoryAndYear(long categoryId, Optional<Integer> year) {
        OverviewPerCategoryHandler handler = new OverviewPerCategoryHandler(
                transactionRepository,
                categoryRepository,
                categoryId,
                year.orElse(0));
        return handler.createBudgetOverviewPerCategory();
    }

    @Override
    public YearlyBudgetOverview getBudgetOverviewByYear(int year) {
        OverviewPerYearHandler handler = new OverviewPerYearHandler(
                transactionRepository,
                year);
        return handler.createYearlyBudgetOverview();
    }

}
