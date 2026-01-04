package com.musicstreaming.music_app.service;


import com.musicstreaming.music_app.dto.UserDTO;

public interface UserService {

    UserDTO register(UserDTO userDTO, String rawPassword);
    UserDTO login(String email, String password);

}

