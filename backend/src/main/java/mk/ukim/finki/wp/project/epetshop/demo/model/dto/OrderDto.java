package mk.ukim.finki.wp.project.epetshop.demo.model.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private String username;

    private String phoneNumber;

    private String address;

    private String city;

    private String postalCode;

    private Boolean toDoor;

    private List<Long> productIds;

    public OrderDto() {
    }

    public OrderDto(String username, String phoneNumber, String address,
                    String city, String postalCode, Boolean toDoor,
                    List<Long> ids) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.toDoor = toDoor;
        this.productIds= ids;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Boolean getToDoor() {
        return toDoor;
    }

}
