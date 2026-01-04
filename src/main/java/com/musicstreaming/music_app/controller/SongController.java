package com.musicstreaming.music_app.controller;

import com.musicstreaming.music_app.dao.SongRepository;
import com.musicstreaming.music_app.model.Song;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:3000")
public class SongController {

    private final SongRepository songRepository;

    private static final String MUSIC_DIR = "C:/music-files/";

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @PostMapping("/upload")
    public Song uploadSong(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        // 1️⃣ Create unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 2️⃣ Save file to disk
        File destination = new File(MUSIC_DIR + fileName);
        file.transferTo(destination);

        // 3️⃣ Save metadata to database
        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);
        song.setFileName(fileName);

        return songRepository.save(song);
    }
    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
    
    //streaming
    
    @GetMapping("/stream/{fileName}")
    public ResponseEntity<Resource> streamSong(@PathVariable String fileName) {
        try {
            Path path = Paths.get(MUSIC_DIR).resolve(fileName);
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    @GetMapping("/search")
    public List<Song> searchSongs(@RequestParam String keyword) {
        return songRepository
            .findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(
                keyword, keyword
            );
    }
    
    @PostMapping("/scan")
    public ResponseEntity<String> scanMusicDirectory() {
        File folder = new File(MUSIC_DIR);

        if (!folder.exists() || !folder.isDirectory()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Music directory not found");
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

        if (files == null) {
            return ResponseEntity.ok("No audio files found");
        }

        int addedCount = 0;

        for (File file : files) {
            String fileName = file.getName();

            // Skip if already exists
            if (songRepository.findByFileName(fileName).isPresent()) {
                continue;
            }

            // Auto-generate title & artist
            String title = fileName.replace(".mp3", "");
            String artist = "Unknown Artist";

            Song song = new Song();
            song.setTitle(title);
            song.setArtist(artist);
            song.setFileName(fileName);

            songRepository.save(song);
            addedCount++;
        }

        return ResponseEntity.ok(
            "Scan complete. Added " + addedCount + " new songs."
        );
    }



}
