package com.gadator.users.services;

import com.gadator.users.DTO.UserDTO;
import com.gadator.users.exceptions.EmailExistsException;
import com.gadator.users.entities.User;
import com.gadator.users.exceptions.NameExistsException;
import com.gadator.users.repositories.RoleRepository;
import com.gadator.users.repositories.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Log
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> findAllDTO()
    {
        return userRepository.findAllDTOByOrderByNameAsc();
    }

    @Override
    public UserDTO findDTOByName(String name)
    {
        return userRepository.findOneDTOByName(name);
    }

    @Override
    public User findOneByName(String name)
    {
        return userRepository.findOneByName(name);
    }


    @Override
    @Transactional
    public User registerNewUserAccount(UserDTO accountDTO) throws EmailExistsException, NameExistsException
    {
        if(emailExists(accountDTO.getEmail()))
        {
            throw new EmailExistsException("There is an account with that email adress: " + accountDTO.getEmail());
        }

        if(nameExists(accountDTO.getName()))
        {
            throw new NameExistsException("There is an account with that name: " + accountDTO.getName());
        }

        log.info("Creating user " + accountDTO.getName());
        User registered = new User();
        registered.setName(accountDTO.getName());
        registered.setEmail(accountDTO.getEmail());
        registered.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        registered.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        return userRepository.save(registered);
    }

    @Override
    @Transactional
    public void deleteUserAccount(String username)
    {
        log.info("Deleting user " + username);
        if(nameExists(username))
        {
            userRepository.delete(userRepository.findOneByName(username));
        }
    }

    private boolean emailExists(String email)
    {
        UserDTO user = userRepository.findOneDTOByEmail(email);
        if (user != null)
        {
            return true;
        }
        return false;
    }

    private boolean nameExists(String name)
    {
        UserDTO user = userRepository.findOneDTOByName(name);
        if (user != null)
        {
            return true;
        }
        return false;
    }

    @Override
    public void changePassword(UserDTO user, String password)
    {

        String encodedPassword = passwordEncoder.encode(password);
        String username = user.getName();

        userRepository.updatePassword(username, encodedPassword);

    }

    @Override
    public boolean checkPassword(UserDTO user, String password)
    {
        String username = user.getName();

        return passwordEncoder
                .matches(password,
                        userRepository.findOneByName(username).getPassword());
    }



}
