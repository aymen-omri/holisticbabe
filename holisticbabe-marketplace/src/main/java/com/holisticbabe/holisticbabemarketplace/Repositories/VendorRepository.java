package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    @Query("select v from Vendor v where approved=false")
    List<Vendor> getNonApprovedVendors();

    @Query("select v from Vendor v where v.user.id_user = ?1")
    Optional<Vendor> getByUserId(long id);

}
