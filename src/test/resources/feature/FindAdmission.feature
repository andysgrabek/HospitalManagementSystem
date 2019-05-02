Feature: Find admission
  as a clerk or doctor
  I want to be able to filter patient admissions,
  so that I can effortlessly search for particular patient admission

  Scenario: Find patient admission
    Given Patient with name "John" and surname "Cucumber" exists
    And Patient with name "John" and surname "Apple" exists
    And Department "A" exists
    And Bed "bed1" is assigned to Department "A"
    And Bed "bed2" is assigned to Department "A"
    And Patient with surname "Cucumber" is assigned to bed "bed1"
    And Patient with surname "Apple" is assigned to bed "bed2"
    And I am in the "Admissions" screen
    When I enter "John" into "nameSearch" input
    And I enter "Cucumber" into "surnameSearch" input
    Then I can see "Cucumber" in the "registeredPatient" list
    But I can not see "Apple" in the "registeredPatient" list
