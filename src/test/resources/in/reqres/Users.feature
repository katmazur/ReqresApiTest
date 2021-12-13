Feature: Users API Tests

  Scenario: Create a new user
    Given I have a random user
    When I send POST request to 'api/users/' endpoint
    Then I see status code 201
    And I see user data in response body


  Scenario: Update user
    Given I have created user
    When I update user job leader using PUT request
    Then I see status code 200
    And I see updated user job leader in response body

  Scenario: Delete user
    Given I have created user
    When I delete user using DELETE request
    Then I see status code 204
    And I see deleted user not found

  Scenario: Register user successful
    Given I have random user with e-mail and password
    When I send POST request to 'api/register' endpoint
    Then I see status code 200
    And I see registered user found when send GET request
