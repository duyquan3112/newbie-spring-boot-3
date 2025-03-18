package com.newbie.identityService.repository;

import com.newbie.identityService.entity.Permission;
import com.newbie.identityService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByName(String name);
}
