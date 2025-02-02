package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.response.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryController {

    List<Category> getCategories();
    Optional<Category> getCategoryById(long id);
    ResponseEntity<CustomResponse> getCategoryBySearchterm(Optional<String> label,
                                                          Optional<Integer> page,
                                                          Optional<Integer> size);
    ResponseEntity<Void> saveCategory(Category category);
    Category updateCategory(Long categoryId, Category category);
    void deleteCategory(long category_id);
        
    
}
