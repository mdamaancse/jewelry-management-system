package com.jewelry.jewelry_management.controller;

import com.jewelry.jewelry_management.entity.Customer;
import com.jewelry.jewelry_management.repository.CustomerRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customers";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        model.addAttribute("customer", customer);
        return "add-customer";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }
}