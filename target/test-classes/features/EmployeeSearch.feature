Feature: US-321 Searching the employee

  Background:

    When user enters valid username and valid password
    And user clicks on login button
    Then user is successfully logged in
    When user clicks on PIM option
    And user clicks on EmployeeList option

  @batch14 @sprint4
  Scenario: Search employee by id
    #Given user is navigated to HRMS application

    When user enters valid employee id
    And user clicks on search button
    Then user see employee information is displayed


  @test @sprint4
  Scenario: Search employee by name
    #Given user is navigated to HRMS application
    #we added the below in the background
    #When user enters valid username and valid password
    #And user clicks on login button
    #Then user is successfully logged in
    #When user clicks on PIM option
    #And user clicks on EmployeeList option
    When user enters valid employee name
    And user clicks on search button
    Then user see employee information is displayed
