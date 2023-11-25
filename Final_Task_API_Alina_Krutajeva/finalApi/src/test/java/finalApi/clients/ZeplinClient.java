package finalApi.clients;

import finalApi.domain.Color;
import finalApi.domain.Project;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static finalApi.constants.ProjectConstants.*;

public class ZeplinClient {

   public static Project project = new Project();

   public static Color color = new Color();

    public static Response getSpecificProject(String projectId){
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", API_TOKEN)
                .when()
                .get("https://api.zeplin.dev/v1/projects/" + projectId)
                .then().log().all()
                .statusCode(200)
                .extract().response();
    }

    public static ExtractableResponse<Response> updateProjectNameAndDescription(String name, String description, String projectId, String token) {
         String nameAndDescriptionJson = """
            {"name": "%s", "description": "%s"}
            """.formatted(name, description).trim();

         project.setName(name);
         project.setDescription(description);

        return RestAssured
                .given().baseUri("https://api.zeplin.dev/v1/projects/" + projectId)
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(nameAndDescriptionJson)
                .when().patch()
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> createColor(String projectId, String colorname, Integer red, Integer green,
                                                                                Integer blue, Double transparent, String token) {
        String colorJson = """
          {"name": "%s", "r": %s,  "g": %s,  "b": %s,  "a": %s}                            
          """.formatted(colorname, red, green, blue, transparent).trim();

        return RestAssured
                .given().baseUri("https://api.zeplin.dev/v1/projects/" + projectId + "/colors")
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(colorJson)
                .when().post()
                .then().log().all()
                .extract();
    }

    public static String getColorInteger(ExtractableResponse<Response> response) {
        return response.jsonPath().get("id");
    }

    public static String getErrorMessage(ExtractableResponse<Response> response) {
        return response.jsonPath().get("message");
    }

    public static Response getProjectColors(String projectId){
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", API_TOKEN)
                .when().get("https://api.zeplin.dev/v1/projects/" + projectId + "/colors")
                .then().log().all()
                .statusCode(200)
                .extract().response();
    }

}

//    public static Response updateProjectNameAndDescription111(String name, String description){
//        return RestAssured
//                .given().log().all()
////                .accept("\"accept\", \"application/json\"")
//                .contentType(ContentType.JSON)
//                .header("authorization", API_TOKEN)
////                .contentType("application/json")
//                .queryParam("name", name)
//                .queryParam("description", description)
////                .body(newName)
////                .body(newDescription)
//                .when()
//                .patch("https://api.zeplin.dev/v1/projects/" + PROJECT_ID)
//                .then().log().all()
//                .statusCode(204)
//                .extract().response();
//    }

    //    public static Response authorize() {
//        return RestAssured
//                .given().log().all()
//                .contentType(ContentType.JSON)
//                .queryParam("response_type", RESPONSE_TYPE)
//                .queryParam("client_id", CLIENT_ID)
//                .queryParam("redirect_uri", REDIRECT_URI)
//                .when()
//                .get("https://api.zeplin.dev/v1/oauth/authorize")
//                .then().log().all()
//                .statusCode(200)
//                .extract().response();
//