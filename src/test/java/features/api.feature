Feature: Test Automation Rest Api dan Web UI

  @api
  Scenario: Test get list data normal
    Given prepare url valid for "GET_LIST_USERS"
    And hit api get list users
    Then validation status code is equals 200
    Then validation response body get list users
    Then validation response json with JSONSchema "get_list_users_normal.json"

  @api
  Scenario: Test create new user normal
    Given prepare url valid for "CREATE_NEW_USERS"
    And hit api post create new user
    Then validation status code is equals 201
    Then validation response body create new user
    Then validation response json with JSONSchema "create_users_normal.json"

  @api
  Scenario: Test delete user normal
    Given prepare url valid for "GET_LIST_USERS"
    And hit api get list users
    And validation response body get list users
    Given prepare url valid for "DELETE_USERS"
    And hit api delete
    Then validation status code is equals 204