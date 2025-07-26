package be.yorian.budgetbuddy.repository;

import be.yorian.budgetbuddy.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/test-category.sql")
class CategoryRepositoryTest extends BaseRepositoryTest {

    private PageRequest pageRequest;
    @Autowired
    private CategoryRepository categoryRepository;


    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 5);
    }


    @Test
    @DisplayName("findByLabelContaining should return matching categories when a match exists")
    void findByLabelContaining_whenMatchExists_shouldReturnPagedCategories() {
        Page<Category> categoriesPage = categoryRepository.findByLabelContainingIgnoreCase(
                "Test", pageRequest);

        assertThat(categoriesPage).isNotNull();
        assertThat(categoriesPage.getTotalElements()).isEqualTo(1);
        assertThat(categoriesPage.getContent()).hasSize(1);
        assertThat(categoriesPage.getContent().getFirst())
                .extracting(Category::getLabel)
                .isEqualTo("Test category");
    }

    @Test
    @DisplayName("findByLabelContaining should be case-insensitive")
    void findByLabelContaining_whenCaseIsDifferent_shouldReturnPagedCategories() {
        Page<Category> categoriesPage = categoryRepository.findByLabelContainingIgnoreCase(
                "test", pageRequest);

        assertThat(categoriesPage.getTotalElements()).isEqualTo(1);
        assertThat(categoriesPage.getContent().getFirst()
                .getLabel()).isEqualTo("Test category");
    }

    @Test
    @DisplayName("findByLabelContaining should return an empty page when no match exists")
    void findByLabelContaining_whenNoMatch_shouldReturnEmptyPage() {
        Page<Category> categoriesPage = categoryRepository.findByLabelContainingIgnoreCase(
                "nonexistent", pageRequest);

        assertThat(categoriesPage).isNotNull();
        assertThat(categoriesPage.getTotalElements()).isZero();
        assertThat(categoriesPage.getContent()).isEmpty();
    }

}