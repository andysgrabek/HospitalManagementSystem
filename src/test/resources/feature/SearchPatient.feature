Feature: Search Patient
  As a clerk,
  I want to have a list with filters of all registered patients
  so that I can quickly find information about every patient

  Background:
    Given Patient with name "John" and surname "Cucumber" exists
    And Patient with name "John" and surname "Apple" exists
    And I am in the "Registration" screen

  Scenario: List all patients with the same name
    When I enter "John" into "nameSearch" input
    Then I can see "Cucumber" in the "registeredPatient" list
    And I can see "Apple" in the "registeredPatient" list

  Scenario: List all patients with the same name and surname
    When I enter "John" into "nameSearch" input
    And I enter "Cucumber" into "surnameSearch" input
    Then I can see "Cucumber" in the "registeredPatient" list
    But I can not see "Apple" in the "registeredPatient" list

  Scenario: List nothing if user with given surname doesn't exists
    When I enter "John" into "nameSearch" input
    And I enter "Blueberry" into "surnameSearch" input
    Then I can not see "Cucumber" in the "registeredPatient" list
    And I can not see "Apple" in the "registeredPatient" list



