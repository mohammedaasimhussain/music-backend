package com.musicstreaming.music_app.dao;




import org.springframework.data.jpa.repository.JpaRepository;


import com.musicstreaming.music_app.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
}

