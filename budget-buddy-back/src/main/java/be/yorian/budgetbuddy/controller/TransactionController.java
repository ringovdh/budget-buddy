package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.entity.Transaction;

public interface TransactionController {

    Transaction saveTransaction(Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
	
}
