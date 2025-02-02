package be.yorian.budgetbuddy.repository;

import be.yorian.budgetbuddy.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    TransactionRepository repository;


    @Test
    @Sql("/test-transaction.sql")
    void findTransactionByDateAndNumberReturnsTransaction() {
        Optional<Transaction> transaction = repository.findByDateAndNumber(createDate("2024-01-09"),"001");
        assertThat(transaction)
                .isPresent()
                .isNotEmpty()
                .hasValueSatisfying(tx -> {
                    assertEquals("001", tx.getNumber());
                    assertEquals(10, tx.getAmount());
                });
    }

    @Test
    @Sql("/test-transaction.sql")
    void findTransactionsByDateWithYearAndMonth() {
        List<Transaction> transactions = repository.findByDateContainingYearAndMonth(1, 2024);
        assertEquals(1, transactions.size());
    }

    @Test
    @Sql("/test-transaction_project.sql")
    void findTransactionsByDateAndProject() {
        List<Transaction> transactions = repository.findByDateContainingYearAndMonthAndProjectNotNull(1, 2024);
        assertEquals(1, transactions.size());
    }

    @Test
    @Sql("/test-transaction.sql")
    void findTransactionsByDateAndProjectIfNoProject() {
        List<Transaction> transactions = repository.findByDateContainingYearAndMonthAndProjectNotNull(1, 2024);
        assertEquals(0, transactions.size());
    }

    @Test
    @Sql("/test-transaction.sql")
    void findTransactionsByDateWithYear() {
        List<Transaction> transactions = repository.findByDateContainingYear(2024);
        assertEquals(1, transactions.size());
    }

    @Test
    @Sql("/test-transaction_project.sql")
    void findTransactionsByProjectname() {
        List<Transaction> transactions = repository.findByProjectProjectname("Test project");
        assertEquals(1, transactions.size());
    }

    @Test
    @Sql("/test-transaction.sql")
    void findTransactionsByCategoryId() {
        List<Transaction> transactions = repository.findByCategoryId(1L);
        assertEquals(1, transactions.size());
    }

    private LocalDate createDate(String dateAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateAsString, formatter);
    }
}