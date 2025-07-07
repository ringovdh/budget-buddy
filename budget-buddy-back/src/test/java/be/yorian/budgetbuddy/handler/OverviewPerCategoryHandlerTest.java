package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.dto.CategoricalBudgetOverview;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class OverviewPerCategoryHandlerTest extends OverviewHandlerTest {

    private OverviewPerCategoryHandler handler;
    @Mock
    CategoryRepository categoryRepository;


    @BeforeEach
    void setUp() {
        this.handler = new OverviewPerCategoryHandler(transactionRepository,
                categoryRepository, 1L, TEST_YEAR);
    }

    @Test
    void createBudgetOverviewPerCategory_ReturnsListWithDataFor1Month() {
        // GIVEN
        Transaction t1 = new Transaction("t1", 50.25, "-", dateInJune, "Aankoop in Carrefour", boodschappen, null);
        Transaction t2 = new Transaction("t2", 24.50, "-", dateInJune.plusDays(2), "Aankoop in Albert Hein", boodschappen, null);
        List<Transaction> transactions = List.of( t1, t2);

        // WHEN
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(boodschappen));
        when(transactionRepository.findByCategoryIdAndDateContainingYear(1L, TEST_YEAR))
                .thenReturn(transactions);

        CategoricalBudgetOverview result = handler.createBudgetOverviewPerCategory();

        // THEN
        assertThat(result.category()).isEqualTo(boodschappen);
        assertThat(result.budgetsPerMonth())
                .hasSize(1)
                .singleElement()
                .satisfies(m -> {
                    assertThat(m.month()).isEqualTo("juni 2025");
                    assertThat(m.transactions()).containsExactlyInAnyOrderElementsOf(transactions);
                    assertThat(m.total()).isEqualTo(-74.75);
                });
    }

    @Test
    void createBudgetOverviewPerCategory_ReturnsListWithDataFor2Months() {
        // GIVEN
        Transaction t1 = new Transaction("t1", 50.25, "-", dateInAugust, "Aankoop in Carrefour", boodschappen, null);
        Transaction t2 = new Transaction("t2", 24.50, "-", dateInJune, "Aankoop in Albert Hein", boodschappen, null);
        List<Transaction> transactions_1 = List.of( t1);
        List<Transaction> transactions_2 = List.of( t2);
        List<Transaction> transactions = List.of(t1, t2);

        // WHEN
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(boodschappen));
        when(transactionRepository.findByCategoryIdAndDateContainingYear(1L, TEST_YEAR))
                .thenReturn(transactions);

        CategoricalBudgetOverview result = handler.createBudgetOverviewPerCategory();

        // THEN
        assertThat(result.category()).isEqualTo(boodschappen);
        assertThat(result.budgetsPerMonth())
                .hasSize(2)
                .anySatisfy(augustus -> {
                    assertThat(augustus.month()).isEqualTo("augustus " + TEST_YEAR);
                    assertThat(augustus.transactions()).containsExactlyElementsOf(transactions_1);
                    assertThat(augustus.total()).isEqualTo(-50.25);
                })
                .anySatisfy(juni -> {
                    assertThat(juni.month()).isEqualTo("juni " + TEST_YEAR);
                    assertThat(juni.transactions()).containsExactlyElementsOf(transactions_2);
                    assertThat(juni.total()).isEqualTo(-24.5);
                });
    }

    @Test
    void createBudgetOverviewPerCategory_ReturnsEmptyMonthList_whenNoTransactions() {
        // GIVEN
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(boodschappen));
        when(transactionRepository.findByCategoryIdAndDateContainingYear(1L, TEST_YEAR))
                .thenReturn(List.of());

        // WHEN
        CategoricalBudgetOverview result = handler.createBudgetOverviewPerCategory();

        // THEN
        assertThat(result.category()).isEqualTo(boodschappen);
        assertThat(result.budgetsPerMonth()).isEmpty();
    }

    @Test
    void createBudgetOverviewPerCategory_ThrowsException_whenNoCategory() {
        // GIVEN
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // THEN
        assertThatThrownBy(() -> handler.createBudgetOverviewPerCategory())
                .isInstanceOf(EntityNotFoundException.class);
    }
}