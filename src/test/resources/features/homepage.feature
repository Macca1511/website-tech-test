@test @homepage
Feature: Homepage

  As a User
  I should be able see the necessary sections on the homepage
  so that I know what options are available to me

  Background:
    Given I have navigated to the Shady Meadows B&B website

  Scenario: The welcome text and subtext are present and correct
    Then the welcome text is shown as expected
    And the welcome subtext is shown as expected

  Scenario Outline: The relevant section headings and text are present and correct
    Then the relevant "<section>" heading is shown as "<expectedText>"
    Then the relevant "<subheading>" text is shown as "<expectedSubText>"
    Examples:
    | section            | expectedText                        | subheading | expectedSubText                                                            |
    | check availability | Check Availability & Book Your Stay | check in   | Check In                                                                   |
    | check availability | Check Availability & Book Your Stay | check out  | Check Out                                                                  |
    | rooms              | Our Rooms                           | rooms      | Comfortable beds and delightful breakfast from locally sourced ingredients |
    | location           | Our Location                        | location   | Find us in the beautiful Newingtonfordburyshire countryside                |
    | contact            | Contact Information                 | address    | Address                                                                    |
    | contact            | Contact Information                 | phone      | Phone                                                                      |

