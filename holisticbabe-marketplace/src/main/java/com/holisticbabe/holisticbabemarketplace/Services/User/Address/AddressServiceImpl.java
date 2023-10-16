package com.holisticbabe.holisticbabemarketplace.Services.User.Address;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.User.AddressDto;
import com.holisticbabe.holisticbabemarketplace.Models.User.Address;
import com.holisticbabe.holisticbabemarketplace.Models.User.Country;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.AddressRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.CountryRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    @Override
    public AddressDto findAddressById(long id) {
        return modelMapper.map(addressRepository.findById(id).get(), AddressDto.class);
    }

    @Override
    public void insertAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public List<AddressDto> getAll() {
        return addressRepository.findAll().stream().map(a -> modelMapper.map(a, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> getAllByUserId(long id) {
        return addressRepository.getAllByUserId(id).stream().map(a -> modelMapper.map(a, AddressDto.class)).toList();
    }

    @Override
    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateAddress(long countryId, long addressId, AddressDto address) {
        Address addressToUpdate = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + addressId));

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with ID: " + countryId));

        addressToUpdate.setAddressLine1(address.getAddressLine1());
        addressToUpdate.setAddressLine2(address.getAddressLine2());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setPostalCode(address.getPostalCode());
        addressToUpdate.setCountry(country);
    }

}
