package com.musicstreaming.music_app.service.impl;



import com.musicstreaming.music_app.dao.UserRepository;
import com.musicstreaming.music_app.dto.UserDTO;
import com.musicstreaming.music_app.model.User;
import com.musicstreaming.music_app.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO register(UserDTO userDTO, String rawPassword) {

        // DTO → Entity
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(rawPassword);

        User saved = userRepository.save(user);

        // Entity → DTO
        UserDTO response = new UserDTO();
        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        response.setEmail(saved.getEmail());

        return response;
    }
    
    @Override
    public UserDTO login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        return dto;
    }

}

