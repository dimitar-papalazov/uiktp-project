package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String name;

    private Integer quantity;

    private Double price;

    @ManyToOne
    private Category category;

    public Product() {
    }

    public Product(String imageUrl, String name, Integer quantity, Category category, Double price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
    }
}
