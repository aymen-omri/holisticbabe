package com.holisticbabe.holisticbabemarketplace.Models;

import java.math.BigInteger;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class _User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_user;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private BigInteger phoneNumber;
    private String password;
    @OneToOne
    @JoinColumn(name = "id_token")
    private Token token;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private _Role role;
    @OneToOne
    @JoinColumn(name = "id_image")
    private Multimedia image ;

    @OneToOne(cascade = CascadeType.ALL)
    private Wishlist wishlist;

}
