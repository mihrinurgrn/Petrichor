package com.project.petrichor.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String root() {
        return "home";
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/passcode")
    public String passcode() {
        return "passcode";
    }


}
