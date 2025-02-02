package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.entity.Project;

public record ProjectData(
        Project project,
        double total) { }
