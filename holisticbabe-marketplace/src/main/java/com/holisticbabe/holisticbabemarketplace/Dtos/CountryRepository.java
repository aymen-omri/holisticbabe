package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
