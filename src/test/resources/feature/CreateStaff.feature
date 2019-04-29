Feature: Create staff member

  Scenario: Successful staff member creation by clerk
    Given Department A exists
    And I am in the <staff> screen
    When I enter <staff_member_name>
    And I enter <staff_member_surname>
    And I choose <staff_member_department>
    And I choose <staff_member_specialisation>
    And I click button <create_staff_member>
    Then I can see new staff_member in the list