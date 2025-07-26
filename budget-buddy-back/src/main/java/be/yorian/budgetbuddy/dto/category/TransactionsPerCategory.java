package be.yorian.budgetbuddy.dto.category;

import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Transaction;

import java.util.List;

public class TransactionsPerCategory {

    private final Category category;
    private final List<Transaction> transactions;
    private final double total;

    public TransactionsPerCategory(Category category, List<Transaction> transactions) {
        this.category = category;
        this.transactions = transactions;
        this.total = calculateAndSetTotal();
    }

    public Category getCategory() {
        return category;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getTotal() {
        return total;
    }

    private double calculateAndSetTotal() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmountWithSign)
                .sum();
    }
}
