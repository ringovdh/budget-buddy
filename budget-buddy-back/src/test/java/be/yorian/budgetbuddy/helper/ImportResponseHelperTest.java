package be.yorian.budgetbuddy.helper;

import be.yorian.budgetbuddy.dto.ImportTransactionsResponse;
import be.yorian.budgetbuddy.dto.TransactionsPerCategory;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.CommentRepository;
import be.yorian.budgetbuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static be.yorian.budgetbuddy.mother.CommentMother.comment;
import static be.yorian.budgetbuddy.mother.TransactionMother.existingTransaction;
import static be.yorian.budgetbuddy.mother.TransactionMother.newTransaction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImportResponseHelperTest {

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    CommentRepository commentRepository;


    @Test
    void createImportResponseWithNewTransactions() {
        when(commentRepository.findAll()).thenReturn(List.of());
        when(transactionRepository.findByDateAndNumber(any(), any())).thenReturn(Optional.empty());
        ImportResponseHelper importResponseHelper =
                new ImportResponseHelper(transactionRepository,
                        commentRepository,
                        List.of(newTransaction()));
        ImportTransactionsResponse response = importResponseHelper.createImportResponse();
        assertThat(response.getExistingTransactions()).isEmpty();
        assertThat(response.getNewTransactions().size()).isEqualTo(1);
        assertThat(response.getNewTransactions().getFirst().getOriginalComment()).isEqualTo("Transaction for testing");
        assertThat(response.getNewTransactions().getFirst().getComment()).isNull();
    }

    @Test
    void createImportResponseWithNewTransactionsAndPredefinedComment() {
        when(commentRepository.findAll()).thenReturn(List.of(comment()));
        when(transactionRepository.findByDateAndNumber(any(), any())).thenReturn(Optional.empty());
        ImportResponseHelper importResponseHelper =
                new ImportResponseHelper(transactionRepository,
                        commentRepository,
                        List.of(newTransaction()));
        ImportTransactionsResponse response = importResponseHelper.createImportResponse();
        assertThat(response.getExistingTransactions()).isEmpty();
        assertThat(response.getNewTransactions().size()).isEqualTo(1);
        assertThat(response.getNewTransactions().getFirst().getComment()).isEqualTo("Transaction for testing (replaced)");
    }

    @Test
    void createImportResponseWithNewAndExistingTransactions() {
        Transaction newTransaction = newTransaction();
        Transaction existingTransaction = existingTransaction();
        when(commentRepository.findAll()).thenReturn(List.of());
        when(transactionRepository.findByDateAndNumber(newTransaction.getDate(), newTransaction().getNumber())).thenReturn(Optional.empty());
        when(transactionRepository.findByDateAndNumber(existingTransaction.getDate(), existingTransaction.getNumber())).thenReturn(Optional.of(existingTransaction));
        ImportResponseHelper importResponseHelper =
                new ImportResponseHelper(transactionRepository,
                        commentRepository,
                        List.of(newTransaction, existingTransaction));
        ImportTransactionsResponse response = importResponseHelper.createImportResponse();
        assertThat(response.getExistingTransactions().size()).isEqualTo(1);
        assertThat(response.getExistingTransactions().getFirst()).extracting(TransactionsPerCategory::getTotal)
                .isEqualTo(-10.0);
        assertThat(response.getNewTransactions().size()).isEqualTo(1);
        assertThat(response.getNewTransactions().getFirst().getOriginalComment()).isEqualTo("Transaction for testing");
        assertThat(response.getNewTransactions().getFirst().getComment()).isNull();
    }

}