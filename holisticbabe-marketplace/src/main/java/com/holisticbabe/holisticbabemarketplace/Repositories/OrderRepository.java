package com.holisticbabe.holisticbabemarketplace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models._Order;

public interface OrderRepository extends JpaRepository<_Order , Long> {
    
}
