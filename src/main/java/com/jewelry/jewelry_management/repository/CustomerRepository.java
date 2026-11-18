package com.jewelry.jewelry_management.repository;

import com.jewelry.jewelry_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}