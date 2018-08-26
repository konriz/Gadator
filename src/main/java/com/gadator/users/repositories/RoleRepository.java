package com.gadator.users.repositories;

import com.gadator.users.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{

    Role findByName(String name);
}
