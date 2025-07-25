package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.controller.CategoryController;
import be.yorian.budgetbuddy.dto.category.CategoryDTO;
import be.yorian.budgetbuddy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;


    @Autowired
    public CategoryControllerImpl(CategoryService categoryService) { 
    	this.categoryService = categoryService;
    }

    
    @Override
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @Override
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(
            @PathVariable long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByLabel(
            @RequestParam Optional<String> label,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {
        Page<CategoryDTO> categories = categoryService.getCategoriesByLabel(
                label.orElse(""),
                page.orElse(0),
                size.orElse(10));
        return ResponseEntity.ok(categories);
    }

    @Override
    @PostMapping
    public ResponseEntity<CategoryDTO> createNewCategory(@RequestBody CategoryDTO category) {
        CategoryDTO newCategory = categoryService.createNewCategory(category);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCategory.id())
                .toUri();
        return ResponseEntity.created(location).body(newCategory);
    }

    @Override
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO category) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, category));
    }
    
    @Override
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

 }
