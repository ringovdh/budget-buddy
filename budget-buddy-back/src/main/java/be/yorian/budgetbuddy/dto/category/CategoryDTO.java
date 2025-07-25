package be.yorian.budgetbuddy.dto.category;

public record CategoryDTO(
        long id,
        String label,
        String icon,
        boolean fixedcost,
        boolean saving,
        boolean revenue) { }
