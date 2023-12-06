package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select a from Address a where a.user.id_user = ?1")
    List<Address> getAllByUserId(long id);

}
