package com.holisticbabe.holisticbabemarketplace.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_address;
    @NonNull
    private String addressLine1;
    private String addressLine2;
    @NonNull
    private String city;
    @NonNull
    private String postalCode;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;
}
