package com.holisticbabe.holisticbabemarketplace.Models.User;

import java.math.BigInteger;

import com.holisticbabe.holisticbabemarketplace.Models.Shared.Image;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    private Image image;
}
