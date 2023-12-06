package com.holisticbabe.holisticbabemarketplace.Models;

import java.time.LocalDate;

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
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_payment_method;
    @NonNull
    private String provider;
    @NonNull
    private String accountNumber;
    @NonNull
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;

    @ManyToOne
    @JoinColumn(name = "id_payment_type")
    private PaymentType paymentType;
}
