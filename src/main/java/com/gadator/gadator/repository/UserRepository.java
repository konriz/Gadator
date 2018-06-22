package com.gadator.gadator.repository;

import com.gadator.gadator.DTO.UserDTO;
import com.gadator.gadator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(
            "Select new com.gadator.gadator.DTO.UserDTO(u) " +
                    "from User u " +
                    "where u.name = :name"
    )
    UserDTO findOneDTOByName(@Param("name") String name);

    @Query(
            "Select new com.gadator.gadator.DTO.UserDTO(u) " +
                    "from User u " +
                    "where u.email = :email"
    )
    UserDTO findOneDTOByEmail(@Param("email") String email);

    @Query(
            "Select new com.gadator.gadator.DTO.UserDTO(u) " +
                    "from User u " +
                    "order by u.name asc"
    )
    List<UserDTO> findAllDTOByOrderByNameAsc();

    User findOneByName(String name);

}
