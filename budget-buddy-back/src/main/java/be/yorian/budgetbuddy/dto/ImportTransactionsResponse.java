package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.dto.category.TransactionsPerCategory;
import be.yorian.budgetbuddy.entity.Transaction;

import java.util.List;

public class ImportTransactionsResponse {

    private List<Transaction> newTransactions;
    private List<TransactionsPerCategory> existingTransactions;


    public List<Transaction> getNewTransactions() {
        return newTransactions;
    }

    public void setNewTransactions(List<Transaction> newTransactions) {
        this.newTransactions = newTransactions;
    }


    public List<TransactionsPerCategory> getExistingTransactions() {
        return existingTransactions;
    }

    public void setExistingTransactions(List<TransactionsPerCategory> existingTransactions) {
        this.existingTransactions = existingTransactions;
    }

}
