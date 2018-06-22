package com.gadator.gadator.service;

import com.gadator.gadator.DTO.UserDTO;
import com.gadator.gadator.entity.User;
import com.gadator.gadator.exception.EmailExistsException;
import com.gadator.gadator.exception.NameExistsException;

import java.util.List;

/**
 * Service for creating and deleting users
 * @author Konriz
 */
public interface UserService {

    List<UserDTO> findAll();

    UserDTO findByName(String name);

    /**
     * Create new user account
     * @param userDTO is a new userDTO
     * @return new user account
     * @throws EmailExistsException when email duplicate
     * @throws NameExistsException when name duplicate
     */
    User registerNewUserAccount(UserDTO userDTO) throws EmailExistsException, NameExistsException;

    /**
     * Delete user account
     * @param username is an username to delete
     */
    void deleteUserAccount(String username);

}
