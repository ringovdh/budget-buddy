package be.yorian.budgetbuddy.mother;

import be.yorian.budgetbuddy.entity.Category;

public class CategoryMother {

    public static Category category() {
        Category category = new Category();
        category.setId(1);
        category.setLabel("Test category");
        return category;
    }
}
