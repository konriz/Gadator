package com.gadator.gadator.controller;

import com.gadator.gadator.entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model)
    {
        String message = "Message";
        model.addAttribute("message", message);
        return "user/registration";
    }
}
