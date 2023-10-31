package com.holisticbabe.holisticbabemarketplace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models._Role;

public interface RoleRepository extends JpaRepository<_Role, Long> {

    _Role findByRoleName(String roleName);

}
