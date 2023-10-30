package com.holisticbabe.holisticbabemarketplace.Dtos;


import com.holisticbabe.holisticbabemarketplace.Models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
