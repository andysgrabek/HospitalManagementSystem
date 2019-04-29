Feature: Create department

  Scenario: Successful department creation by clerk
    Given I am in the <department> screen
    When I enter <department_name>
    And I click button <create_department>
    Then I can see new department in the list