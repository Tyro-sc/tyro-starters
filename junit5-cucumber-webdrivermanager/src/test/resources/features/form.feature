Feature: My Feature

  Scenario: Complete the form
    Given user visits our website
    When user gives 'my@email.org' as Email
    And user gives '1234' as Password
    And user chooses 'FR' as Language
    Then filled values are:
      | Email    | my@email.org |
      | Password | 1234         |
      | Language | FR           |