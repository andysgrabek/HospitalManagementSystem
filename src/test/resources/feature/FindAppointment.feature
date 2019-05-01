Feature: Find Appointment
  As a user
  I want to be able to filter information in appointment list
  so that I can effortlessly search for particular appointment

  Background:
    Given Patient with name "John" and surname "Cucumber" exists
    And Patient with name "John" and surname "Apple" exists
    And Department "A" exists
    And Department "B" exists
    And Patient with surname "Apple" is assigned to appointment in department "A"
    And Patient with surname "Cucumber" is assigned to appointment in department "B"
    And I am in the "Patients waiting" screen

  Scenario: List all appointments
    Then I can see "Cucumber" in the "appointment" list
    And I can see "Apple" in the "appointment" list

  Scenario: List all appointments for one appointment
    When I pick first option in "department" comboBox
    Then I can see "Apple" in the "appointment" list
    But I can not see "Cucumber" in the "appointment" list




