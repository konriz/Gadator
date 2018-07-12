package com.gadator.users.controllers;

import com.gadator.users.DTO.UserDTO;
import com.gadator.messages.services.MessagesService;
import com.gadator.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessagesService messagesService;


    @GetMapping
    @ResponseBody
    public ModelAndView currentUser(Principal principal)
    {
        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("users", userService.findAllDTO());
        return mav;
    }

    @GetMapping(value = "/{userName}")
    public ModelAndView showUserDetails(@PathVariable("userName") String userName, Principal principal)
    {
        if(userName == principal.getName())
        {
            UserDTO currentUser = userService.findDTOByName(principal.getName());
            ModelAndView mav = new ModelAndView("user/panel", "loggedUser", currentUser);
            mav.addObject("messages", messagesService.findAllMessagesByAuthor(currentUser.getName()));
            return mav;
        }

        ModelAndView mav = new ModelAndView("user/details", "user", userService.findDTOByName(userName));
        mav.addObject("messages", messagesService.findAllMessagesByAuthor(userName));
        return mav;
    }

    @GetMapping(value = "/{userName}/delete")
    public ModelAndView confirmDeleteUserAccount(@PathVariable("userName") String username)
    {
        UserDTO user = userService.findDTOByName(username);
        return new ModelAndView("user/delete", "user", user);
    }

    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    @PostMapping(value = "/{userName}/delete")
    public ModelAndView deleteUserAccount(@PathVariable("userName") String userName)
    {
        UserDTO user = userService.findDTOByName(userName);
        if(user != null)
        {
            deleteUser(user);
        }

        return new ModelAndView("user/deleted", "user", user);
    }

    private void deleteUser(UserDTO user)
    {
        messagesService.deleteAllMessagesByAuthor(user.getName());
        userService.deleteUserAccount(user.getName());
    }

}
