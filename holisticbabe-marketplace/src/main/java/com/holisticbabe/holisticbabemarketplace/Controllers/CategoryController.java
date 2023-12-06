package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Requests.CategoryRequest;
import com.holisticbabe.holisticbabemarketplace.Services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestPart("image") MultipartFile image) {
        try {
            CategoryRequest category1 = new CategoryRequest();
            category1.setName(name);
            category1.setDescription(description);
            category1.setDateCreated(LocalDateTime.now());
            Category createdCategory = categoryService.save(category1, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long categoryId,
            @RequestParam(name="name",required = false) String name,
            @RequestParam(name="description",required = false) String description,
            @RequestParam(value = "newImage", required = false) MultipartFile newImage
    ) {
        try {
            CategoryRequest category1 = new CategoryRequest();
            category1.setName(name);
            category1.setDescription(description);
            category1.setDateCreated(LocalDateTime.now());
            Category updatedCategory = categoryService.updateCategory(categoryId, category1, newImage);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam("name") String searchQuery) {
        List<Category> categories = categoryService.searchCategoriesByName(searchQuery);
        if (!categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{categoryId}/product-count")
    public ResponseEntity<Integer> getProductCountInCategory(@PathVariable Long categoryId) {
        int productCount = categoryService.getProductCountInCategory(categoryId);
        return ResponseEntity.ok(productCount);
    }


}
