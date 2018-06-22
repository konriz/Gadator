package com.gadator.gadator.controller;

import com.gadator.gadator.DTO.UserDTO;
import com.gadator.gadator.entity.User;
import com.gadator.gadator.exception.EmailExistsException;
import com.gadator.gadator.exception.NameExistsException;
import com.gadator.gadator.service.MessagesService;
import com.gadator.gadator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
        UserDTO currentUser = userService.findByName(principal.getName());
        ModelAndView mav = new ModelAndView("user/details", "loggedUser", currentUser);
        mav.addObject("messages", messagesService.findAllMessagesByAuthor(currentUser.getName()));
        return mav;
    }

    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    @GetMapping(value = "/list")
    public ModelAndView listUsers()
    {
        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("users", userService.findAll());
        return mav;
    }

    @GetMapping(value = "/{userName}")
    public ModelAndView showUserDetails(@PathVariable("userName") String userName)
    {
        ModelAndView mav = new ModelAndView("user/details", "user", userService.findByName(userName));
        mav.addObject("messages", messagesService.findAllMessagesByAuthor(userName));
        return mav;
    }

    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    @GetMapping(value = "/{userName}/delete")
    private void deleteUserAccount(@PathVariable("userName") String username)
    {
        UserDTO user = userService.findByName(username);
        if(user != null)
        {
            messagesService.deleteAllMessagesByAuthor(username);
            userService.deleteUserAccount(user.getName());
        }
    }

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
            @ModelAttribute("user") @Valid UserDTO accountDTO, BindingResult result, WebRequest request,
            Errors errors)
    {
        User registered = new User();

        if (!result.hasErrors())
        {
            registered = createUserAccount(accountDTO, result);
        }
        if (registered == null)
        {
            result.rejectValue("email", "message.regError");
        }
        if(result.hasErrors())
        {
            return new ModelAndView("user/registration", "user", accountDTO);
        }

        return new ModelAndView("user/welcome", "user", accountDTO);
    }

    private User createUserAccount(UserDTO accountDTO, BindingResult result)
    {
        User registered = null;
        try
        {
            registered = userService.registerNewUserAccount(accountDTO);
        } catch (NameExistsException | EmailExistsException e)
        {
            return null;
        }

        return registered;
    }




}
