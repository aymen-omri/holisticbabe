package com.holisticbabe.holisticbabemarketplace.Models;


import java.math.BigDecimal;

import com.holisticbabe.holisticbabemarketplace.Models.Bootcamp;
import com.holisticbabe.holisticbabemarketplace.Models.Course;
import com.holisticbabe.holisticbabemarketplace.Models.Order;
import com.holisticbabe.holisticbabemarketplace.Models.Product;

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

public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_order_line;
    private int quantity;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name ="id_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "bootcamp_id")
    private Bootcamp bootcamp;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;
}
