package com.holisticbabe.holisticbabemarketplace.Models.User;

import java.math.BigInteger;
import java.time.LocalDate;

import com.holisticbabe.holisticbabemarketplace.Models.Category;

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
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_payment_method;
    private String provider;
    private BigInteger accountNumber;
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;

    @ManyToOne
    @JoinColumn(name = "id_payment_type")
    private Category category;
}
