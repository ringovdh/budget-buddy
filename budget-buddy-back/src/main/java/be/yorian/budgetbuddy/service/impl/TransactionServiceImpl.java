package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.TransactionRepository;
import be.yorian.budgetbuddy.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static be.yorian.budgetbuddy.mapper.TransactionMapper.mapTransaction;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;


    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transaction_not_found"));
        return transactionRepository.save(mapTransaction(transaction, updatedTransaction));
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

}
