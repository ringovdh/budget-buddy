package be.yorian.budgetbuddy.mother;

import be.yorian.budgetbuddy.dto.category.CategoryDTO;
import be.yorian.budgetbuddy.entity.Category;

public class CategoryMother {

    public static Category categoryGrocery() {
        return Category.builder()
                .id(1L)
                .label("Boodschappen")
                .icon("boodschappen-icon")
                .fixedcost(false)
                .revenue(false)
                .saving(false)
                .build();
    }

    public static CategoryDTO categoryDtoGrocery() {
        return new CategoryDTO(
                1L,
                "Boodschappen",
                "boodschappen-icon",
                false,
                false,
                false);
    }

    public static CategoryDTO updatedGroceryCategoryDto() {
        return new CategoryDTO(
                1L,
                "Boodschappen aangepast",
                "boodschappen-icon",
                false,
                false,
                false);
    }

    public static Category categorySaving() {
        return Category.builder()
                .id(2L)
                .label("Sparen")
                .icon("sparen-icoon")
                .fixedcost(false)
                .revenue(false)
                .saving(true)
                .build();
    }

    public static CategoryDTO categoryDtoSaving() {
        return new CategoryDTO(
                2L,
                "Sparen",
                "sparen-icoon",
                false,
                true,
                false);
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

    public static CategoryDTO newCategoryDto() {
        return new CategoryDTO(
                0L,
                "Nieuwe categorie",
                "nieuwe-categorie-icon",
                false,
                false,
                false
        );
    }

    public static Category newCategory() {
        return new Category.CategoryBuilder()
                .id(0L)
                .label("Nieuwe categorie")
                .icon("nieuwe-categorie-icon")
                .fixedcost(false)
                .saving(false)
                .revenue(false)
                .build();
    }

    public static CategoryDTO savedNewCategoryDTO() {
        return new CategoryDTO(
                5L,
                "Nieuwe categorie",
                "nieuwe-categorie-icon",
                false,
                false,
                false
        );

    }

    public static CategoryDTO savedUpdatedCategoryDTO() {
        return new CategoryDTO(
                5L,
                "Aangepaste categorie",
                "nieuwe-categorie-icon",
                false,
                false,
                false
        );
    }

}
