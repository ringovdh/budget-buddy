package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.dto.category.CategoryDTO;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static be.yorian.budgetbuddy.mother.CategoryMother.categoryDtoGrocery;
import static be.yorian.budgetbuddy.mother.CategoryMother.categoryGrocery;
import static be.yorian.budgetbuddy.mother.CategoryMother.categorySaving;
import static be.yorian.budgetbuddy.mother.CategoryMother.newCategory;
import static be.yorian.budgetbuddy.mother.CategoryMother.newCategoryDto;
import static be.yorian.budgetbuddy.mother.CategoryMother.updatedGroceryCategoryDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    private Category grocery;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;


    @BeforeEach
    void setUp() {
        grocery = categoryGrocery();
    }


    @Test
    @DisplayName("Get categories should return all categories")
    void getCategories_returnsAllCategories() {
        Category saving = categorySaving();
        List<Category> categories = List.of(grocery, saving);

        when(categoryRepository.findAll(any(Sort.class))).thenReturn(categories);

        List<CategoryDTO> categorieDtos = categoryService.getCategories();
        assertThat(categorieDtos)
                .hasSize(2)
                .extracting(CategoryDTO::label)
                .containsExactlyInAnyOrder(grocery.getLabel(), saving.getLabel());
        verify(categoryRepository).findAll(any(Sort.class));
    }

    @Test
    @DisplayName("Get category by id should return correct category when exists")
    void getCategoryById_whenExists_shouldReturnCategory() {
        when(categoryRepository.findById(grocery.getId())).thenReturn(Optional.of(grocery));

        CategoryDTO categoryDto = categoryService.getCategoryById(grocery.getId());

        assertCategoryDto(categoryDto, categoryDtoGrocery());
        verify(categoryRepository).findById(grocery.getId());
    }

    @Test
    @DisplayName("Get category by id should throw exception when not exists")
    void getCategoryById_whenNotExists_shouldThrowException() {
        long unknownCategoryId = 99L;

        when(categoryRepository.findById(unknownCategoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getCategoryById(unknownCategoryId));

        assertThat(exception.getMessage()).isEqualTo("category_not_found");
        verify(categoryRepository).findById(unknownCategoryId);
    }

    @Test
    @DisplayName("Get categories by label should return categories when label contains category")
    void getCategoriesByLabel_shouldReturnCategories_whenLabelContainsCategory() {
        Page<Category> page = new PageImpl<>(List.of(grocery));
        String label = "schap";

        when(categoryRepository.findByLabelContainingIgnoreCase(eq(label), any(Pageable.class))).thenReturn(page);

        Page<CategoryDTO> categories = categoryService.getCategoriesByLabel(label, 0, 10);

        assertThat(categories.getContent())
                .hasSize(1)
                .extracting(CategoryDTO::label)
                .contains(grocery.getLabel());
        verify(categoryRepository).findByLabelContainingIgnoreCase(eq(label), any(Pageable.class));
    }

    @Test
    @DisplayName("Post category should return saved category and location")
    void createNewCategory_shouldCreateCategory_andReturnCategory() {
        CategoryDTO newCategoryDto = newCategoryDto();
        Category newCategory = newCategory();

        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        CategoryDTO savedCategory = categoryService.createNewCategory(newCategoryDto);

        assertCategoryDto(savedCategory, newCategoryDto);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Put category should return updated category")
    void updateCategory_shouldReturnUpdatedCategory() {
        CategoryDTO updatedCategoryDTO = updatedGroceryCategoryDto();

        when(categoryRepository.findById(grocery.getId())).thenReturn(Optional.of(grocery));

        CategoryDTO categoryDTO = categoryService.updateCategory(grocery.getId(), updatedCategoryDTO);

        assertCategoryDto(categoryDTO, updatedCategoryDTO);
        verify(categoryRepository).findById(grocery.getId());
    }

    @Test
    @DisplayName("Put category should throw exception when not exists")
    void updateCategory_shouldThrowException_whenCategoryNotExists() {
        CategoryDTO updatedCategoryDTO = updatedGroceryCategoryDto();
        long unknownCategoryId = 99L;

        when(categoryRepository.findById(unknownCategoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.updateCategory(unknownCategoryId, updatedCategoryDTO));

        assertThat(exception.getMessage()).isEqualTo("category_not_found");
        verify(categoryRepository).findById(unknownCategoryId);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Delete category should delete category when exists")
    void deleteCategory_shouldDeleteCategory_whenExists() {
        when(categoryRepository.findById(grocery.getId())).thenReturn(Optional.of(grocery));

        categoryService.deleteCategory(grocery.getId());

        verify(categoryRepository).findById(grocery.getId());
        verify(categoryRepository, times(1)).delete(grocery);
    }

    @Test
    @DisplayName("Delete category should throw exception when not exists")
    void deleteCategory_shouldReturnExceptionWhenCategoryNotExists() {
        long unknownCategoryId = 99L;

        when(categoryRepository.findById(unknownCategoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.deleteCategory(unknownCategoryId));

        assertThat(exception.getMessage()).isEqualTo("category_not_found");
        verify(categoryRepository).findById(unknownCategoryId);
        verify(categoryRepository, never()).delete(any());
    }

    private static void assertCategoryDto(CategoryDTO actualCategory, CategoryDTO expectedCategory) {
        assertThat(actualCategory).usingRecursiveComparison().
                isEqualTo(expectedCategory);
    }

}