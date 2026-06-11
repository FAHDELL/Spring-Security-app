package com.app.customer_front_theymeleaf_app.repository;

import com.app.customer_front_theymeleaf_app.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
