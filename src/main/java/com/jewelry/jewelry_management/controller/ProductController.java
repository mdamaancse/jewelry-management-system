
package com.jewelry.jewelry_management.controller;

import com.jewelry.jewelry_management.entity.Product;
import com.jewelry.jewelry_management.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/edit/{id}")
public String editProduct(@PathVariable Long id, Model model) {
    Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    model.addAttribute("product", product);
    return "add-product";
}

@PostMapping("/update")
public String updateProduct(@ModelAttribute Product product) {
    productRepository.findById(product.getId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

    productRepository.save(product);
    return "redirect:/products";
}

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
