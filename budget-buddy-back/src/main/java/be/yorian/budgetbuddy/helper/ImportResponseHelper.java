package be.yorian.budgetbuddy.helper;

import be.yorian.budgetbuddy.dto.ImportTransactionsResponse;
import be.yorian.budgetbuddy.dto.TransactionsPerCategory;
import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.CommentRepository;
import be.yorian.budgetbuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

@Component
public class ImportResponseHelper {

    private final TransactionRepository transactionRepository;
    private final CommentRepository commentRepository;
    private final List<Comment> comments = new ArrayList<>();
    private final ImportTransactionsResponse response = new ImportTransactionsResponse();

    @Autowired
    public ImportResponseHelper(TransactionRepository transactionRepository,
                                CommentRepository commentRepository)
    {
        this.transactionRepository = transactionRepository;
        this.commentRepository = commentRepository;
    }


    public ImportTransactionsResponse createImportResponse(List<Transaction> transactions) {
        loadAllComments();
        filterNewTransactions(transactions);

        return response;
    }

    private void loadAllComments() {
        comments.addAll(commentRepository.findAll());
    }

    private void filterNewTransactions(List<Transaction> transactions) {
        List<Transaction> newTransactions = new ArrayList<>();
        List<Transaction> existingTransactions = new ArrayList<>();
        transactions.forEach(tx -> transactionRepository.findByDateAndNumber(tx.getDate(), tx.getNumber())
                .ifPresentOrElse(
                        existingTransactions::add,
                        () -> newTransactions.add(handleNewTransaction(tx))
                ));
        Comparator<Transaction> categoryComparator = comparing(tx -> tx.getCategory() != null ? tx.getCategory().getLabel() : "");
        newTransactions.sort(categoryComparator.reversed());

        this.response.setExistingTransactions(createBudgetOverview(existingTransactions));
        this.response.setNewTransactions(newTransactions);
    }

    private Transaction handleNewTransaction(Transaction transaction) {
        setPredefinedCommentAndCategory(transaction);
        return transaction;
    }

    private List<TransactionsPerCategory> createBudgetOverview(List<Transaction> existingTransactions) {
        List<TransactionsPerCategory> budgetOverview = new ArrayList<>();
        existingTransactions.stream()
                .collect(groupingBy(Transaction::getCategory))
                .forEach((category, transactions) ->
                    budgetOverview.add(new TransactionsPerCategory(category, transactions))
                );

        return budgetOverview;
    }

    private void setPredefinedCommentAndCategory(Transaction tx) {
        comments.forEach(comment -> {
            if (tx.originalComment.toLowerCase().contains(comment.getSearchterm())) {
                tx.setComment(comment.getReplacement());
                tx.setCategory(comment.getCategory());
            }
        });
    }

}