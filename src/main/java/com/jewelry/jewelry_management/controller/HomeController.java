package com.jewelry.jewelry_management.controller;

import com.jewelry.jewelry_management.repository.ProductRepository;
import com.jewelry.jewelry_management.repository.CustomerRepository;
import com.jewelry.jewelry_management.repository.SaleRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import java.util.Comparator;

@Controller
public class HomeController {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public HomeController(ProductRepository productRepository,
                          CustomerRepository customerRepository,
                          SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {

        var products = productRepository.findAll();
        var customers = customerRepository.findAll();
        var sales = saleRepository.findAll();

        double totalRevenue = sales.stream()
                .mapToDouble(sale -> sale.getTotalAmount())
                .sum();

        long lowStockCount = products.stream()
                .filter(product -> product.getStock() <= 5)
                .count();

        var recentSales = sales.stream()
                .sorted(Comparator.comparing(
                        sale -> sale.getSaleDate(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .limit(5)
                .toList();

        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalCustomers", customers.size());
        model.addAttribute("totalSales", sales.size());
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("recentSales", recentSales);

        if (authentication != null) {
            model.addAttribute("adminUsername", authentication.getName());
        }

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
