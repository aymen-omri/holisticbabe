package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models._Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<_Role, Long> {
}
