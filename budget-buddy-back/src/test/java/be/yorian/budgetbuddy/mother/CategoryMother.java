package be.yorian.budgetbuddy.mother;

import be.yorian.budgetbuddy.entity.Category;

public class CategoryMother {

    public static Category categoryGrocery() {
        return Category.builder()
                .id(1L)
                .label("Boodschappen")
                .fixedcost(false)
                .revenue(false)
                .saving(false)
                .build();
    }

    public static Category categorySaving() {
        return Category.builder()
                .id(2L)
                .label("Saving")
                .fixedcost(false)
                .revenue(false)
                .saving(true)
                .build();
    }

    public static Category categoryChore() {
        return Category.builder()
                .id(3L)
                .label("Klussen")
                .fixedcost(false)
                .revenue(false)
                .saving(false)
                .build();
    }

}
