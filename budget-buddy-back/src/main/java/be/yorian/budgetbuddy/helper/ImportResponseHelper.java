package be.yorian.budgetbuddy.helper;

import be.yorian.budgetbuddy.dto.ImportTransactionsResponse;
import be.yorian.budgetbuddy.dto.TransactionsPerCategory;
import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.CommentRepository;
import be.yorian.budgetbuddy.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class ImportResponseHelper {

    private final TransactionRepository transactionRepository;
    private final CommentRepository commentRepository;
    private final List<Comment> comments = new ArrayList<>();
    private final List<Transaction> transactions;
    private final ImportTransactionsResponse response;
    private final List<Transaction> newTransactions = new ArrayList<>();
    private final List<Transaction> existingTransactions = new ArrayList<>();

    public ImportResponseHelper(TransactionRepository transactionRepository,
                                CommentRepository commentRepository,
                                List<Transaction> transactions)
    {
        this.transactionRepository = transactionRepository;
        this.commentRepository = commentRepository;
        this.transactions = transactions;
        this.response = new ImportTransactionsResponse();
    }


    public ImportTransactionsResponse createImportResponse() {
        loadAllComments();
        filterNewTransactions();

        return response;
    }

    private void loadAllComments() {
        comments.addAll(commentRepository.findAll());
    }

    private void filterNewTransactions() {
        transactions.forEach(tx -> {
            transactionRepository.findByDateAndNumber(tx.getDate(), tx.getNumber())
                    .ifPresentOrElse(
                            existingTransactions::add,
                            () -> handleNewTransaction(tx)
                    );
        });

        newTransactions.sort(Comparator.comparing(Transaction::getDate));

        this.response.setExistingTransactions(createBudgetOverview(existingTransactions));
        this.response.setNewTransactions(newTransactions);
    }

    private void handleNewTransaction(Transaction transaction) {
        setPredefinedCommentAndCategory(transaction);
        newTransactions.add(transaction);
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