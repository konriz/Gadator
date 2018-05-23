package com.gadator.gadator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bots/")
public class BotsController {

    @GetMapping
    public String botsHello(){
        return "Hello";
    }
}
