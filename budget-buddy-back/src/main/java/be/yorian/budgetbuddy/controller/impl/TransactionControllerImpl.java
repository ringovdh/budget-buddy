package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.controller.TransactionController;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionControllerImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    @PostMapping()
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @Override
    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable("id") Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransaction(id);
    }

}
