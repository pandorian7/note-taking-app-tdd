package com.pandorian.tdd_bdd.steps;

//import com.pandorian.tdd_bdd.CucumberSpringConfiguration;
import com.pandorian.tdd_bdd.entity.Note;
import com.pandorian.tdd_bdd.entity.User;
import com.pandorian.tdd_bdd.service.NoteService;
import com.pandorian.tdd_bdd.service.UserService;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NoteSteps2 {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private NoteService noteService;
//
//    private Note note;
//    private User user;
//
//    @Given("user {string} {string} exists with username {string} and password {string}")
//    public void user_is_logged_in(String firstName, String lastName, String username, String password) {
//        var newUser = new User(username, password, firstName, lastName);
//        user = userService.signup(newUser);
//
//
//    }
//
//    @Given("Alice Rowling create an account")
//    @Given("Alice Rowling has created an account")
//    public void alice_rowling_has_account() {
//        user_is_logged_in(
//                "Alice", "Rowling",
//                "alice", "Alice@123"
//        );
//    }
//
//    @When("user submits note {string} with title {string}")
//    public void user_submits_note(String content, String title) {
//        var newNote = new Note("Note Title", "Note Content");
//        newNote.setOwner(user);
//        note = noteService.addNote(newNote);
//    }
//
//    @When("user submits a valid note")
//    public void user_submits_valid_note() {
//        user_submits_note("Note Content", "Note Title");
//    }
//
//    @When("note is updated to title {string} content {string}")
//    public void note_update(String title, String content) {
//        note.setTitle(title);
//        note.setContent(content);
//    }
//
//    @Then("note should have an id")
//    public void user_gets_a_note_with_an_id() {
//        assertNotEquals(null, note.getId(), "Note ID is null");
//    }
//
//    @Then("note title and content should be {string} and {string}")
//    public void note_title_and_content_should_be(String title, String content) {
//        assertEquals(title, note.getTitle());
//        assertEquals(content, note.getContent());
//    }
}
