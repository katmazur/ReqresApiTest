package in.reqres.steps;

import in.reqres.UserHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static in.reqres.Constants.baseUrl;
import static in.reqres.Constants.createUserUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserSteps extends UserHelper {
    private RequestSpecification request;
    private static Response response;
    private static String payload;
    private static String userId;

    @Given("I have a random user")
    public void iHaveRandomUser() {
        request = RestAssured.given().header("Content-Type", "application/json");
        payload = createRandomUser();
    }

    @When("I send POST request to '{}' endpoint")
    public void iSendPostRequest(String endpoint) {
        response = request.body(payload).when().post(baseUrl + endpoint);
    }

    @Then("I see status code {}")
    public void iSeeStatusCode(Integer code) {
        response.then().assertThat().statusCode(code);
    }

    @Then("I see user data in response body")
    public void iSeeUserData() {
        String responseNameValue = response.then().extract().path("name");
        assertEquals(responseNameValue, userName);
    }

    @Given("I have created user")
    public void iHaveCreatedUser() {
        request = RestAssured.given().header("Content-Type", "application/json");
        response = request.body(createRandomUser()).when().post(createUserUrl);
        response.then().assertThat().statusCode(201);
        userId = response.then().extract().path("id");


    }
    @When("I update user job {} using PUT request")
    public void iUpdateUser(String newJob){
        //1. create url
        //2. create payload (with updated jov position)
        //3. make put request
        String putUrl = baseUrl + "api/users/" + userId;
        String updatePayLoad = createNewJob(newJob);
        response = request.body(updatePayLoad).when().put(putUrl);
    }
    @Then("I see updated user job {} in response body")
    public void iSeeUpdatedJob (String expectedJob){
        String responseJobValue = response.then().extract().path("job");
        assertEquals(responseJobValue,expectedJob);
    }
    @When ("I delete user using DELETE request")
    public void iDeleteUser(){
        String deleteUrl = baseUrl + "api/users/" + userId;
        response = request.when().delete(deleteUrl);
    }
    @Then ("I see deleted user not found")
    public void iSeeUserDeleted(){
        //1. send get request with deleted id
        //2. check the code response
        String getUrl = baseUrl + "api/users/" + userId;
        response = request.when().get(getUrl);
        response.then().assertThat().statusCode(404);
    }

}
