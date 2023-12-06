package com.holisticbabe.holisticbabemarketplace.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_vendor;

    private String companyName;
    private String societyIdentificationNumber;
    private String description;
    private String shopLink;
    private LocalDate date;
    private int approved;

    @ManyToMany
    List<Country> countries = new ArrayList<>();

    @OneToOne
    private Address companyAddress;

    @OneToOne
    @JoinColumn(name = "id_user")
    private _User user;

}
