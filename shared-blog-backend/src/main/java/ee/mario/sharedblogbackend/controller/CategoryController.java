package ee.mario.sharedblogbackend.controller;

import ee.mario.sharedblogbackend.entity.Category;
import ee.mario.sharedblogbackend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategory() {
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @PostMapping("categories")
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category) {
        if (category.getId() != null) {
            throw new RuntimeException("Cannot add with ID!");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @PutMapping("categories")
    public ResponseEntity<List<Category>> editCategory(@RequestBody Category category) {
        if (category.getId() == null) {
            throw new RuntimeException("Cannot add without ID!");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

}
