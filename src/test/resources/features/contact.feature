@test @contact
Feature: Send a message

  As a User
  I should be able to contact the hosts via the send us a message section
  so that I can have my query answered

  Background:
    Given I have navigated to the Shady Meadows B&B website

  Scenario: The contact section is shown as expected
    Then the send us a message section is shown as expected

  Scenario Outline: User is able to submit contact form
    When I scroll to the contact form
    And I fill in the contact form with the following details:
      | name    | <name>    |
      | email   | <email>   |
      | phone   | <phone>   |
      | subject | <subject> |
      | message | <message> |
    And I click submit contact details
    Then the following thanks for getting in touch message is shown <confirmation_message>

    Examples:
      | name       | email                  | phone       | subject                | message                        | confirmation_message                     |
      |John Tester | test@test.nowhere      | 01234567890 | testing subject        | testing sending contact        | Thanks for getting in touch John Tester! |
      |Joe Bloggs  | testagain@test.nowhere | 08924567890 | second testing subject | second testing sending contact | Thanks for getting in touch Joe Bloggs!  |
