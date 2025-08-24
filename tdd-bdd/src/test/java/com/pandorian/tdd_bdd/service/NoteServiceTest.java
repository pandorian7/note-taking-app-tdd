package com.pandorian.tdd_bdd.service;

import com.pandorian.tdd_bdd.entity.*;
import com.pandorian.tdd_bdd.exceptions.*;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class NoteServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    private Note note;
    private User user;

    @BeforeEach
    void init() {
        user = new User(
                "alice", "Alice@123",
                "Alice", "Rowling"
        );
        note = new Note("Note Title", "Note Content");
    }

    private User addUser() {
        return userService.signup(user);
    }

    private void setUpNote() {
        User newUser = addUser();
        note.setOwner(newUser);
    }

    private void addNote() {
        setUpNote();
        note = noteService.addNote(note);
    }

    // Creation Tests

    @Nested
    class CreateNotes {

        @Test
        void add_note_success() {
            setUpNote();
            Note ret = noteService.addNote(note);
            assertNotEquals(null, ret.getId(), "Note ID is null");
            assertNotEquals(null, ret.getOwner(), "Owner is null");
            assertEquals(ret.getOwner().getId(), user.getId(), "Owner Id differs");
            assertEquals(ret.getTitle(), note.getTitle(), "Title differs");
            assertEquals(ret.getContent(), note.getContent(), "Content differs");
        }

        @Test
        void add_note_failure_no_owner() {
            assertThrows(OwnerlessNoteException.class, () -> {
                noteService.addNote(note);
            }, "Ownerless Note");
        }

        @Test
        void add_note_failure_no_title() {
            note.setTitle("");
            setUpNote();
            assertThrows(TitleEmptyNoteException.class, () -> {
                noteService.addNote(note);
            }, "Titleless Note");
        }

        @Test
        void add_note_failure_no_content() {
            note.setContent("");
            setUpNote();
            assertThrows(ContentEmptyNoteException.class, () -> {
                noteService.addNote(note);
            }, "Contentless Note");
        }

        @Test
        void add_note_failure_content_too_long() {
            int length = NoteService.MAXIMUM_NOTE_CONTENT_LENGTH + 1;
            String content = "A".repeat(length);
            note.setContent(content);
            assertThrows(NoteContentTooLongException.class, () -> {
                noteService.addNote(note);
            }, "Note Conte Too Long");
        }
    }

    // Modification Tests

    @Nested
    class ModifyNotes {

        @Test
        void modify_note_success() {
            addNote();
            note.setTitle("Some Other Title");
            note.setContent("Some Other Content");
            Note res = noteService.modifyNote(note);
            assertEquals(note.getId(), res.getId(), "Note Id has Changed");
            assertEquals(note.getTitle(), res.getTitle(), "Note Title Update Failed");
            assertEquals(note.getContent(), res.getContent(), "Note Content Update Failed");
            assertEquals(note.getOwner().getId(), res.getOwner().getId(), "Note Owner Id has changed");
        }

        @Test
        void modify_note_failure_invalid_id() throws NoSuchFieldException, IllegalAccessException {
            setUpNote();
            Field idField = Note.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(note, 999L);
            assertThrows(NoteDoesNotExistException.class, () -> {
                noteService.modifyNote(note);
            }, "Updated Non Existing Note");
        }

        @Test
        void modify_note_failure_invalid_owner_id() throws NoSuchFieldException, IllegalAccessException {
            addUser();
            Field userIDField = User.class.getDeclaredField("id");
            userIDField.setAccessible(true);
            userIDField.set(user, 999L);
            note.setOwner(user);

            assertThrows(UserDoesNotExistException.class, () -> {
                noteService.modifyNote(note);
            }, "Updated a Note with Non-Existent User");
        }

        @Test
        void modify_note_failure_owner_changed() {
            addNote();

            assertThrows(IllegalStateException.class, () -> {
                note.setOwner(new User());
            }, "Note Owner Changed");
        }

        @Test
        void modify_note_failure_no_title() {
            addNote();
            note.setTitle("");

            assertThrows(TitleEmptyNoteException.class, () -> {
                noteService.modifyNote(note);
            }, "Updated Note with Empty Title");

            note.setTitle("     ");

            assertThrows(TitleEmptyNoteException.class, () -> {
                noteService.modifyNote(note);
            }, "Updated Note with Title with whitespaces");
        }

        @Test
        void modify_note_failure_no_content() {
            addNote();
            note.setContent("");

            assertThrows(ContentEmptyNoteException.class, () -> {
                noteService.modifyNote(note);
            }, "Updated Note with Empty Content");

            note.setContent("     ");

            assertThrows(ContentEmptyNoteException.class, () -> {
                noteService.modifyNote(note);
            }, "Updated Note with Content with whitespaces");
        }

        @Test
        void modify_note_failure_content_too_long() {
            addNote();
            int length = NoteService.MAXIMUM_NOTE_CONTENT_LENGTH + 1;
            String content = "A".repeat(length);
            note.setContent(content);

            assertThrows(NoteContentTooLongException.class, () -> {
                noteService.modifyNote(note);
            }, "Updated Note with Long Content");
        }
    }

    // Deletion Tests

    @Nested
    class DeleteNotes {

        @Test
        void delete_note_success() {
            addNote();
            Long noteId = note.getId();
            noteService.deleteNoteById(noteId);

            assertThrows(NoteDoesNotExistException.class, () -> {
                noteService.getNoteById(noteId);
            }, "Deleted Non Existing Note");
        }
    }
}
