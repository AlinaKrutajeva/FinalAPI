@Zeplin
Feature: Zeplin API test feature
  I'm working on automating tests for a subset of the Zeplin API. The focus is on projects and
  colors functionalities.

  @UpdateProject(PositiveScenario)
  Scenario: Update project with a unique name and description which includes current date and time
    Given Ensure that the specific project exists in the Workspace
    When Change project name and description that includes the current date and time
    And Verify that project name and description have been successfully updated
    Then Return previous project name with description

    @AddColorToTheProject(PositiveScenario)
    Scenario: Add a new color to the project and verify that it was successfully added to the project by sending a request to obtain all colors
      Given Ensure that the specific project exists in the Workspace
      When Create a new color with valid values, such as a unique color code and a name
      Then Verify that the color is successfully created and added to the project

    @UpdateProject(NegativeScenario)
      Scenario: Update project: /projects/{project_id}
        Given Attempt to update a project with an invalid project_id that does not exist in the Workspace
        When Try to update the project with an empty name and description
        Then Send an update request with invalid authentication credentials

    @CreateColor(NegativeScenario)
      Scenario: Create color: /projects/{project_id}/colors
        Given Try to add a color to a project with an invalid project_id that does not exist in the Workspace
        When Create a color with invalid color parameter, such as wrong rgba number
        Then Attempt to add a color to a project without proper authorization
