package finalApi.stepdefinitions;

import finalApi.domain.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static finalApi.clients.ZeplinClient.*;
import static finalApi.constants.ProjectConstants.*;
import static finalApi.helpers.RandomDataGenerator.RandomSymbolGenerator.generateRandomAlphanumeric;
import static org.hamcrest.core.IsEqual.equalTo;

public class ZeplinSteps {

 SoftAssertions softAssertions = new SoftAssertions();

    @Given("Ensure that the specific project exists in the Workspace")
    public void getWorkspaceDataAndCheckInfo() {
        Response response = getSpecificProject(PROJECT_ID);
        project = response.as(Project.class);
     softAssertions.assertThat(project.getId())
                .as("I assert that the project ID is correct!")
                .isEqualTo(PROJECT_ID);
     softAssertions.assertThat(project.getName())
                .as("I assert that the project name is correct!")
                .isEqualTo(PROJECT_NAME);
     softAssertions.assertThat(project.getDescription())
                .as("I assert that the project description is correct!")
                .isEqualTo(PROJECT_DESCRIPTION);
     softAssertions.assertAll();
        System.out.println("The 1st step was executed!");
    }

    @When("Change project name and description that includes the current date and time")
    public void changeProjectNameAndDescriptionThatIncludesTheCurrentDateAndTime() {

     ExtractableResponse<Response> response = updateProjectNameAndDescription("NewName", "NewDescription", PROJECT_ID, API_TOKEN);

     Assertions.assertThat(response.statusCode())
             .as("Status code is not 204")
             .isEqualTo(204);

     System.out.println("The 2nd step was executed!");
    }

    @And("Verify that project name and description have been successfully updated")
    public void verifyThatTheProjectSuccessfullyUpdated() {
        Response response = getSpecificProject(PROJECT_ID);

     response.then()
            .body("name", equalTo(project.getName()))
            .body("description", equalTo(project.getDescription()));

        System.out.println("The 3rd step was executed!");
    }

    @Then("Return previous project name with description")
    public void returnPreviousProjectNameWithDescription() {

        ExtractableResponse<Response> response = updateProjectNameAndDescription(PROJECT_NAME, PROJECT_DESCRIPTION, PROJECT_ID, API_TOKEN);

        Assertions.assertThat(response.statusCode())
                .as("Status code is not 204")
                .isEqualTo(204);

        System.out.println("The 4th step was executed!");
    }

    @When("Create a new color with valid values, such as a unique color code and a name")
    public void createANewColorWithValidValues() {
        ExtractableResponse<Response> response = createColor(PROJECT_ID, "Purple",147,37, 37, 0.6, API_TOKEN);
        color.setColorId(getColorInteger(response));

        softAssertions.assertThat(response.statusCode())
                .as("Status code is not 200")
                .isEqualTo(200);
        softAssertions.assertThat(color.getColorId())
                .as("I assert that the color ID is wrong!")
                .isEqualTo(response.body().jsonPath().getString("id"));
        softAssertions.assertAll();
    }

    @Then("Verify that the color is successfully created and added to the project")
    public void verifyThatTheColorIsSuccessfullyCreatedAndAddedToTheProject() {
        Response response = getProjectColors(PROJECT_ID);

        softAssertions.assertThat(response.statusCode())
                .as("Status code is not 200")
                .isEqualTo(200);
        softAssertions.assertThat(response.body().jsonPath().getString("id"))
                .as("Projects doesn't have following colorId: " + response.body().jsonPath().getString("id"))
                .contains(color.getColorId());
        softAssertions.assertAll();
        System.out.println("The 4th step was executed!");
    }

    @Then("Retrieve all colors associated with the project and validate that the newly added color is present")
    public void retrieveAllColorsAndValidateThatAddedColorIsPresent() {
    }

    @Given("Attempt to update a project with an invalid project_id that does not exist in the Workspace")
    public void attemptToUpdateProjectThatDoesNotExistInTheSystem() {

        ExtractableResponse<Response> response = updateProjectNameAndDescription(PROJECT_NAME, PROJECT_DESCRIPTION,generateRandomAlphanumeric(24), API_TOKEN);

        softAssertions.assertThat(response.statusCode())
                .as("Status code is not 400")
                .isEqualTo(400);
        softAssertions.assertThat(getErrorMessage(response))
                .as("Wrong error message")
                .contains("fails to match the required pattern");
        softAssertions.assertAll();

        System.out.println("The 1st step was executed!");
    }

    @When("Try to update the project with an empty name and description")
    public void updateProjectWithEmptyNameAndDescription() {
        ExtractableResponse<Response> response = updateProjectNameAndDescription("", "", PROJECT_ID, API_TOKEN);

        softAssertions.assertThat(response.statusCode())
                .as("Status code is not 400")
                .isEqualTo(400);
        softAssertions.assertThat(getErrorMessage(response))
                .as("Wrong error message")
                .contains("is not allowed to be empty");
        softAssertions.assertAll();

        System.out.println("The 1st step was executed!");
    }

    @Then("Send an update request with invalid authentication credentials")
    public void sendUpdateRequestWithInvalidAuthenticationCredentials() {
        ExtractableResponse<Response> response = updateProjectNameAndDescription(PROJECT_NAME, PROJECT_DESCRIPTION, PROJECT_ID, "Bearer " + generateRandomAlphanumeric(10));

        softAssertions.assertThat(response.statusCode())
                .as("Status code is not 400")
                .isEqualTo(401);
        softAssertions.assertThat(getErrorMessage(response))
                .as("Wrong error message")
                .contains("invalid_token");
        softAssertions.assertAll();

        System.out.println("The 1st step was executed!");
    }

    @Then("Update the project with a name and description containing special characters or invalid characters")
    public void updateProjectWithInvalidCharacters() {
    }

    @Given("Try to add a color to a project with an invalid project_id that does not exist in the Workspace")
    public void addColorToProjectThatDoesNotExistInSystem() {
        ExtractableResponse<Response> response = createColor(generateRandomAlphanumeric(24), "Purple",147,37, 37, 0.6, API_TOKEN);

        softAssertions.assertThat(response.statusCode())
                .as("Status code is not 400")
                .isEqualTo(400);
        softAssertions.assertThat(getErrorMessage(response))
                .as("Wrong error message")
                .contains("fails to match the required pattern");
        softAssertions.assertAll();

        System.out.println("The 1st step was executed!");
    }

    @When("Create a color with invalid color parameter, such as wrong rgba number")
    public void createAColorWithoutIncludingTheRequiredParameters() {
        ExtractableResponse<Response> response = createColor(PROJECT_ID, "Purple",500,37, 37, 0.6, API_TOKEN);

        softAssertions.assertThat(response.statusCode())
                .as("Status code is not 400")
                .isEqualTo(400);
        softAssertions.assertThat(getErrorMessage(response))
                .as("Wrong error message")
                .contains(" must be less than or equal to 255");
        softAssertions.assertAll();

        System.out.println("The 2nd step was executed!");
    }

    @Then("Attempt to add a color to a project without proper authorization")
    public void addAColorToAProjectWithoutProperAuthorization() {

        ExtractableResponse<Response> response = createColor(PROJECT_ID, "Purple",500,37, 37, 0.6, "Bearer " + generateRandomAlphanumeric(10));

        softAssertions.assertThat(response.statusCode())
                .as("Status code is not 400")
                .isEqualTo(401);
        softAssertions.assertThat(getErrorMessage(response))
                .as("Wrong authentication error message")
                .contains("invalid_token");
        softAssertions.assertAll();

        System.out.println("The 2nd step was executed!");
    }
}
