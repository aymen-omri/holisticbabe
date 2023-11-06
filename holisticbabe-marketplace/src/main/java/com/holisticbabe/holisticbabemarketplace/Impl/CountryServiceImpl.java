package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.CountryDto;
import com.holisticbabe.holisticbabemarketplace.Models.Country;
import com.holisticbabe.holisticbabemarketplace.Repositories.CountryRepository;
import com.holisticbabe.holisticbabemarketplace.Services.CountryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CountryDto findById(long id) {
        return modelMapper.map(countryRepository.findById(id).get() , CountryDto.class);
    }

    @Override
    public List<CountryDto> findAll() {
        return countryRepository
                .findAll()
                .stream()
                .map(country -> modelMapper.map(country, CountryDto.class))
                .sorted(Comparator.comparing(CountryDto::getCountryName))
                .toList();
    }

    @Override
    public void insertCountry(CountryDto country) {
        countryRepository.save(modelMapper.map(country, Country.class));
    }

    @Override
    public void deleteCountry(long id) {
        countryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateCountry(long id, CountryDto country) {
        Country countryToUpdate = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country doesn't exist!"));
        countryToUpdate.setCountryName(country.getCountryName());
    }

}
