package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.AddressDto;
import com.holisticbabe.holisticbabemarketplace.Models.Address;

public interface AddressService {
    AddressDto findAddressById(long id);

    void insertAddress(Address address, long id);

    List<AddressDto> getAll();

    List<AddressDto> getAllByUserId(long id);

    void deleteAddress(long id);

    void updateAddress(long countryId, long addressId, long useId, AddressDto address);
}
