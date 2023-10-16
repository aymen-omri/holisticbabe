package com.holisticbabe.holisticbabemarketplace.Repositories.User;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models.User.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
