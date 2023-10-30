package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models.Bootcamp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {
}
