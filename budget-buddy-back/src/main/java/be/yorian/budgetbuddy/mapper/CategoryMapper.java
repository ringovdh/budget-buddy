package be.yorian.budgetbuddy.mapper;

import be.yorian.budgetbuddy.dto.category.CategoryDTO;
import be.yorian.budgetbuddy.entity.Category;

public class CategoryMapper {

    public static Category mapToCategory(CategoryDTO categoryDto) {
        if (categoryDto != null) {
            return new Category.CategoryBuilder()
                    .label(categoryDto.label())
                    .icon(categoryDto.icon())
                    .fixedcost(categoryDto.fixedcost())
                    .saving(categoryDto.saving())
                    .revenue(categoryDto.revenue())
                    .build();
        }
        return null;
    }

    public static CategoryDTO mapCategoryToDTO(Category category) {
        if (category != null) {
            return new CategoryDTO(
                    category.getId(),
                    category.getLabel(),
                    category.getIcon(),
                    category.isFixedcost(),
                    category.isSaving(),
                    category.isRevenue()
            );
        }
        return null;
    }

    public static void updateCategoryFromDto(Category category, CategoryDTO updatedCategory) {
        if (category == null || updatedCategory == null) {
            return;
        }
        category.setLabel(updatedCategory.label());
        category.setIcon(updatedCategory.icon());
        category.setFixedcost(updatedCategory.fixedcost());
        category.setSaving(updatedCategory.saving());
        category.setRevenue(updatedCategory.revenue());
    }

}
