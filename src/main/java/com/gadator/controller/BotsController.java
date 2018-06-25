package com.gadator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/bots/")
public class BotsController {

    @GetMapping
    public String botsHello(){
        log.info("Bot connected");
        return "Hello";
    }
}
