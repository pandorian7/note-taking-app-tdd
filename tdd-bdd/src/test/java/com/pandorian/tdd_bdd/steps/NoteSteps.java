package com.pandorian.tdd_bdd.steps;

import com.pandorian.tdd_bdd.entity.Note;
import com.pandorian.tdd_bdd.service.NoteService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class NoteSteps extends StepsClass {
    @Autowired
    NoteService noteService;

    Note getNote() {
        return new Note("Note Title", "Note Content");
    }

    @When("user submits note {string} with title {string}")
    public void user_submits_note(String content, String title) {
        var note = new Note(title, content);
        note.setOwner(context.user);

        collectApplicationError(() -> {
            context.note = noteService.addNote(note);
        });
    }

    @When("user submits a valid note")
    public void user_submits_valid_note() {
        var note = getNote();
        note.setOwner(context.user);

        collectApplicationError(() -> {
            context.note = noteService.addNote(note);
        });
    }

    @Then("note has an id")
    public void note_has_id() {
        assertNotEquals(null, context.note);
        assertNotEquals(null, context.note.getId());
    }

    @When("user changes note title to {string}")
    public void update_note_title(String title) {
        if (context.note == null) context.note = new Note();
        context.note.setTitle(title);
    }

    @When("user changes note content to {string}")
    public void update_note_content(String content) {
        if (context.note == null) context.note = new Note();
        context.note.setContent(content);

    }

    @When("user changes note title to {string} and note content to {string}")
    public void update_note(String title, String content) {
        context.note.setTitle(title);
        context.note.setContent(content);
    }

    @Then("user save note modifications")
    public void save_note_modifications() {
        collectApplicationError(() -> {
            context.note = noteService.modifyNote(context.note);
        });
    }

    @When("user add note")
    public void user_save_note() {
        collectApplicationError(() -> {
            context.note.setOwner(context.user);
            context.note = noteService.addNote(context.note);
        });
    }

    /**
     * Updates the currently loaded note title and content <br>
     *  Title    --> Other Title <br>
     *  Content  --> Other Content
     */
    @When("user changes note to another valid note")
    public void update_to_valid_note() {
        update_note("Other Title", "Other Content");
    }

    @Then("note title should be {string}")
    public void check_note_title(String title) {
        assertEquals(title, context.note.getTitle());
    }

    @Then("note content should be {string}")
    public void check_note_content(String content) {
        assertEquals(content, context.note.getContent());
    }
}
