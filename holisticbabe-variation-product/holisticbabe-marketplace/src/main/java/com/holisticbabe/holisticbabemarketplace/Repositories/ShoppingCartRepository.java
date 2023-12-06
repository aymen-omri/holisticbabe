package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}