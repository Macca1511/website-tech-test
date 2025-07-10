@accessibility @defect
Feature: Check accessibility

  As a User with accessibility issues
  I should be able to navigate the Shady Meadows website
  so that I can make a reservation to stay

  #ShadyMeadows Bug No.012
  #this test fails as the current accessibility score is below 80.0.
  Scenario: Check accessibility of the homepage using Lighthouse
  Given I have navigated to the Shady Meadows B&B website
  Then I am able to run a Lighthouse accessibility scan

  #this test fails due to the accessibility scanner being unable to scan the page.
  Scenario: Check accessibility of the book this room page using Lighthouse
    Given I have navigated to the Shady Meadows B&B website
    When I have select to book a single room
    Then I am able to run a Lighthouse accessibility scan