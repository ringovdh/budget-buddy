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
    public YearlyBudgetOverview getBudgetOverviewPerYear(int year) {
        List<Transaction> transactions = transactionRepository.findByDateContainingYear(year);
        Map<Integer, List<Transaction>> groupedByMonth = transactions.stream()
                .collect(groupingBy(transaction -> transaction.getDate().getMonthValue()));
        List<BudgetPerMonth> budgetPerMonthList = createBudgetPerMonthList(groupedByMonth);
        GraphData graphData = retrieveYearlyGraphData(budgetPerMonthList, year);
        List<ProjectData> projectData = retrieveProjectData(transactions);
        SavingsData savingsData = retrieveSavingsData(transactions, year);
        return new YearlyBudgetOverview(budgetPerMonthList, graphData, savingsData, projectData);
    }

    private List<BudgetPerMonth> createBudgetPerMonthList(Map<Integer, List<Transaction>> groupedByMonth) {
        List<BudgetPerMonth> budgetPerMonthList = new ArrayList<>();
        groupedByMonth.forEach((month, transactions) -> {
            BudgetPerMonth bpm = new BudgetPerMonth(
                    month,
                    calculateTotalIncomingBudget(transactions),
                    calculateTotalFixedOutgoingBudget(transactions),
                    calculateTotalOutgoingBudget(transactions),
                    calculateTotalSavings(transactions),
                    transactions
            );
            budgetPerMonthList.add(bpm);
        });
        return budgetPerMonthList;
    }

    private double calculateTotalSavings(List<Transaction> transactions) {
        return transactions.stream()
                .filter(tx -> tx.category.isSaving())
                .mapToDouble(Transaction::getAmountWithSign)
                .sum() * -1;
    }

    private double calculateTotalOutgoingBudget(List<Transaction> transactions) {
        return transactions.stream()
                .filter(tx -> tx.category.isOtherCost())
                .mapToDouble(Transaction::getAmountWithSign)
                .sum();
    }

    private double calculateTotalFixedOutgoingBudget(List<Transaction> transactions) {
        return transactions.stream()
                .filter(tx -> tx.category.fixedcost)
                .mapToDouble(Transaction::getAmountWithSign)
                .sum();
    }

    private Double calculateTotalIncomingBudget(List<Transaction> transactions) {
        return transactions.stream()
                .filter(tx -> tx.category.revenue)
                .mapToDouble(Transaction::getAmountWithSign)
                .sum();
    }

    private GraphData retrieveYearlyGraphData(List<BudgetPerMonth> budgetPerMonthList, int year) {
        return new GraphData(
                fillMonths(),
                mapIncommingAmountsToGraphData(budgetPerMonthList),
                mapFixedCostAmountsToGraphData(budgetPerMonthList),
                mapOtherCostAmountsToGraphData(budgetPerMonthList));
    }

    private List<ProjectData> retrieveProjectData(List<Transaction> transactions) {
        List<ProjectData> projectDataList = new ArrayList<>();
        transactions.stream()
                .filter(txs -> txs.getProject() != null)
                .collect(groupingBy(Transaction::getProject))
                .forEach((project, txs) -> {
                    double total = txs.stream().mapToDouble(Transaction::getAmountWithSign).sum();
                    projectDataList.add(new ProjectData(project, total));
                });
        return projectDataList;
    }

    private SavingsData retrieveSavingsData(List<Transaction> transactions, int year) {
        List<Integer> days = fillDaysOfYear(year);
        List<Transaction> savings = transactions.stream()
                .filter(txs -> txs.category.isSaving()).toList();

        return new SavingsData(
                days,
                mapSavingsToGraphData(savings, days));
    }

    private Map<Integer, Double> mapSavingsToGraphData(List<Transaction> savings, List<Integer> days) {
        Map<Integer, Double> mappedSavings = new HashMap<>();
        days.forEach(d -> savings.forEach(t -> {
                    if (d == t.getDate().getDayOfYear()) {
                        if (mappedSavings.containsKey(d)) {
                            mappedSavings.merge(d, t.getAmountWithSign(), Double::sum);
                        } else {
                            mappedSavings.put(d, t.getAmountWithSign());
                        }
                    } else {
                        if (!mappedSavings.containsKey(d)) {
                            mappedSavings.put(d, 0.0);
                        }
                    }
                })
        );
        mergeAmountsWithPrevious(mappedSavings);
        return mappedSavings;
    }

    private Map<Integer, Double> mapOtherCostAmountsToGraphData(List<BudgetPerMonth> budgetList) {
        Map<Integer, Double> mappedBudget = new HashMap<>();
        fillMonths().forEach(month -> mappedBudget.put(month, 0.0));
        budgetList.forEach(bpm -> mappedBudget.put(bpm.month(), -bpm.totalOutgoingBudget()));
        mergeAmountsWithPrevious(mappedBudget);
        return mappedBudget;
    }

    private Map<Integer, Double> mapFixedCostAmountsToGraphData(List<BudgetPerMonth> budgetList) {
        Map<Integer, Double> mappedBudget = new HashMap<>();
        fillMonths().forEach(month -> mappedBudget.put(month, 0.0));
        budgetList.forEach(bpm -> mappedBudget.put(bpm.month(), -bpm.totalFixedOutgoingBudget()));
        mergeAmountsWithPrevious(mappedBudget);
        return mappedBudget;
    }

    private Map<Integer, Double> mapIncommingAmountsToGraphData(List<BudgetPerMonth> budgetList) {
        Map<Integer, Double> mappedBudget = new TreeMap<>();
        fillMonths().forEach(month -> mappedBudget.put(month, 0.0));
        budgetList.forEach(bpm -> mappedBudget.put(bpm.month(), bpm.totalIncomingBudget()));
        mergeAmountsWithPrevious(mappedBudget);
        return mappedBudget;
    }

    private void mergeAmountsWithPrevious(Map<Integer, Double> amounts) {
        amounts.forEach((key, amount) -> {
            if (amounts.size() > 1 && key != 1) {
                amounts.merge(key, amounts.get(key - 1), Double::sum);
            }
        });
    }

    private List<Integer> fillDaysOfYear(int year) {
        List<Integer> days = new ArrayList<>();
        int lengthOfYear = Year.of(year).length();
        for (int i = 1; i <= lengthOfYear; i++) {
            days.add(i);
        }
        return days;
    }

    private List<Integer> fillMonths() {
        return List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    }

}
