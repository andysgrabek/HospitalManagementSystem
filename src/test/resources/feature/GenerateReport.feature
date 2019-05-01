Feature: Generate report
  As a user
  I want to be able to generate report about patient admissions
  so that I can output information about patient to another service or place

  Scenario: Generate report for all patients
    Given Patient with name "John" and surname "Cucumber" exists
    And Patient with name "John" and surname "Apple" exists
    And Department "A" exists
    And Patient with surname "Apple" is assigned to appointment in department "A"
    And I am in the "Departments" screen
    When I click button with text "ALL"
    And I press enter button
    Then Report is generated





