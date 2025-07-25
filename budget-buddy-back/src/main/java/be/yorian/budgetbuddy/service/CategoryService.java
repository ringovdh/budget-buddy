package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.dto.category.CategoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
	
	List<CategoryDTO> getCategories();

	CategoryDTO getCategoryById(long category_id);

	Page<CategoryDTO> getCategoriesByLabel(String label, int page, int size);

	CategoryDTO createNewCategory(CategoryDTO categoryDto);

	CategoryDTO updateCategory(Long comment_id, CategoryDTO categoryDto);

	void deleteCategory(long category_id);
	
}
