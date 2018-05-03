package com.bincontrol.binstoreserver.controller;

import com.bincontrol.binstoreserver.entity.User;
import com.bincontrol.binstoreserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "add")
    public @ResponseBody String addNewUser(@RequestParam String name) {

        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

}
