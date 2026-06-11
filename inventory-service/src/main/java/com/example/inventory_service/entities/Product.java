package com.example.inventory_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Product {

    @Id
    private String id;
    private  String name;
    private double price;
    private int quantity;
}
