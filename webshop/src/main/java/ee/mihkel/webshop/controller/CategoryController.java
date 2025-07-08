package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Category;
import ee.mihkel.webshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryRepository.findById(id).orElseThrow());
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
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
}
