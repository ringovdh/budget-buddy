package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.dto.category.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryController {

    ResponseEntity<List<CategoryDTO>> getCategories();

    ResponseEntity<CategoryDTO> getCategoryById(long id);

    ResponseEntity<Page<CategoryDTO>> getCategoriesByLabel(Optional<String> label,
                                                           Optional<Integer> page,
                                                           Optional<Integer> size);

    ResponseEntity<CategoryDTO> createNewCategory(CategoryDTO category);

    ResponseEntity<CategoryDTO> updateCategory(Long categoryId, CategoryDTO category);

    ResponseEntity<Void> deleteCategory(long category_id);
}
