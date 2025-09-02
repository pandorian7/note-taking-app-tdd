Feature: Users - BDD
  
  Scenario: sign up successful
    When Alice Rowling has signed up

  Scenario: user cannot sign up because username is taken
    Given Alice Rowling has signed up
    Then Alice Rowling sign up
    Then throws error UserAlreadyExistsException

  Scenario: user cannot sign up because of empty username
    When user "Alice" "Rowling" sign up with "" "Alice@123"
    Then throws error UsernameEmptyException
  Scenario: user cannot sign up because of short username
    When user "Alice" "Rowling" sign up with "ali" "Alice@123"
    Then throws error UsernameTooShortException

  Scenario: user cannot sign up because of empty password
    When user "Alice" "Rowling" sign up with "alice" ""
    Then throws error PasswordEmptyException

  Scenario: user cannot sign up because the password is not strong enough (no caps)
    When user "Alice" "Rowling" sign up with "alice" "alice@123"
    Then throws error PasswordNotComplexEnoughException

  Scenario: user cannot sign up because of empty password (no simple letters)
    When user "Alice" "Rowling" sign up with "alice" "ALICE@123"
    Then throws error PasswordNotComplexEnoughException


  Scenario: user cannot sign up because of empty password (no numbers)
    When user "Alice" "Rowling" sign up with "alice" "alice@Alice"
    Then throws error PasswordNotComplexEnoughException


  Scenario: user cannot sign up because of empty password (no symbols)
    When user "Alice" "Rowling" sign up with "alice" "aliceALICE"
    Then throws error PasswordNotComplexEnoughException


  Scenario: user cannot sign up because of empty first name
    When user "" "Rowling" sign up with "alice" "Alice@123"
    Then throws error NameEmptyException

  Scenario: user cannot sign up because of empty last name
    When user "Alice" "" sign up with "alice" "Alice@123"
    Then throws error NameEmptyException