package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models._User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<_User, Long> {


}
