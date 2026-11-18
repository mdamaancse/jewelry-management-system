package com.jewelry.jewelry_management.controller;

import com.jewelry.jewelry_management.entity.Sale;
import com.jewelry.jewelry_management.repository.SaleRepository;
import com.jewelry.jewelry_management.repository.CustomerRepository;
import com.jewelry.jewelry_management.repository.ProductRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sales")
public class SaleController {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public SaleController(SaleRepository saleRepository,
                          CustomerRepository customerRepository,
                          ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listSales(Model model) {
        model.addAttribute("sales", saleRepository.findAll());
        return "sales";
    }

    @GetMapping("/add")
    public String addSale(Model model) {
        model.addAttribute("sale", new Sale());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "add-sale";
    }

    @PostMapping("/save")
    @Transactional
    public String saveSale(@RequestParam Long customerId,
                           @RequestParam Long productId,
                           @RequestParam int quantity,
                           RedirectAttributes redirectAttributes) {

        if (quantity <= 0) {
            redirectAttributes.addFlashAttribute("error", "Quantity must be greater than zero.");
            return "redirect:/sales/add";
        }

        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            redirectAttributes.addFlashAttribute("error", "Insufficient stock available.");
            return "redirect:/sales/add";
        }

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setProduct(product);
        sale.setQuantity(quantity);
        sale.setTotalAmount(product.getPrice() * quantity);

        product.setStock(product.getStock() - quantity);

        productRepository.save(product);
        saleRepository.save(sale);

        return "redirect:/sales";
    }

    @PostMapping("/delete/{id}")
    @Transactional
    public String deleteSale(@PathVariable Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        var product = sale.getProduct();
        product.setStock(product.getStock() + sale.getQuantity());

        productRepository.save(product);
        saleRepository.delete(sale);

        return "redirect:/sales";
    }
}
