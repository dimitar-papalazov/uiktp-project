package mk.ukim.finki.wp.project.epetshop.demo.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateOrder;

    @ManyToOne
    private Member member;

    private String phoneNumber;

    private String address;

    private String city;

    private String postalCode;

    private Boolean toDoor;

    private Long trackingNumber;

    @ManyToMany
    private List<Product> products;

    public Order() {
    }

    public void setTrackingNumber(Long trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Order(Member member, String phoneNumber, String address,
                 String city, String postalCode, Boolean toDoor ,
                 List<Product> products){
        this.member=member;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.city=city;
        this.postalCode=postalCode;
        this.toDoor=toDoor;
        this.trackingNumber=null;
        this.dateOrder = new Date();
        this.products = products;
    }
}
