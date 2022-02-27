# See
# https://github.com/intuit/karate#syntax-guide
# for how to write feature scenarios
Feature: As a developer i want to know if my spring boot application is running

Scenario: testing the get call for User  without limit default return 30

          Given url microserviceUrl
          And path '/oldUser'
          When method GET
          Then status 200

Scenario: testing the get call for User  with limit 0

          Given url microserviceUrl
          And path '/oldUser'
          And param limit = 0
          When method GET
          Then status 409


Scenario: testing the get call for User  with limit 200

          Given url microserviceUrl
          And path '/oldUser'
          And param limit = 200
          When method GET
          Then status 200

Scenario: testing the get call for User  with limit 500

          Given url microserviceUrl
          And path '/oldUser'
          And param limit = 500
          When method GET
          Then status 200

Scenario: testing the get call for User  with limit 1111 Which rate limit error

          Given url microserviceUrl
          And path '/oldUser'
          And param limit = 1111
          When method GET
          Then status 500

