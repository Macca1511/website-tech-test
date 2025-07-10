@reservation @defect
Feature: Making a reservation

  As a User
  I should be able to make a room reservation
  so that I can stay at the Shady Meadows B&B

  #ShadyMeadows Bug. No.1 - test has intermittent failures due to the option to book a room disappearing.
  Scenario Outline: User is able to make a booking for each room type
    Given I have navigated to the Shady Meadows B&B website
    And I scroll to the our rooms section
    And I have select to book a <type> room
    When I click reserve now
    And I complete the booking with the following details:
      | forename | <forename> |
      | lastname | <lastname> |
      | email    | <email>    |
      | phone    | <phone>    |
    And I click to confirm reservation
    Then I should see either booking confirmation or an error message
    Examples:
      | type   | forename  | lastname | email              | phone       |
      | single | John      | Test     | test1@test.nowhere | 01134567890 |
      | double | Johnny    | Tester   | test2@test.nowhere | 01234567890 |
      | suite  | Johnathan | Testing  | test3@test.nowhere | 01334567890 |