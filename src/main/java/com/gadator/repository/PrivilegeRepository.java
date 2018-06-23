package com.gadator.repository;

import com.gadator.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer>{

    Privilege findByName(String name);
}
