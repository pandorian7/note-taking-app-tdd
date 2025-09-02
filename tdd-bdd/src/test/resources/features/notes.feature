Feature: Notes
  Scenario: Successful Note Add
    Given Alice Rowling has signed up and logged in
    When user submits a valid note
    Then note has an id
    
  Scenario: note add failed because of user not provided
    When user submits a valid note
    Then throws error OwnerlessNoteException

  Scenario: note add failed because of title not provided
    Given Alice Rowling has signed up and logged in
    Then user changes note title to ""
    Then user changes note content to "Note Content"
    When user add note
    Then throws error TitleEmptyNoteException

  Scenario: note add failed because of content not provided
    Given Alice Rowling has signed up and logged in
    Then user changes note title to "Note Title"
    Then user changes note content to ""
    When user add note
    Then throws error ContentEmptyNoteException

  Scenario: Successful note modification
    Given Alice Rowling has signed up and logged in
    And user submits a valid note
    Then user changes note to another valid note
    Then user save note modifications
    Then note title should be "Other Title"
    And note content should be "Other Content"

  Scenario: note modification failed because of title not provided
    Given Alice Rowling has signed up and logged in
    And user submits a valid note
    When user changes note title to ""
    And user save note modifications
    Then throws error TitleEmptyNoteException

  Scenario: note modification failed because of content not provided
    Given Alice Rowling has signed up and logged in
    And user submits a valid note
    When user changes note content to ""
    And user save note modifications
    Then throws error ContentEmptyNoteException
