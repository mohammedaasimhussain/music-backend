package com.musicstreaming.music_app.controller;

import com.musicstreaming.music_app.dao.PlaylistRepository;
import com.musicstreaming.music_app.dao.SongRepository;
import com.musicstreaming.music_app.dao.UserRepository;
import com.musicstreaming.music_app.model.Playlist;
import com.musicstreaming.music_app.model.Song;
import com.musicstreaming.music_app.model.User;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "*")
public class PlaylistController {

    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

   
    public PlaylistController(
            SongRepository songRepository,
            PlaylistRepository playlistRepository,
            UserRepository userRepository
    ) {
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    // ✅ Create playlist
    @PostMapping
    public Playlist createPlaylist(
            @RequestParam String name,
            @RequestParam Long userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setUser(user);

        return playlistRepository.save(playlist);
    }

    // ✅ Add song to playlist
    @PostMapping("/{playlistId}/songs/{songId}")
    public Playlist addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
        }

        return playlistRepository.save(playlist);
    }
   
    
 // Get all playlists of a user
    @GetMapping("/user/{userId}")
    public List<Playlist> getUserPlaylists(@PathVariable Long userId) {
        return playlistRepository.findByUserId(userId);
    }

    // Get songs inside a playlist
    @GetMapping("/{playlistId}")
    public Playlist getPlaylist(@PathVariable Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
    }
    
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public Playlist removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlist.getSongs().removeIf(song -> song.getId().equals(songId));

        return playlistRepository.save(playlist);
    }




}
