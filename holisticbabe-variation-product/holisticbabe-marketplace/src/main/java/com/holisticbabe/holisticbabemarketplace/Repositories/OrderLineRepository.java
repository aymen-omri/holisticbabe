package com.holisticbabe.holisticbabemarketplace.Repositories;


import com.holisticbabe.holisticbabemarketplace.Models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
