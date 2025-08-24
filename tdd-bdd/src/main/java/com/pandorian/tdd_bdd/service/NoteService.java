package com.pandorian.tdd_bdd.service;

import com.pandorian.tdd_bdd.entity.Note;
import com.pandorian.tdd_bdd.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public final static int MAXIMUM_NOTE_CONTENT_LENGTH = 100;

    public Note addNote(Note note) {
        return noteRepository.save(note);
    }

    public Note modifyNote(Note note) {
        return new Note();
    }

    public void deleteNoteById(Long id) {

    }

    public Note getNoteById(Long id) {
        return new Note();
    }

    public Long count() {
        return -1L;
    }
}
