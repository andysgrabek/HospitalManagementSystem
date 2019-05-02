Feature: Create inpatient admission
  as a clerk
  I want to be able to create patient admission,
  so that information about patient's visit is available in the system

  Background:
    Given Patient with name "John" and surname "Cucumber" exists
    And Department "A" exists
    And Bed "bed1" is assigned to Department "A"
    And I am in the "Admissions" screen
    When I click button with text "Admit"

  Scenario: Admit patient as inpatient
    Given I set checkbox "inpatientField" as "true"
    And I pick first option in "department" comboBox
    And I pick first option in "bed" comboBox
    And I click button "admit"
    Then I can see "Cucumber" in the "registeredPatient" list
