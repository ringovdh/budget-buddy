package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
	
	List<Category> getCategories();
	Optional<Category> getCategoryById(long category_id);

	Page<Category> getCategoriesByLabel(String label, int page, int size);
	Category saveCategory(Category category);
	Category updateCategory(Long comment_id, Category category);
	void deleteCategory(long category_id);
	
}
