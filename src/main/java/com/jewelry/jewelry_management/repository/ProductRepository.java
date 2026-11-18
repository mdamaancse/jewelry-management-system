
package com.jewelry.jewelry_management.repository;

import com.jewelry.jewelry_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
