package be.yorian.budgetbuddy.mapper;

import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;

public class TransactionMapper {

    public static Transaction mapDtoToTransaction(TransactionDTO transactionDTO) {
        Transaction tx = new Transaction();
        tx.setNumber(transactionDTO.number());
        tx.setOriginalComment(transactionDTO.comment());
        tx.setAmount(transactionDTO.amount());
        tx.setSign(transactionDTO.sign());
        tx.setDate(transactionDTO.date());

        return tx;
    }

    public static Transaction mapTransaction(Transaction existingTransaction, Transaction newTransaction) {
        existingTransaction.setNumber(newTransaction.getNumber());
        existingTransaction.setDate(newTransaction.getDate());
        existingTransaction.setComment(newTransaction.getComment());
        existingTransaction.setSign(newTransaction.getSign());
        existingTransaction.setAmount(newTransaction.getAmount());
        existingTransaction.setCategory(newTransaction.getCategory());
        existingTransaction.setProject(newTransaction.getProject());

        return existingTransaction;
    }

}
