Feature: User Registration

  Scenario Outline: Attempt to register a user
    Given the user is on the registration page using browser "<browser>"
    And the user enters the date of birth "<date>"
    And the user enters first "<firstName>" and last "<lastName>" name
    And the user enters an email and confirms it
    And the user enters a password "<password>"
    And the user retypes the password "<retypePwd>"
    And the user marks the terms and conditions "<terms>"
    And the user marks the checkbox for age confirmation
    And the user marks the checkbox for the code of ethics and conduct
    When the user clicks the confirm and join button
    Then the user should see the message "<expMessage>" "<status>"


    Examples:
      | browser | date       | firstName | lastName | password | retypePwd | terms     | expMessage                                                                | status  |
      | chrome  | 15/02/2000 | Anna      | Anka     | Pass135  | Pass135   | Checked   | THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND                 | succeed |
      | chrome  | 15/02/2000 | Britta    |          | Pass135  | Pass135   | Checked   | Last Name is required                                                     | fail    |
      | edge    | 15/02/2000 | Stina     | Anka     | Pass135  | Pass137   | Checked   | Password did not match                                                    | fail    |
      | edge    | 15/02/2000 | Lisa      | Anka     | Pass135  | Pass135   | Unchecked | You must confirm that you have read and accepted our Terms and Conditions | fail    |