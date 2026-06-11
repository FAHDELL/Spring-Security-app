package com.app.customer_front_theymeleaf_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Product {

    private String id;
    private  String name;
    private double price;
    private int quantity;
}
