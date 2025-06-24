package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.dto.BudgetPerCategory;
import be.yorian.budgetbuddy.dto.GraphData;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.TransactionRepository;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
                formatMonth(year, month),
                createBudgetPerCategoryList(groupedByCategory, year), // data per categorie verzamelen
                retrieveMonthlyGraphData(groupedByCategory), // grafiekdata verzamelen
                retrieveProjectData2(groupedByCategory) // projectdata verzamelen;
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

        List<Integer> sortedDays = new ArrayList<>(cumulativeBudgetMap.keySet());
        for (int i = 1; i < sortedDays.size(); i++) {
            int currentDay = sortedDays.get(i);
            int previousDay = sortedDays.get(i - 1);
            double previousCumulativeTotal = cumulativeBudgetMap.get(previousDay);
            cumulativeBudgetMap.merge(currentDay, previousCumulativeTotal, Double::sum);
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
