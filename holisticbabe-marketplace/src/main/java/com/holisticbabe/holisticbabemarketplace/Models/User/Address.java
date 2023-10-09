package com.holisticbabe.holisticbabemarketplace.Models.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_address;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;
    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;
}
