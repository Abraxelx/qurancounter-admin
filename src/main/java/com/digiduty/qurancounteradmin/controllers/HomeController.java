package com.digiduty.qurancounteradmin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home1() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
}
