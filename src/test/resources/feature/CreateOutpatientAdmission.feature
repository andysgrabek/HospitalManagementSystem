Feature: Create outpatient admission
  as a clerk
  I want to be able to create patient admission,
  so that information about patient's visit is available in the system

  Background:
    Given Patient with name "John" and surname "Cucumber" exists
    And Department "A" exists
    And I am in the "Admissions" screen
    When I click button with text "Admit"

  Scenario: Admit patient as outpatient
    And I set checkbox "inpatientField" as "false"
    And I pick first option in "department" comboBox
    And I enter date "01-01-2020" into "datePicker" input
    And I enter time into "appointmentTime" input
    And I click button "admit"
    Then I can see "Cucumber" in the "registeredPatient" list