package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    
    
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
    	this.categoryRepository = categoryRepository;
	}

    @Override
    public List<Category> getCategories() {
		return categoryRepository.findAll(sortByLabel());
	}
    
    @Override
	public Optional<Category> getCategoryById(long category_id) {
		return categoryRepository.findById(category_id);
	}

    @Override
    public Page<Category> getCategoriesByLabel(String label, int page, int size) {
        return categoryRepository.findByLabelContaining(label, of(page, size));
    }

    @Override
	public Category saveCategory(Category category) {
        return categoryRepository.save(category);
	}

	@Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if (optionalCategory.isEmpty()) {
            throw new EntityNotFoundException("category_not_found");
        } else {
            Category existingCategory = optionalCategory.get();
            existingCategory.setIcon(category.getIcon());
            existingCategory.setLabel(category.getLabel());
            existingCategory.setFixedcost(category.isFixedcost());
            existingCategory.setIndetails(category.isIndetails());
            existingCategory.setInmonitor(category.isInmonitor());
            existingCategory.setLimitamount(category.getLimitamount());
            existingCategory.setRevenue(category.isRevenue());
            return categoryRepository.save(existingCategory);
        }
    }

	@Override
	public void deleteCategory(long category_id) {
		categoryRepository.deleteById(category_id);
	}

	private Sort sortByLabel() {
        return Sort.by("label").ascending();
    }	
	
}
