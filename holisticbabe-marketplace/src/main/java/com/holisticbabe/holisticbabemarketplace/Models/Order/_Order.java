package com.holisticbabe.holisticbabemarketplace.Models.Order;


import java.math.BigDecimal;

import com.holisticbabe.holisticbabemarketplace.Models.User.Address;
import com.holisticbabe.holisticbabemarketplace.Models.User.PaymentMethod;

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
public class _Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_order;
    private String orderDate;
    private BigDecimal orderTotal;
    private boolean orderStatus;

    @ManyToOne
    @JoinColumn(name = "id_address")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "id_payment_method")
    private PaymentMethod paymentMethod;
}
