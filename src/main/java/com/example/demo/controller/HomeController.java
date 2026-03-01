package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class HomeController {

    // GET / → "Hello world!" + server time + name input form
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("serverTime", new Date());
        return "home";
    }

    // POST /user → "Hi {userName}"
    @PostMapping("/user")
    public String loginUser(@RequestParam("userName") String userName, Model model) {
        model.addAttribute("userName", userName);
        return "user";
    }
}
