package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.dto.BudgetPerMonthPerCategory;
import be.yorian.budgetbuddy.dto.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
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
        // alle transactie ophalen en groeperen
        Map<String, List<Transaction>> groupedByMonth = groupTransactionsForCategory();
        return new CategoricalBudgetOverview(
                categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new),
                createBudgetPerMonthList(groupedByMonth) // data per maand verzamelen
        );
    }

    private List<BudgetPerMonthPerCategory> createBudgetPerMonthList(Map<String, List<Transaction>> groupedByMonth) {
        List<BudgetPerMonthPerCategory> budgetPerMonthList = new ArrayList<>();
        groupedByMonth.forEach((month, transactions) -> {
            BudgetPerMonthPerCategory bpmpc = new BudgetPerMonthPerCategory(
                    month,
                    calculateTotalBudget(transactions),
                    transactions
            );
            budgetPerMonthList.add(bpmpc);
        });
        return budgetPerMonthList;
    }

    private double calculateTotalBudget(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmountWithSign)
                .sum();
    }

    private Map<String, List<Transaction>> groupTransactionsForCategory() {
        List<Transaction> transactions;
        if (year == 0) {
            transactions = transactionRepository.findByCategoryId(categoryId);
        } else {
            transactions = transactionRepository.findByCategoryIdAndDateContainingYear(categoryId, year);
        }
        Map<String, List<Transaction>> groupedByMonth = transactions.stream()
                .collect(groupingBy(t -> formatMonth(t.getDate().getYear(), t.getDate().getMonth().getValue())));
        return groupedByMonth;
    }
}
