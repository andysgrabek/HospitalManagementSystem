Feature: Register

  Scenario: Successful patient-registration by clerk
    Given I am in the <register_patient> screen
    When I enter <name>
    And I enter <surname>
    And I enter <birth-date>
    And I enter <home-address>
    And I enter <phone-number>
    And I enter <tribe>
    And I enter <alive/dead>
    And I click button <submit>
    Then new patient is created
    And I am redirected to <clerk_panel> screen