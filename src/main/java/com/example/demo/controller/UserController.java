package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // READ - list all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("newUser", new User());
        return "users";
    }

    // CREATE
    @PostMapping("/create")
    public String createUser(@ModelAttribute("newUser") User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    // EDIT form
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("newUser", new User());
        return "users";
    }

    // UPDATE
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id);
        userRepository.save(user);
        return "redirect:/users";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
