Feature: Search staff member
  As a clerk,
  I want to have a list with filters of all staff members
  so that I can quickly find and assign them to particular department

  Scenario: List all staff members with the same name
    Given Department "depA" exists
    And Staff member with name "John" and surname "Cucumber" exists
    And Staff member with name "John" and surname "Apple" exists
    And I am in the "Staff" screen
    When I enter "John" into "nameSearch" input
    Then I can see "Cucumber" in the "staff" list
    And I can see "Apple" in the "staff" list

  Scenario: List all staff members with the same name and surname
    When I enter "John" into "nameSearch" input
    And I enter "Cucumber" into "surnameSearch" input
    Then I can see "Cucumber" in the "staff" list
    But I can not see "Apple" in the "staff" list

  Scenario: List nothing if user with given surname doesn't exists
    When I enter "John" into "nameSearch" input
    And I enter "Blueberry" into "surnameSearch" input
    Then I can not see "Cucumber" in the "staff" list
    And I can not see "Apple" in the "staff" list



