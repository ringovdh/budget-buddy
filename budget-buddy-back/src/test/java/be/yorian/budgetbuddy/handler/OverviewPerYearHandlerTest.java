package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Project;
import be.yorian.budgetbuddy.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static be.yorian.budgetbuddy.mother.CategoryMother.categoryChore;
import static be.yorian.budgetbuddy.mother.CategoryMother.categorySaving;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class OverviewPerYearHandlerTest extends OverviewHandlerTest {

    private Category sparen;
    private Category klussen;
    private LocalDate dateInSeptember;
    private OverviewPerYearHandler handler;


    @BeforeEach
    void setUp() {
        this.sparen = categorySaving();
        this.klussen = categoryChore();
        this.dateInSeptember = dateInJune.plusMonths(3);
        this.handler = new OverviewPerYearHandler(transactionRepository, TEST_YEAR);
    }


    @Test
    void getBudgetOverviewPerYear_withMixedTransactions_returnsCompleteOverview() {
        Project project = new Project();
        Transaction t1 = new Transaction("t1", 50.25, "-", dateInJune, "Aankoop in Carrefour", boodschappen, null);
        Transaction t2 = new Transaction("t2", 24.50, "-", dateInAugust, "Aankoop in Albert Hein", boodschappen, null);
        Transaction t3 = new Transaction("t3", 0.50, "-", dateInJune, "Automatisch sparen", sparen, null);
        Transaction t4 = new Transaction("t4", 75.25, "-",dateInSeptember,"Aankoop in Hubo", klussen, project);
        List<Transaction> transactions_1 = List.of(t1, t3);
        List<Transaction> transactions_2 = List.of(t2);
        List<Transaction> transactions_3 = List.of(t4);
        List<Transaction> transactions = List.of(t1, t2, t3, t4);

        when(transactionRepository.findByDateContainingYear(TEST_YEAR)).thenReturn(transactions);

        YearlyBudgetOverview result = handler.createYearlyBudgetOverview();

        assertThat(result.budgetsPerMonth())
                .hasSize(3)
                .anySatisfy(m -> {
                    assertThat(m.month()).isEqualTo(6);
                    assertThat(m.transactions()).containsExactlyElementsOf(transactions_1);
                    assertThat(m.totalOutgoingBudget()).isEqualTo(-50.25);
                    assertThat(m.totalSavings()).isEqualTo(-0.5);
                })
                .anySatisfy(m2 -> {
                    assertThat(m2.month()).isEqualTo(8);
                    assertThat(m2.transactions()).containsExactlyElementsOf(transactions_2);
                    assertThat(m2.totalOutgoingBudget()).isEqualTo(-24.50);
                })
                .anySatisfy(m3 -> {
                    assertThat(m3.month()).isEqualTo(9);
                    assertThat(m3.transactions()).containsExactlyElementsOf(transactions_3);
                    assertThat(m3.totalOutgoingBudget()).isEqualTo(-75.25);
                });

        assertThat(result.graphData().otherCostAmounts())
                .hasSize(12)
                .containsEntry(5, 0.0)
                .containsEntry(6, -50.25)
                .containsEntry(7, -50.25)
                .containsEntry(8, -74.75)
                .containsEntry(12, -150.00);

        assertThat(result.projectsData())
                .hasSize(1)
                .anySatisfy(p -> {
                    assertThat(p.project()).isEqualTo(project);
                    assertThat(p.total()).isEqualTo(-75.25);
                });

        int day = dateInJune.getDayOfYear();
        assertThat(result.savingsData())
                .satisfies(sd -> {
                    assertThat(sd.labels().size()).isEqualTo(365);
                    assertThat(sd.savingAmounts().size()).isEqualTo(365);
                    assertThat(sd.savingAmounts().get(day - 1)).isEqualTo(0.0);
                    assertThat(sd.savingAmounts().get(day)).isEqualTo(-0.5);
                    assertThat(sd.savingAmounts().get(day + 1)).isEqualTo(-0.5);
                });
    }

    @Test
    void getBudgetOverviewPerYear_whenNoTransactionsExist_shouldReturnEmptyOverview() {

        when(transactionRepository.findByDateContainingYear(TEST_YEAR)).thenReturn(List.of());

        YearlyBudgetOverview result = handler.createYearlyBudgetOverview();

        assertThat(result).isNotNull();
        assertThat(result.budgetsPerMonth()).isEmpty();
        assertThat(result.projectsData()).isEmpty();
        assertThat(result.savingsData().savingAmounts()).size().isEqualTo(365);
        assertThat(result.graphData().otherCostAmounts().get(12)).isEqualTo(0.0);
    }
}