package com.musicstreaming.music_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musicstreaming.music_app.model.Playlist;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUserId(Long userId);
}



