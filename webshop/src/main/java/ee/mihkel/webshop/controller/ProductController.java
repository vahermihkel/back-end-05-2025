package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.ProductDTO;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import ee.mihkel.webshop.service.CacheService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
//    List<Integer> integers = new ArrayList<>(Arrays.asList(31, 52, 24));

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CacheService cacheService;

    // ProductRepository productRepository = new ProductRepository();

    // localhost:8080/products?size=2&page=0&sort=price,asc
    @GetMapping("products")
    public ResponseEntity<Page<Product>> getProducts(Pageable pageable) { // @RequestParam int size, int page, string[] sort
        return ResponseEntity.ok().body(productRepository.findAll(pageable));
    }

//    @GetMapping("public-products")
//    public ResponseEntity<List<ProductDTO>> getPublicProducts() {
//        List<Product> products = productRepository.findAll();
//        List<ProductDTO> productDTOs = new ArrayList<>();
//        for (Product p: products) {
//            ProductDTO productDTO = new ProductDTO();
//            productDTO.setId(p.getId());
//            productDTO.setName(p.getName());
//            productDTO.setPrice(p.getPrice());
//            productDTOs.add(productDTO);
//        }
//        return ResponseEntity.ok().body(productDTOs);
//    }

    @GetMapping("public-products")
    public ResponseEntity<Page<ProductDTO>> getPublicProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductDTO> productDTOs = products.map(product -> modelMapper.map(product, ProductDTO.class));
//        List<ProductDTO> productDTOs = List.of(modelMapper.map(products, ProductDTO[].class));
        return ResponseEntity.ok().body(productDTOs);
    }

    // localhost:8080/add-product
    // Variant1 - miinus: ei saa aru, mis väärtust kaasa saadan. pluss - lühike:
    //localhost:8080/add-product/coca/2/3/5
    // Variant2
    //localhost:8080/add-product?name=Coca&price=2&quantity=3&discount=5
    // Variant3 - miinus: GET ei saa. pluss: kui läheb väga pikaks, siis selgelt kompaktsem.
    //localhost:8080/add-product --> BODY kaudu lisame: {name: "Coca", price: 2}

//    @GetMapping("add-product")
//    public List<Product> addProduct(@RequestParam String name, double price) {
//        Product product = new Product();
//        product.setName(name);
//        product.setPrice(price);
//        productRepository.save(product);
//        return productRepository.findAll();
//    }

    @PostMapping("products")
    public ResponseEntity<List<Product>> addProduct(@RequestBody Product product) {
        // TODO: Kui on ID olemas, siis viskab errori
        if (product.getId() != null) {
            throw new RuntimeException("Cannot add with ID!");
        }
        productRepository.save(product);
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ExecutionException {
        return ResponseEntity.ok().body(cacheService.getProduct(id));
    }

    @Cacheable(value = "productCache", key = "#id")
    @GetMapping("redis-products/{id}")
    public ResponseEntity<Product> getRedisProduct(@PathVariable Long id)  {
        return ResponseEntity.ok().body(productRepository.findById(id).orElseThrow());
    }

    @CacheEvict(value = "productCache", key = "#id")
    @DeleteMapping("products/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        cacheService.deleteFromCache(id); // GUAVA
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @CachePut(value = "productCache", key = "#product.id")
    @PutMapping("products")
    public ResponseEntity<List<Product>> editProduct(@RequestBody Product product) {
        // TODO: Kui on puudu ID, siis viskab errori
        if (product.getId() == null) {
            throw new RuntimeException("Cannot add without ID!");
        }
        productRepository.save(product);
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @GetMapping("search-from-products/{searchTerm}")
    public ResponseEntity<List<Product>> searchFromProducts(@PathVariable String searchTerm) {
        return ResponseEntity.ok(productRepository.findByNameContainsIgnoreCase(searchTerm));
    }
}
