package com.gadator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@RequestMapping("/")
public class HomeController {

    @RequestMapping
    public ModelAndView getHome()
    {
        ModelAndView mav = new ModelAndView("home");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();

        mav.addObject("today", dateFormat.format(cal.getTime()));

        return mav;

    }
}
