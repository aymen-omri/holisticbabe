package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.math.BigInteger;
import java.util.Optional;

import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models._User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<_User, Long> {
    
    Optional<_User> findByEmail(String email);

    Optional<_User> findByPhoneNumber(BigInteger phoneNumber);

    Optional<_User> findByUsername(String username);



}
