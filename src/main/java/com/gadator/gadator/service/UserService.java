package com.gadator.gadator.service;

import com.gadator.gadator.DTO.UserDTO;
import com.gadator.gadator.entity.User;
import com.gadator.gadator.exception.EmailExistsException;
import com.gadator.gadator.exception.NameExistsException;
import com.gadator.gadator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        User registered = new User();
        registered.setName(accountDTO.getName());
        registered.setEmail(accountDTO.getEmail());
        registered.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        registered.setRole("USER");

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
