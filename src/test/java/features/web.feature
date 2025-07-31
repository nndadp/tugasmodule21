Feature: Test Automation Rest Api dan Web UI

  @ui
  Scenario: Login with valid username and password
    Given user is on login page
    When user input username with "standard_user"
    And user input password with "secret_sauce"
    And user click login button
    Then user will redirect to homepage

  @ui
  Scenario: Login with invalid username and password
    Given user is on login page
    When user input username with "user_lain"
    And user input password with "coba_coba"
    And user click login button
    Then A message appears "Username and password do not match any user in this service"

  @ui
  Scenario: Login with empty password
    Given user is on login page
    When user input username with "standard_user"
    And user input password with ""
    And user click login button
    Then A message appears "Password is required"
