package com.gadator.users.repositories;

import com.gadator.users.DTO.UserDTO;
import com.gadator.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(
            "Select new com.gadator.users.DTO.UserDTO(u) " +
                    "from User u " +
                    "where u.name = :name"
    )
    UserDTO findOneDTOByName(@Param("name") String name);

    @Query(
            "Select new com.gadator.users.DTO.UserDTO(u) " +
                    "from User u " +
                    "where u.email = :email"
    )
    UserDTO findOneDTOByEmail(@Param("email") String email);

    @Query(
            "Select new com.gadator.users.DTO.UserDTO(u) " +
                    "from User u " +
                    "order by u.name asc"
    )
    List<UserDTO> findAllDTOByOrderByNameAsc();

//    @Query(
//            "UPDATE users " +
//                    "SET password = :password " +
//                    "WHERE name = :username"
//    )
//    void updatePassword(@Param("username") String username, @Param("password")String password);

    User findOneByName(String name);

}
