package com.holisticbabe.holisticbabemarketplace.Repositories.User;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models.User._Role;

public interface RoleRepository extends JpaRepository<_Role, Long> {

    _Role findByRoleName(String roleName);

}
