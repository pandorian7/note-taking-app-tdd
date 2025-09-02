package com.pandorian.tdd_bdd.steps;

import com.pandorian.tdd_bdd.entity.Note;
import com.pandorian.tdd_bdd.entity.User;
import com.pandorian.tdd_bdd.exceptions.ApplicationException;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class TestContext {
     public User user;
     public Note note;
     public ApplicationException error;
}
