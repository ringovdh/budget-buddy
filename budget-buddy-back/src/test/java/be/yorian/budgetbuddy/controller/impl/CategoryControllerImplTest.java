package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.dto.category.CategoryDTO;
import be.yorian.budgetbuddy.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static be.yorian.budgetbuddy.mother.CategoryMother.categoryDtoGrocery;
import static be.yorian.budgetbuddy.mother.CategoryMother.categoryDtoSaving;
import static be.yorian.budgetbuddy.mother.CategoryMother.newCategoryDTO;
import static be.yorian.budgetbuddy.mother.CategoryMother.savedNewCategoryDTO;
import static be.yorian.budgetbuddy.mother.CategoryMother.savedUpdatedCategoryDTO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryControllerImpl.class)
class CategoryControllerImplTest extends BaseControllerTest {

    private static final String CATEGORIES_URL = "/categories/";
    private static final String GET_CATEGORIES_ALL_URL = CATEGORIES_URL + "all";
    private static final String GET_CATEGORIES_LABEL_URL = CATEGORIES_URL + "label";
    private static final String GET_CATEGORY_URL = CATEGORIES_URL + "{categoryId}";
    private static final String CATEGORY_NOT_FOUND = "Category not found";


    @MockitoBean
    private CategoryService categoryService;


    @Test
    @DisplayName("Get categories should return all categories")
    void getCategories_returnsAllCategories() throws Exception {
        List<CategoryDTO> categories = List.of(categoryDtoGrocery(), categoryDtoSaving());
        when(categoryService.getCategories()).thenReturn(categories);

        mockMvc.perform(get(GET_CATEGORIES_ALL_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(categories.get(0).id().intValue())))
                .andExpect(jsonPath("$[0].label", is(categories.get(0).label())))
                .andExpect(jsonPath("$[1].id", is(categories.get(1).id().intValue())))
                .andExpect(jsonPath("$[1].label", is(categories.get(1).label())));

        verify(categoryService, times(1)).getCategories();
    }

    @Test
    @DisplayName("Get category by id should return correct category when exists")
    void getCategoryById_whenExists_shouldReturnCategory() throws Exception {
        CategoryDTO category = categoryDtoGrocery();
        when(categoryService.getCategoryById(category.id())).thenReturn(category);

        mockMvc.perform(get(GET_CATEGORY_URL, category.id())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(category.id().intValue())))
                .andExpect(jsonPath("$.label", is(category.label())));

        verify(categoryService, times(1)).getCategoryById(category.id());
    }

    @Test
    @DisplayName("Get category by id should return exception when not exists")
    void getCategoryById_whenNotExists_shouldReturnException() throws Exception {
        long unknownCategoryId = 99L;
        when(categoryService.getCategoryById(unknownCategoryId))
                .thenThrow(new EntityNotFoundException(CATEGORY_NOT_FOUND));

        ResultActions resultActions = mockMvc.perform(get(GET_CATEGORY_URL, unknownCategoryId)
                        .accept(MediaType.APPLICATION_JSON));

        expectNotFound(resultActions, CATEGORY_NOT_FOUND);
        verify(categoryService, times(1)).getCategoryById(unknownCategoryId);
    }

    @Test
    @DisplayName("Get category by label should return categories when label contains category")
    void getCategoryByLabel_shouldReturnCategories_whenLabelContainsCategory() throws Exception {
        String label = "schap";
        CategoryDTO category = categoryDtoGrocery();
        Page<CategoryDTO> page = new PageImpl<>(List.of(category));
        when(categoryService.getCategoriesByLabel(label, 0, 10))
                .thenReturn(page);

        mockMvc.perform(get(GET_CATEGORIES_LABEL_URL)
                        .param("label", label)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].label", is(category.label())));

        verify(categoryService, times(1)).getCategoriesByLabel(label, 0, 10);
    }

    @Test
    @DisplayName("Create category should return saved category and location")
    void createNewCategory_shouldCreateCategory_andReturnCategory() throws Exception {
        CategoryDTO newCategory = newCategoryDTO();
        CategoryDTO savedCategory = savedNewCategoryDTO();
        when(categoryService.createNewCategory(any(CategoryDTO.class))).thenReturn(savedCategory);

        mockMvc.perform(post(CATEGORIES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith(CATEGORIES_URL + savedCategory.id())))
                .andExpect(jsonPath("$.id", is(savedCategory.id().intValue())))
                .andExpect(jsonPath("$.label", is(savedCategory.label())));

        verify(categoryService, times(1)).createNewCategory(any(CategoryDTO.class));
    }

    @Test
    @DisplayName("Update category should return updated category")
    void updateCategory_shouldReturnUpdatedCategory() throws Exception {
        CategoryDTO updatedCategory = savedUpdatedCategoryDTO();

        when(categoryService.updateCategory(eq(updatedCategory.id()), any(CategoryDTO.class)))
                .thenReturn(updatedCategory);

        mockMvc.perform(put(GET_CATEGORY_URL, updatedCategory.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedCategory.id().intValue())))
                .andExpect(jsonPath("$.label", is(updatedCategory.label())));

        verify(categoryService, times(1))
                .updateCategory(eq(updatedCategory.id()), any(CategoryDTO.class));
    }

    @Test
    @DisplayName("Update category should return exception when not exists")
    void updateCategory_shouldReturnException_whenCategoryNotExists() throws Exception {
        CategoryDTO updatedCategory = savedUpdatedCategoryDTO();
        long unknownCategoryId = 99L;

        when(categoryService.updateCategory(eq(unknownCategoryId), any(CategoryDTO.class)))
                .thenThrow(new EntityNotFoundException(CATEGORY_NOT_FOUND));

        ResultActions resultActions = mockMvc.perform(put(GET_CATEGORY_URL, unknownCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)));

        expectNotFound(resultActions, CATEGORY_NOT_FOUND);
        verify(categoryService, times(1))
                .updateCategory(eq(unknownCategoryId), any(CategoryDTO.class));
    }

    @Test
    @DisplayName("Delete category should return no content when category is deleted")
    void deleteCategory_shouldReturnNoContent_whenCategoryDeleted() throws Exception {
        long categoryId = 1L;

        doNothing().when(categoryService).deleteCategory(categoryId);

        mockMvc.perform(delete(GET_CATEGORY_URL, categoryId))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    @DisplayName("Delete category should return exception when not exists")
    void deleteCategory_shouldReturnException_whenCategoryNotExists() throws Exception {
        long unknownCategoryId = 99L;

        doThrow(new EntityNotFoundException("Category not found"))
                .when(categoryService).deleteCategory(unknownCategoryId);

        ResultActions resultActions = mockMvc.perform(delete(GET_CATEGORY_URL, unknownCategoryId));

        expectNotFound(resultActions, CATEGORY_NOT_FOUND);
        verify(categoryService, times(1)).deleteCategory(unknownCategoryId);
    }

}
