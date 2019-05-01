Feature: Create department
  as a clerk or doctor
  I want to be able to create department,
  so that I can group patients in the better way

  Scenario: Successful department creation by clerk
    Given I am in the "Departments" screen
    When I enter "A" into "name" input
    And I click button "createDepartment"
    Then I can see "A" in the "departments" list