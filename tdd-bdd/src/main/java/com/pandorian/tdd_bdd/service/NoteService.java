package com.pandorian.tdd_bdd.service;

import com.pandorian.tdd_bdd.entity.Note;
import com.pandorian.tdd_bdd.exceptions.*;
import com.pandorian.tdd_bdd.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    public final static int MAXIMUM_NOTE_CONTENT_LENGTH = 100;

    public Note addNote(Note note) {

        if (note.getOwner() == null) throw new OwnerlessNoteException();

        if (note.getTitle().isBlank()) throw new TitleEmptyNoteException();

        if (note.getContent().isBlank()) throw new ContentEmptyNoteException();

        int contentLength = note.getContent().length();
        if (contentLength > MAXIMUM_NOTE_CONTENT_LENGTH)
            throw new NoteContentTooLongException(contentLength, MAXIMUM_NOTE_CONTENT_LENGTH);

        return noteRepository.save(note);
    }

    public Note modifyNote(Note note) {

        if (note.getId() == null) throw new NullNoteIdException();

        if (!noteRepository.existsById(note.getId())) throw new NoteDoesNotExistException(note.getId());

        if (note.getOwner() == null || note.getOwner().getId() == null)
            throw new NullNoteOwnerException();

        if (!userService.existsById(note.getOwner().getId()))
            throw new UserDoesNotExistException(note.getOwner().getId());

        if (note.getTitle().isBlank()) throw new TitleEmptyNoteException();

        return noteRepository.save(note);
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
