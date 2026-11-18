package com.jewelry.jewelry_management.controller;

import com.jewelry.jewelry_management.entity.Product;
import com.jewelry.jewelry_management.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductRepository productRepository;

    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Product not found")));
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productRepository.findAll().stream()
                .filter(p -> p.getCategory() != null &&
                        p.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        String search = keyword.toLowerCase();

        return productRepository.findAll().stream()
                .filter(p ->
                        (p.getName() != null &&
                                p.getName().toLowerCase().contains(search)) ||
                        (p.getDescription() != null &&
                                p.getDescription().toLowerCase().contains(search))
                )
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        if (product.getName() == null || product.getName().isBlank()
                || product.getPrice() < 0
                || product.getWeight() < 0
                || product.getStock() < 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid product details"));
        }

        product.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {

        return productRepository.findById(id)
                .<ResponseEntity<?>>map(product -> {
                    if (updatedProduct.getName() == null ||
                            updatedProduct.getName().isBlank() ||
                            updatedProduct.getPrice() < 0 ||
                            updatedProduct.getWeight() < 0 ||
                            updatedProduct.getStock() < 0) {
                        return ResponseEntity.badRequest()
                                .body(Map.of("message", "Invalid product details"));
                    }

                    product.setName(updatedProduct.getName());
                    product.setCategory(updatedProduct.getCategory());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setWeight(updatedProduct.getWeight());
                    product.setStock(updatedProduct.getStock());

                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Product not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Product not found"));
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
    }
}
