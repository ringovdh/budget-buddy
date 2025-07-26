package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.dto.category.BudgetPerCategory;

import java.util.List;

public record MonthlyBudgetOverview(
        String month,
        List<BudgetPerCategory> budgetsPerCategory,
        GraphData graphData,
        List<ProjectData> projectsData) {
}
