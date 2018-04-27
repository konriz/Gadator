package com.gadator.gadator.controller;

import com.gadator.gadator.DTO.UserDTO;
import com.gadator.gadator.entity.User;
import com.gadator.gadator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView showRegistrationForm()
    {
        UserDTO userDTO = new UserDTO();

        ModelAndView mav = new ModelAndView("user/registration");
        mav.addObject("user", userDTO);
        return mav;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerAccount(
            @ModelAttribute("user") @Valid UserDTO accountDTO)
    {
        User registered = new User();
        registered = createUserAccount(accountDTO);
        System.out.println("User registered : " + registered.getName());

        return new ModelAndView("home", "user", registered);
    }

    private User createUserAccount(UserDTO accountDTO)
    {
        User registered = new User(accountDTO.getName());
        return registered;
    }

    @GetMapping("/list")
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }



}
