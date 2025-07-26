package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.dto.category.CategoryDTO;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.mapper.CategoryMapper;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static be.yorian.budgetbuddy.mapper.CategoryMapper.mapToCategory;
import static be.yorian.budgetbuddy.mapper.CategoryMapper.mapCategoryToDTO;
import static be.yorian.budgetbuddy.mapper.CategoryMapper.updateCategoryFromDto;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final String CATEGORY_NOT_FOUND = "category_not_found";
    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll(sortByLabel())
                .stream().map(CategoryMapper::mapCategoryToDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(long categoryId) {
        Category category = findCategoryById(categoryId);
        return mapCategoryToDTO(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesByLabel(String label, int page, int size) {
        return categoryRepository.findByLabelContainingIgnoreCase(label, of(page, size, Sort.by("label")))
                .map(CategoryMapper::mapCategoryToDTO);
    }

    @Override
    public CategoryDTO createNewCategory(CategoryDTO categoryDto) {
        Category category = mapToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return mapCategoryToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO updatedCategory) {
        Category category = findCategoryById(categoryId);
        updateCategoryFromDto(category, updatedCategory);
        return mapCategoryToDTO(category);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category categoryToDelete = findCategoryById(categoryId);
        categoryRepository.delete(categoryToDelete);
    }

    private Category findCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(()-> new EntityNotFoundException(CATEGORY_NOT_FOUND));
    }

    private Sort sortByLabel() {
        return Sort.by("label").ascending();
    }

}
