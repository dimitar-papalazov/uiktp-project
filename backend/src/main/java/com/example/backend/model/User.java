package com.example.backend.model;

import com.example.backend.model.enums.Role;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String firstName;

    private String lastName;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String username, String firstName, String lastName, String password, Role role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public User() {
    }
}
