package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.controller.CategoryController;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.response.CustomResponse;
import be.yorian.budgetbuddy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryControllerImpl implements CategoryController {

	
    private CategoryService categoryService;

    public CategoryControllerImpl() {}

    @Autowired
    public CategoryControllerImpl(CategoryService categoryService) { 
    	this.categoryService = categoryService;
    }

    
    @Override
    @GetMapping("/")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @Override
    @GetMapping("/{category_id}")
    public Optional<Category> getCategoryById(@PathVariable("category_id") long category_id) {
        return categoryService.getCategoryById(category_id);
    }

    @Override
    @GetMapping(produces = "application/json", path="/label")
    public ResponseEntity<CustomResponse> getCategoryBySearchterm(@RequestParam Optional<String> label,
                                                                 @RequestParam Optional<Integer> page,
                                                                 @RequestParam Optional<Integer> size) {
        Page<Category> categories = categoryService.getCategoriesByLabel(label.orElse(""),
                page.orElse(0), size.orElse(10));
        CustomResponse response = new CustomResponse();
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        Map<String, Page> dataMap = new HashMap<>();
        dataMap.put("page", categories);
        response.setData(dataMap);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<Void> saveCategory(@RequestBody Category category) {
        Category new_category = categoryService.saveCategory(category);

        return entityWithLocation(new_category.getId());
    }

    @Override
    @PutMapping("/{category_id}")
    public Category updateCategory(@PathVariable("category_id")Long categoryId, @RequestBody Category category) {
        return categoryService.updateCategory(categoryId, category);
    }
    
    @Override
    @DeleteMapping("/{category_id}")
    public void deleteCategory(@PathVariable("category_id") long category_id) {
        categoryService.deleteCategory(category_id);
    }

    private ResponseEntity<Void> entityWithLocation(Object id) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{category_id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }
 }
