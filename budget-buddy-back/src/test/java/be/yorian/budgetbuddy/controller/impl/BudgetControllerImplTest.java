package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.service.BudgetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static be.yorian.budgetbuddy.mother.BudgetOverviewMother.emptyCategoricalBudgetOverview;
import static be.yorian.budgetbuddy.mother.BudgetOverviewMother.emptyMonthlyBudgetOverview;
import static be.yorian.budgetbuddy.mother.BudgetOverviewMother.emptyYearlyBudgetOverview;
import static be.yorian.budgetbuddy.mother.CategoryMother.categoryGrocery;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetControllerImpl.class)
class BudgetControllerImplTest extends BaseControllerTest {

    @MockitoBean
    BudgetService budgetService;

    private static final String MONTHLY_OVERVIEW_URL = "/budgets/budget-by-month";
    private static final String CATEGORY_OVERVIEW_URL = "/budgets/budget-by-category/{categoryId}";
    private static final String YEARLY_OVERVIEW_URL = "/budgets/budget-by-year/{year}";


    @Test
    @DisplayName("Get budget-by-month should return a monthly overview")
    void getBudgetOverviewPerMonth_shouldReturnOverview() throws Exception {
        int month = 7;
        int year = 2025;

        when(budgetService.getBudgetOverviewPerMonth(month, year))
                .thenReturn(emptyMonthlyBudgetOverview("juli 2025"));

        mockMvc.perform(get(MONTHLY_OVERVIEW_URL)
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.month").value("juli 2025"))
                .andExpect(jsonPath("$.budgetsPerCategory").isArray())
                .andExpect(jsonPath("$.graphData").isMap())
                .andExpect(jsonPath("$.projectsData").isArray());
    }

    @Test
    @DisplayName("Get budget-by-category with a year should call the correct service method")
    void getBudgetOverviewByCategory_withYear_shouldCallCorrectService() throws Exception {
        long categoryId = 1L;
        int year = 2025;

        when(budgetService.getBudgetOverviewByCategoryAndYear(categoryId, Optional.of(year)))
                .thenReturn(emptyCategoricalBudgetOverview(categoryGrocery()));

        mockMvc.perform(get(CATEGORY_OVERVIEW_URL, categoryId)
                        .param("year", String.valueOf(year))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category.label").value("Boodschappen"))
                .andExpect(jsonPath("$.budgetsPerMonth").isArray());

        verify(budgetService, times(1)).getBudgetOverviewByCategoryAndYear(categoryId, Optional.of(year));
    }

    @Test
    @DisplayName("Get budget-by-category without a year should call the correct service method")
    void getBudgetOverviewByCategory_withoutYear_shouldCallCorrectService() throws Exception {
        long categoryId = 1L;

        when(budgetService.getBudgetOverviewByCategoryAndYear(categoryId, Optional.empty()))
                .thenReturn(emptyCategoricalBudgetOverview(categoryGrocery()));

        mockMvc.perform(get(CATEGORY_OVERVIEW_URL, categoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category.label").value("Boodschappen"))
                .andExpect(jsonPath("$.budgetsPerMonth").isArray());

        verify(budgetService, times(1)).getBudgetOverviewByCategoryAndYear(categoryId, Optional.empty());
    }

    @Test
    @DisplayName("Get budget-by-year should return a yearly overview")
    void getBudgetOverviewByYear_shouldReturnOverview() throws Exception {
        int year = 2025;

        when(budgetService.getBudgetOverviewByYear(year))
                .thenReturn(emptyYearlyBudgetOverview());

        mockMvc.perform(get(YEARLY_OVERVIEW_URL, year)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.budgetsPerMonth").isArray())
                .andExpect(jsonPath("$.graphData").exists())
                .andExpect(jsonPath("$.savingsData").exists())
                .andExpect(jsonPath("$.projectsData").isArray());
    }
}