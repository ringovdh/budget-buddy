package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.dto.category.BudgetPerMonthPerCategory;
import be.yorian.budgetbuddy.dto.category.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class OverviewPerCategoryHandler extends OverviewHandler {

    private final CategoryRepository categoryRepository;
    private final Long categoryId;
    private final int year;

    public OverviewPerCategoryHandler(TransactionRepository transactionRepository,
                                      CategoryRepository categoryRepository,
                                      Long categoryId, int year) {
        super(transactionRepository);
        this.categoryRepository = categoryRepository;
        this.categoryId = categoryId;
        this.year = year;
    }


    public CategoricalBudgetOverview createBudgetOverviewPerCategory() {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);

        // alle transactie ophalen en groeperen
        Map<YearMonth, List<Transaction>> groupedByMonth = groupTransactionsForCategory();
        return new CategoricalBudgetOverview(
                category,
                createBudgetPerMonthList(groupedByMonth) // data per maand verzamelen
        );
    }

    private List<BudgetPerMonthPerCategory> createBudgetPerMonthList(Map<YearMonth, List<Transaction>> groupedByMonth) {
        return groupedByMonth.entrySet().stream()
                .map(entry -> new BudgetPerMonthPerCategory(
                    formatMonth(entry.getKey()),
                    calculateTotalBudget(entry.getValue()),
                    entry.getValue()
            )).toList();
    }

    private double calculateTotalBudget(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmountWithSign)
                .sum();
    }

    private Map<YearMonth, List<Transaction>> groupTransactionsForCategory() {
        List<Transaction> transactions;
        if (year == 0) {
            transactions = transactionRepository.findByCategoryId(categoryId);
        } else {
            transactions = transactionRepository.findByCategoryIdAndDateContainingYear(categoryId, year);
        }
        return transactions.stream()
                .collect(groupingBy(t -> YearMonth.of(t.getDate().getYear(), t.getDate().getMonth().getValue())));
    }
}
