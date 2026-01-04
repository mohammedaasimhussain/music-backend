package com.musicstreaming.music_app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicstreaming.music_app.dto.UserDTO;
import com.musicstreaming.music_app.dto.UserRegistrationRequest;
import com.musicstreaming.music_app.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserRegistrationRequest request) {
        return userService.register(request.toUserDTO(), request.getPassword());
    }
}

