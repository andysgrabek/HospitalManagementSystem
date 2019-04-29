Feature: Register

  Scenario: Successful patient-registration by clerk
    Given I am in the <register_patient> screen
    When I enter <name>
    And I enter <surname>
    And I enter <birth-date>
    And I enter <home-address>
    And I enter <phone-number>
    And I click button <submit>
    Then I can see new patient in the list