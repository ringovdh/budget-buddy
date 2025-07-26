package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.dto.category.BudgetPerCategory;
import be.yorian.budgetbuddy.dto.GraphData;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.ProjectData;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.TransactionRepository;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;

public class OverviewPerMonthHandler extends OverviewHandler {
    private final int month;
    private final int year;
    private final List<Integer> days;

    public OverviewPerMonthHandler(TransactionRepository transactionRepository
            , int month, int year) {
        super(transactionRepository);
        this.month = month;
        this.year = year;
        this.days = fillDaysOfMonth(month, year);
    }


    public MonthlyBudgetOverview createMonthlyBudgetOverview() {
        // alle transactie ophalen en groeperen
        Map<Category, List<Transaction>> groupedByCategory = groupTransactionsForMonth(month, year);
        return new MonthlyBudgetOverview(
                formatMonth(YearMonth.of(year, month)),
                createBudgetPerCategoryList(groupedByCategory, year), // data per categorie verzamelen
                retrieveMonthlyGraphData(groupedByCategory), // grafiekdata verzamelen
                retrieveProjectData(groupedByCategory) // projectdata verzamelen;
        );
    }

    private Map<Category, List<Transaction>> groupTransactionsForMonth(int month, int year) {
        return transactionRepository.findByDateContainingYearAndMonth(month, year)
                .stream().collect(groupingBy(Transaction::getCategory));
    }

    private GraphData retrieveMonthlyGraphData(Map<Category, List<Transaction>> groupedByCategory) {
        return new GraphData(
                days,
                handleBudget(groupedByCategory, Category::isRevenue, Transaction::getAmountWithSign),
                handleBudget(groupedByCategory, Category::isFixedcost, Transaction::getAmountWithSign),
                handleBudget(groupedByCategory, Category::isOtherCost, Transaction::getAmountWithSign)
        );
    }

    private List<ProjectData> retrieveProjectData(Map<Category, List<Transaction>> groupedByCategory) {
        List<ProjectData> projectDataList = new ArrayList<>();
        groupedByCategory.values().stream().flatMap(List::stream)
                .filter(txs -> txs.getProject() != null)
                .collect(groupingBy(Transaction::getProject))
                .forEach((project, txs) -> {
                    double total = txs.stream().mapToDouble(Transaction::getAmountWithSign).sum();
                    projectDataList.add(new ProjectData(project, total));
                });
        return projectDataList;
    }

    private Map<Integer, Double> handleBudget(Map<Category, List<Transaction>> groupedByCategory,
                                              Predicate<Category> categoryFilter,
                                              Function<Transaction, Double> amountMapper) {
        Map<Integer, Double> budgetMap = new HashMap<>();
        days.forEach(day -> budgetMap.put(day, 0.0));

        groupedByCategory.forEach((category, txs) -> {
            if (categoryFilter.test(category)) {
                txs.forEach(tx -> {
                    int dayOfMonth = tx.getDate().getDayOfMonth();
                    double amountToAdd = amountMapper.apply(tx);
                    budgetMap.merge(dayOfMonth, amountToAdd, Double::sum);
                });
            }
        });
        return calculateBudgetMap(budgetMap);
    }

    private Map<Integer, Double> calculateBudgetMap(Map<Integer, Double> budgetMap) {
        Map<Integer, Double> cumulativeBudgetMap = new TreeMap<>(budgetMap);
        double total = 0.0;

        for (Map.Entry<Integer, Double> entry : cumulativeBudgetMap.entrySet()) {
            total += entry.getValue();
            entry.setValue(total);
        }

        return cumulativeBudgetMap;
    }

    private List<Integer> fillDaysOfMonth(int month, int year) {
        List<Integer> days = new ArrayList<>();
        int lengthOfMonth = YearMonth.of(year, month).lengthOfMonth();
        for (int i = 1; i <= lengthOfMonth; i++) {
            days.add(i);
        }
        return days;
    }

    private List<BudgetPerCategory> createBudgetPerCategoryList(Map<Category, List<Transaction>> groupedByCategory, int year) {
        List<BudgetPerCategory> budgetPerCategoryList = new ArrayList<>();
        groupedByCategory.forEach((category, transactions) -> {
            BudgetPerCategory bpc = new BudgetPerCategory(
                    year,
                    category,
                    transactions,
                    calculateTotalForCategory(transactions)
            );
            budgetPerCategoryList.add(bpc);
        });
        return budgetPerCategoryList;
    }

    private double calculateTotalForCategory(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmountWithSign)
                .sum();
    }

}
