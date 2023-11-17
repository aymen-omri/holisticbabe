package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.AddressDto;
import com.holisticbabe.holisticbabemarketplace.Models.Address;
import com.holisticbabe.holisticbabemarketplace.Models.Country;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.AddressRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.CountryRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Services.AddressService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public AddressDto findAddressById(long id) {
        return modelMapper.map(addressRepository.findById(id).get(), AddressDto.class);
    }

    @Override
    public void insertAddress(Address address, long id_user) {
        Optional<_User> user = userRepository.findById(id_user);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        List<Address> addresses = addressRepository.getAllByUserId(id_user);
        addresses.forEach(add -> {
            if (add.getAddressType().equals(address.getAddressType())) {
                throw new IllegalArgumentException(
                        "This type of address already exists for this user consider updating the existing one.");
            }
        });
        address.setUser(user.get());
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
    public void updateAddress(long countryId, long addressId , long userId, AddressDto address) {
        Address addressToUpdate = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + addressId));

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with ID: " + countryId));

        List<Address> addresses = addressRepository.getAllByUserId(userId);
        addresses.forEach(add -> {
            if (add.getAddressType().equals(address.getAddressType()) && add.getId_address() != addressToUpdate.getId_address()) {
                throw new IllegalArgumentException(
                        "This type of address already exists for this user consider updating the existing one.");
            }
        });

        addressToUpdate.setAddressLine1(address.getAddressLine1());
        addressToUpdate.setAddressLine2(address.getAddressLine2());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setPostalCode(address.getPostalCode());
        addressToUpdate.setAddressType(address.getAddressType());
        addressToUpdate.setCountry(country);
    }

}
