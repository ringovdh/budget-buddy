package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.dto.BudgetOverviewPerCategory;
import be.yorian.budgetbuddy.dto.BudgetPerMonth;
import be.yorian.budgetbuddy.dto.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.dto.GraphData;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.ProjectData;
import be.yorian.budgetbuddy.dto.SavingsData;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;

import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.handler.OverviewPerCategoryHandler;
import be.yorian.budgetbuddy.handler.OverviewPerMonthHandler;
import be.yorian.budgetbuddy.handler.OverviewPerYearHandler;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.repository.TransactionRepository;
import be.yorian.budgetbuddy.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;

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
    public CategoricalBudgetOverview getBudgetOverviewByCategory(Long categoryId, int year) {
        OverviewPerCategoryHandler handler = new OverviewPerCategoryHandler(
                transactionRepository,
                categoryRepository,
                categoryId,
                year);
        return handler.createBudgetOverviewPerCategory();
    }

    @Override
    @Deprecated
    public List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(Long categoryId, int year) {
        List<BudgetOverviewPerCategory> dtos = new ArrayList<>();
        List<Transaction> transactions;
        if (year == 0) {
            transactions = transactionRepository.findByCategoryId(categoryId);
        } else {
            transactions = transactionRepository.findByCategoryIdAndDateContainingYear(categoryId, year);
        }
        transactions.stream()
                .collect(groupingBy(t -> t.getDate().getYear()))
                .forEach((y, txs) -> {
                    BudgetOverviewPerCategory dto = new BudgetOverviewPerCategory();
                    dto.setYear(y);
                    dto.setTransactions(txs);
                    dto.calculateAndSetTotal(txs);
                    dtos.add(dto);
                });
        return dtos;
    }

    @Override
    public YearlyBudgetOverview getBudgetOverviewByYear(int year) {
        OverviewPerYearHandler handler = new OverviewPerYearHandler(
                transactionRepository,
                year);
        return handler.createYearlyBudgetOverview();
    }

}
