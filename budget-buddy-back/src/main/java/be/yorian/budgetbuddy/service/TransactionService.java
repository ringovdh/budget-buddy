package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.entity.Transaction;

public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
}
