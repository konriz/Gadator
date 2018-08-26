package com.gadator.controller;

import com.gadator.users.DTO.UserDTO;
import com.gadator.users.entities.User;
import com.gadator.users.exceptions.EmailExistsException;
import com.gadator.users.exceptions.NameExistsException;
import com.gadator.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    UserService userService;

    @RequestMapping
    public ModelAndView getHome()
    {
        ModelAndView mav = new ModelAndView("home");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();

        mav.addObject("today", dateFormat.format(cal.getTime()));

        return mav;

    }

    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public ModelAndView showRegistrationForm()
    {
        UserDTO userDTO = new UserDTO();

        ModelAndView mav = new ModelAndView("registration");
        mav.addObject("user", userDTO);
        return mav;
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
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
            return new ModelAndView("registration", "user", accountDTO);
        }

        return new ModelAndView("welcome", "user", accountDTO);
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

    @RequestMapping(value = "checkbox", method = RequestMethod.GET)
    public ModelAndView getCheckboxes(@RequestParam(value = "count", required = false, defaultValue = "10") int rowsCount)
    {
        ModelAndView mav = new ModelAndView("checkbox");

        List<Integer> counter = new ArrayList<>();
        for(int i = 1; i<=rowsCount; i++){
            counter.add(i);
        }

        mav.addObject("counter", counter);
        return mav;
    }
}
