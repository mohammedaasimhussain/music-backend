package com.musicstreaming.music_app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 🔗 Playlist belongs to a User (ENTITY)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🎵 Playlist contains many Songs
    @ManyToMany
    @JoinTable(
        name = "playlist_songs",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs;

    public Playlist() {}

    // 🔹 Getters & Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {   // ✅ FIXED
        return user;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(User user) {   // ✅ FIXED
        this.user = user;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
