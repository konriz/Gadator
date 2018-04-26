package com.gadator.gadator.controller;

import com.gadator.gadator.DTO.UserDTO;
import com.gadator.gadator.entity.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

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

}
