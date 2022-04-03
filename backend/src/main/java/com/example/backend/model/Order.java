package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Product> products;

    private String phoneNumber;

    private String address;

    private String city;

    private String postalCode;

    private Boolean deliveryToDoor;

    private Long trackingNumber;

    public Order() {
    }

    public Order(LocalDateTime orderDate, User user, List<Product> products, String phoneNumber, String address, String city, String postalCode, Boolean deliveryToDoor, Long trackingNumber) {
        this.orderDate = orderDate;
        this.user = user;
        this.products = products;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.deliveryToDoor = deliveryToDoor;
        this.trackingNumber = trackingNumber;
    }
}
