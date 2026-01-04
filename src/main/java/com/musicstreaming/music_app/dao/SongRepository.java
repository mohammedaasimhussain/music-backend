package com.musicstreaming.music_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musicstreaming.music_app.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByFileName(String fileName);

    List<Song> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(
        String title, String artist
    );
}
