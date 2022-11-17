package com.homihq.manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@Slf4j
@RequestMapping("/home")
public class HomeController {

    @PostMapping("")
    public String showHomePage() {
        return "home";
    }
}
