package com.gadator.users.repositories;

import com.gadator.users.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer>{

    Privilege findByName(String name);
}
