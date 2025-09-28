package com.pandorian.tdd_bdd.controllers;
import com.pandorian.tdd_bdd.annotations.AuthRequired;
import com.pandorian.tdd_bdd.annotations.CurrentUser;
import com.pandorian.tdd_bdd.entity.Note;
import com.pandorian.tdd_bdd.entity.User;
import com.pandorian.tdd_bdd.exceptions.RequiredArgumentMissingException;
import com.pandorian.tdd_bdd.service.NoteService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @AuthRequired
    @GetMapping
    public ResponseEntity<?> getNotes(@CurrentUser User user) {
        Hibernate.initialize(user.getNotes()); // initialize the lazy collection
        return ResponseEntity.ok(Map.of(
                "user", user.getUsername(),
                "notes", user.getNotes()
        ));
    }

    @AuthRequired
    @PostMapping
    public ResponseEntity<?> addNote(@CurrentUser User user, @RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        String content = payload.get("content");

        if (title == null) {
            throw new RequiredArgumentMissingException("title");
        }
        if (content == null) {
            throw new RequiredArgumentMissingException("content");
        }

        Note note = new Note(title, content);

        note.setOwner(user);

        note = noteService.addNote(note);

        return ResponseEntity.status(201).body(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyNote(@PathVariable int id, @RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        String content = payload.get("content");

        if (title == null) {
            throw new RequiredArgumentMissingException("title");
        }
        if (content == null) {
            throw new RequiredArgumentMissingException("content");
        }

        Note note = noteService.getNoteById(id);

        note.setTitle(title);

        note.setContent(content);

        note = noteService.modifyNote(note);

        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable int id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

}
