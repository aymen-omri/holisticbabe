package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.CountryDto;

public interface CountryService {

    CountryDto findById(long id);

    List<CountryDto> findAll();

    void insertCountry(CountryDto country);

    void deleteCountry(long id);

    void updateCountry(long id, CountryDto country);

}
