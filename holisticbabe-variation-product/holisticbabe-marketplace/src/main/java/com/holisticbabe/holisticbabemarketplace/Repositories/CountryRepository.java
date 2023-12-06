package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCountryName(String countryName);

}
