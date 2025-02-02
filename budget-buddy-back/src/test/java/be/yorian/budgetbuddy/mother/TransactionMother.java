package be.yorian.budgetbuddy.mother;

import be.yorian.budgetbuddy.entity.Transaction;

import static be.yorian.budgetbuddy.mother.CategoryMother.category;
import static java.time.LocalDate.now;

public class TransactionMother {

    public static Transaction newTransaction() {
        Transaction transaction = new Transaction();
        transaction.setNumber("0002");
        transaction.setAmount(Double.valueOf("100"));
        transaction.setSign("-");
        transaction.setDate(now());
        transaction.setOriginalComment("Transaction for testing");
        return transaction;
    }

    public static Transaction existingTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTx_id(1);
        transaction.setNumber("0001");
        transaction.setAmount(Double.valueOf("10"));
        transaction.setSign("-");
        transaction.setDate(now());
        transaction.setCategory(category());
        transaction.setComment("Transaction for testing");
        return transaction;
    }
}
