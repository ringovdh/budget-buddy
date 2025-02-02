package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.entity.Project;
import be.yorian.budgetbuddy.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record ProjectOverview(
        Project project,
        List<Transaction> transactions,
        BigDecimal projectTotal) { }
