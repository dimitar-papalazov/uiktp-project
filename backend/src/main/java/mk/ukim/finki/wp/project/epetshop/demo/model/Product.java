package mk.ukim.finki.wp.project.epetshop.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductType type;

    private String imageUrl;

    private String name;

    private Double price;

    private Integer quantity;

    private Integer sale;

    private Integer sold;

    public Product() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product(ProductType type, String imageUrl, String name, Double price, Integer quantity, Integer sale, Integer sold) {
        this.type = type;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sale = sale;
        this.sold = sold;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Long getId() {
        return id;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getSale() {
        return sale;
    }

    public Integer getSold() {
        return sold;
    }
}
