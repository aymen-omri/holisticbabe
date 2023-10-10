package com.holisticbabe.holisticbabemarketplace.Repositories.User;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models.User._User;

public interface UserRepository extends JpaRepository<_User, Long> {
    
    Optional<_User> findByEmail(String email);

    Optional<_User> findByPhoneNumber(BigInteger phoneNumber);

    Optional<_User> findByUsername(String username);

}
