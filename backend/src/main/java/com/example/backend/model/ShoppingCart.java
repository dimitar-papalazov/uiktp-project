package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    User user;

//    @ManyToMany
//    List<Product> productList;


    public ShoppingCart(User user) {
        this.user = user;
        //productList = new ArrayList<>();
    }

    public ShoppingCart() {
    }
}
