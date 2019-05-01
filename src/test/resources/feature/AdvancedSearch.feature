Feature: Advanced Search
  as a clerk
  I want to be able to write advanced SQL commands,
  so that I can search for any kind of information in database

  Scenario: Execute advanced search command
    Given Department "depA" exists
    And I am in the "Advanced search" screen
    When I enter "SELECT name FROM Department" into "queryText" input
    And I set checkbox "saveCheckBox" as "false"
    And I click button "executeQuery"
    Then I can see "depA" in table "tableView"