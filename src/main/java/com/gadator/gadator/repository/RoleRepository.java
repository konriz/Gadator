package com.gadator.gadator.repository;

import com.gadator.gadator.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{

    Role findByName(String name);
}
