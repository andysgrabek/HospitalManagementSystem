Feature: Create staff member
  as a clerk
  I want to be able to add new staff member,
  so that I can add new maintainers or members with specific roles

  Scenario: Successful staff member creation by clerk
    Given Department "A" exists
    And I am in the "Staff" screen
    When I enter "John" into "name" input
    And I enter "Cucumber" into "surname" input
    And I pick first option in "department" comboBox
    And I pick first option in "role" comboBox
    And I click button "createStaff"
    Then I can see "Cucumber" in the "staff" list