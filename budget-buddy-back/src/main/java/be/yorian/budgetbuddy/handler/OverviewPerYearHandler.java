package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.dto.BudgetPerMonth;
import be.yorian.budgetbuddy.dto.GraphData;
import be.yorian.budgetbuddy.dto.ProjectData;
import be.yorian.budgetbuddy.dto.SavingsData;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.TransactionRepository;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class OverviewPerYearHandler extends OverviewHandler {

    private final int year;
    private final List<Integer> months;
    private final List<Integer> days;

    public OverviewPerYearHandler(TransactionRepository transactionRepository, int year) {
        super(transactionRepository);
        this.year = year;
        this.months = fillMonths();
        this.days = fillDaysOfYear();
    }

    public YearlyBudgetOverview createYearlyBudgetOverview() {
        // alle transactie ophalen en groeperen per maand
        Map<Integer, List<Transaction>> groupedByMonth = groupTransactionsForYear();
        List<BudgetPerMonth> budgetsPerMonth = createBudgetPerMonthList(groupedByMonth);
        return new YearlyBudgetOverview(
                budgetsPerMonth, // data per maand verzamelen
                retrieveYearlyGraphData(budgetsPerMonth), // grafiekdata verzamelen
                retrieveSavingsData(groupedByMonth), // spaardata verzamelen
                retrieveProjectData(groupedByMonth) // projectdata verzamelen
        );
    }

    private Map<Integer, List<Transaction>> groupTransactionsForYear() {
        return transactionRepository.findByDateContainingYear(this.year).stream()
                .collect(groupingBy(transaction -> transaction.getDate().getMonthValue()));
    }

    private List<BudgetPerMonth> createBudgetPerMonthList(Map<Integer, List<Transaction>> groupedByMonth) {
        return groupedByMonth.entrySet().stream()
                .map(entry -> new BudgetPerMonth(
                        entry.getKey(),
                        calculateTotalBudget(entry.getValue(), Category::isRevenue),
                        calculateTotalBudget(entry.getValue(), Category::isFixedcost),
                        calculateTotalBudget(entry.getValue(), Category::isOtherCost),
                        calculateTotalBudget(entry.getValue(), Category::isSaving),
                        entry.getValue())
                ).sorted(Comparator.comparing(BudgetPerMonth::month))
                .toList();
    }

    private double calculateTotalBudget(List<Transaction> transactions,
                                        Predicate<Category> categoryFilter) {
        return transactions.stream()
                .filter(tx -> categoryFilter.test(tx.category))
                .mapToDouble(Transaction::getAmountWithSign)
                .sum();
    }

    private GraphData retrieveYearlyGraphData(List<BudgetPerMonth> budgetsPerMonth) {
        return new GraphData(
                this.months,
                mapBudget(budgetsPerMonth, BudgetPerMonth::totalIncomingBudget),
                mapBudget(budgetsPerMonth,  BudgetPerMonth::totalFixedOutgoingBudget),
                mapBudget(budgetsPerMonth, BudgetPerMonth::totalOutgoingBudget));
    }

    private List<ProjectData> retrieveProjectData(Map<Integer, List<Transaction>> groupedByMonth) {
        return groupedByMonth.values().stream().flatMap(List::stream)
                .filter(txs -> txs.getProject() != null)
                .collect(groupingBy(Transaction::getProject, summingDouble(Transaction::getAmountWithSign)))
                .entrySet().stream()
                .map(entry -> new ProjectData(entry.getKey(), entry.getValue()))
                .toList();
    }

    private SavingsData retrieveSavingsData(Map<Integer, List<Transaction>> groupedByMonth) {
        List<Transaction> savings = groupedByMonth.values().stream().flatMap(List::stream)
                .filter(txs -> txs.category.isSaving()).toList();

        return new SavingsData(
                this.days,
                mapSavingsToGraphData(savings));
    }

    private Map<Integer, Double> mapSavingsToGraphData(List<Transaction> savings) {
        Map<Integer, Double> dailyTotals = new TreeMap<>();
        this.days.forEach(day -> dailyTotals.put(day, 0.0));

        savings.forEach(tx -> {
            int dayOfYear = tx.getDate().getDayOfYear();
            dailyTotals.merge(dayOfYear, tx.getAmountWithSign(), Double::sum);
        });

        return calculateBudgetMap(dailyTotals);
    }

    private Map<Integer, Double> mapBudget(List<BudgetPerMonth> budgetsPerMonth,
                                           Function<BudgetPerMonth, Double> amountMapper) {
        Map<Integer, Double> budgetMap = new HashMap<>();
        months.forEach(month -> budgetMap.put(month, 0.0));

        budgetsPerMonth.forEach(bpm -> {
            double amountToAdd = amountMapper.apply(bpm);
            budgetMap.merge(bpm.month(), amountToAdd, Double::sum);
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

    private List<Integer> fillDaysOfYear() {
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
