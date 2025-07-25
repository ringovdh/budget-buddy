package be.yorian.budgetbuddy.repository;

import be.yorian.budgetbuddy.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryRepositoryTest extends BaseRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @Sql("/test-category.sql")
    void findCategoriesByLabelContaining() {
        Page<Category> categories = categoryRepository.findByLabelContainingIgnoreCase("Test", PageRequest.of(0, 1));
        assertEquals(1, categories.getTotalElements());
        assertThat(categories.getContent().getFirst())
                .satisfies(c ->
                        assertEquals( "Test category", c.getLabel())
                );
    }
}