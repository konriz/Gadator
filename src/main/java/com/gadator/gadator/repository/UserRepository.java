package com.gadator.gadator.repository;

import com.gadator.gadator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findOneByName(String name);

    User findOneByEmail(String email);

    List<User> findAllByOrderByNameAsc();
}
