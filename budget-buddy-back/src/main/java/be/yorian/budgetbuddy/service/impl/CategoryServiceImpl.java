package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll(sortByLabel());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(long category_id) {
        return categoryRepository.findById(category_id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> getCategoriesByLabel(String label, int page, int size) {
        return categoryRepository.findByLabelContaining(label, of(page, size));
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("category_not_found"));

        category.setIcon(updatedCategory.getIcon());
        category.setLabel(updatedCategory.getLabel());
        category.setFixedcost(updatedCategory.isFixedcost());
        category.setIndetails(updatedCategory.isIndetails());
        category.setInmonitor(updatedCategory.isInmonitor());
        category.setLimitamount(updatedCategory.getLimitamount());
        category.setRevenue(updatedCategory.isRevenue());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long category_id) {
        categoryRepository.deleteById(category_id);
    }

    private Sort sortByLabel() {
        return Sort.by("label").ascending();
    }

}
