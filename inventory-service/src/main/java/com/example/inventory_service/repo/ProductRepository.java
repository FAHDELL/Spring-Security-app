package com.example.inventory_service.repo;

import com.example.inventory_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,String> {
}
