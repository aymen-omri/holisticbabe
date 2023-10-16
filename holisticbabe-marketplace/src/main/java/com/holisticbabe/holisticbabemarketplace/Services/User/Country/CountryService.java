package com.holisticbabe.holisticbabemarketplace.Services.User.Country;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.User.CountryDto;

public interface CountryService {

    CountryDto findById(long id);

    List<CountryDto> findAll();

    void insertCountry(CountryDto country);

    void deleteCountry(long id);

    void updateCountry(long id, CountryDto country);

}
