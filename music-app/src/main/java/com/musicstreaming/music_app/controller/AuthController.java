
package com.musicstreaming.music_app.controller;

import org.springframework.web.bind.annotation.*;

import com.musicstreaming.music_app.dto.LoginRequest;
import com.musicstreaming.music_app.dto.UserDTO;
import com.musicstreaming.music_app.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword());
    }
}
