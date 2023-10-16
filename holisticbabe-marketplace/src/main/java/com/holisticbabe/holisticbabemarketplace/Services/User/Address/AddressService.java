package com.holisticbabe.holisticbabemarketplace.Services.User.Address;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.User.AddressDto;
import com.holisticbabe.holisticbabemarketplace.Models.User.Address;

public interface AddressService {
    AddressDto findAddressById(long id);

    void insertAddress(Address address);

    List<AddressDto> getAll();

    List<AddressDto> getAllByUserId(long id);

    void deleteAddress(long id);

    void updateAddress(long countryId, long addressId, AddressDto address);
}
