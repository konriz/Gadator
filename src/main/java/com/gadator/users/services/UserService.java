package com.gadator.users.services;

import com.gadator.users.DTO.UserDTO;
import com.gadator.users.exceptions.EmailExistsException;
import com.gadator.users.exceptions.NameExistsException;
import com.gadator.users.entities.User;

import java.util.List;

/**
 * Service for creating and deleting users
 * @author Konriz
 */
public interface UserService {


    /**
     * Return full user by name
     * @param name username
     * @return user with username
     */
    User findOneByName(String name);

    /**
     * List all users as DTOs
     * @return all users DTOs
     */
    List<UserDTO> findAllDTO();

    /**
     *
     * @param name - username of DTO to get
     * @return DTO of user with name
     */
    UserDTO findDTOByName(String name);

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

//    TODO implement change password
//    void changePassword(UserDTO user, String password);

    boolean checkPassword(UserDTO user, String password);

}
