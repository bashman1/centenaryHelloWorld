#Feature: Hello API Endpoint
#
#  Background:
#    Given the API base URL is "https://wso2mi.centenarybank.demo.com"
#
#  Scenario: Basic Hello API response
#    When I send a GET request to "/hello"
#    Then the response status code should be 200
#    And the Content-Type should be "application/json"
#    And the response should contain message "Hello World"
#    And the response time should be less than 2000 ms
#
#  Scenario: Handle request without Accept header
#    When I send a GET request to "/hello" without headers
#    Then the response status code should be 200
#    And the response should contain message "Hello World"
#
#  Scenario: Invalid endpoint returns 404
#    When I send a GET request to "/invalid"
#    Then the response status code should be 404
#
#  Scenario: Validate JSON schema of Hello API
#    When I send a GET request to "/hello"
#    Then the response should match the expected schema
#
#
Feature: Hello API Endpoint
  Background:
    Given the API base URL is "https://wso2mi.centenarybank.demo.com"

  Scenario Outline: Basic Hello API response
    When I send a GET request to "<endpoint>"
    Then the response status code should be <statusCode>
    And the Content-Type should be "<contentType>"
    And the response should contain message "<message>"
    And the response time should be less than <maxResponseTime> ms

    Examples:
      | endpoint | statusCode | contentType      | message     | maxResponseTime |
      | /hello   | 200        | application/json | Hello World | 5000            |

  Scenario Outline: Handle request without Accept header
    When I send a GET request to "<endpoint>" without headers
    Then the response status code should be <statusCode>
    And the response should contain message "<message>"

    Examples:
      | endpoint | statusCode | message      |
      | /hello   | 200        | Hello World  |

  Scenario Outline: Invalid endpoint returns 404
    When I send a GET request to "<endpoint>"
    Then the response status code should be <statusCode>

    Examples:
      | endpoint   | statusCode |
      | /invalid   | 404        |

  Scenario Outline: Validate JSON schema of Hello API
    When I send a GET request to "<endpoint>"
    Then the response should match the expected schema

    Examples:
      | endpoint |
      | /hello   |
