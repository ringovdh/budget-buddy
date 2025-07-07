package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.repository.TransactionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static be.yorian.budgetbuddy.mother.CategoryMother.categoryGrocery;

@ExtendWith(MockitoExtension.class)
public class OverviewHandlerTest {

    protected final int TEST_YEAR = 2025;
    protected Category boodschappen = categoryGrocery();
    protected LocalDate dateInJune = LocalDate.of(TEST_YEAR, 6, 11);
    protected LocalDate dateInAugust = dateInJune.plusMonths(2);;
    @Mock
    protected TransactionRepository transactionRepository;
}
