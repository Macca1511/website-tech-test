@test @validation
Feature: Error validation

  As a User
  I should be able to see appropriate error validation
  So I know what information is required to be able to complete my request

  Background:
    Given I have navigated to the Shady Meadows B&B website

  Scenario Outline: The error validation text is generated on the contact section
    When I scroll to the contact form
    And I fill in the contact form with the following details:
      | name    | <name>    |
      | email   | <email>   |
      | phone   | <phone>   |
      | subject | <subject> |
      | message | <message> |
    And I click submit contact details
    Then the following error validation message is shown <error_message>

    Examples:
      | name       | email             | phone       | subject         | message                 | error_message                                  |
      | blank      | test@test.nowhere | 01234567890 | testing subject | testing sending contact | Name may not be blank                          |
      | Joe Bloggs | blank             | 01234567890 | testing subject | testing sending contact | Email may not be blank                         |
      | Joe Bloggs | test@test.nowhere | 01234567890 | testing subject | test                    | Message must be between 20 and 2000 characters.|
      | Joe Bloggs | test              | 01234567890 | testing subject | testing sending contact | must be a well-formed email address            |
      | Joe Bloggs | test@test.nowhere | 012345      | testing subject | testing sending contact | Phone must be between 11 and 21 characters.    |
