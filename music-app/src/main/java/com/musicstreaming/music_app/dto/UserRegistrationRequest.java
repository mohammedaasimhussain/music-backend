package com.musicstreaming.music_app.dto;



public class UserRegistrationRequest {

    private String username;
    private String email;
    private String password;

    // Convert request → UserDTO (used in service)
    public UserDTO toUserDTO() {
        UserDTO dto = new UserDTO();
        dto.setUsername(this.username);
        dto.setEmail(this.email);
        return dto;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

