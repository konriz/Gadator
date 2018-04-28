package com.gadator.gadator.repository;

import com.gadator.gadator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findOneByName(String name);

    User findOneByEmail(String email);
}
