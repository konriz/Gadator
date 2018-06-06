package com.gadator.gadator.service;

import com.gadator.gadator.DTO.UserDTO;
import com.gadator.gadator.entity.User;
import com.gadator.gadator.exception.EmailExistsException;
import com.gadator.gadator.exception.NameExistsException;
import com.gadator.gadator.repository.RoleRepository;
import com.gadator.gadator.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll()
    {
        return userRepository.findAllByOrderByNameAsc();
    }

    public User findByName(String name)
    {
        return userRepository.findOneByName(name);
    }


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

    private boolean emailExists(String email)
    {
        User user = userRepository.findOneByEmail(email);
        if (user != null)
        {
            return true;
        }
        return false;
    }

    private boolean nameExists(String name)
    {
        User user = userRepository.findOneByName(name);
        if (user != null)
        {
            return true;
        }
        return false;
    }

}
