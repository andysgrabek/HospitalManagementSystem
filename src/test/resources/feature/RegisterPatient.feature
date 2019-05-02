Feature: Register Patient
  As a clerk,
  I want to be able to register a patient
  So that his/her data is available in the system

  Scenario: Successful patient-registration by clerk
    Given I am in the "Registration" screen
    When I enter "John" into "name" input
    And I enter "Cucumber" into "surname" input
    And I enter date "01-01-1990" into "birthDatePicker" input
    And I enter "ABC street" into "addressLine" input
    And I enter "Copenhagen" into "city" input
    And I enter "10000" into "postalCode" input
    And I enter "123123123" into "phoneNumber" input
    And I click button "registerPatient"
    Then I can see "Cucumber" in the "registeredPatient" list
