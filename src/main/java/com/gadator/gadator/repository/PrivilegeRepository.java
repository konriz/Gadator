package com.gadator.gadator.repository;

import com.gadator.gadator.entity.Privilege;
import com.gadator.gadator.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer>{

    Privilege findByName(String name);
}
