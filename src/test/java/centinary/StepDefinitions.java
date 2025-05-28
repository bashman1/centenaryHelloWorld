package centinary;

import io.cucumber.java.en.*;

//import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.config.SSLConfig;

public class StepDefinitions {

    private String baseUrl;
    private Response response;

    @Given("the API base URL is {string}")
    public void set_base_url(String url) {
        this.baseUrl = url;
    }

//    @When("I send a GET request to {string}")
//    public void send_get_request(String endpoint) {
//        response = given()
//                .baseUri(baseUrl)
//                .header("accept", "*/*")
//                .when()
//                .get(endpoint);
//    }

    @When("I send a GET request to {string}")
    public void send_get_request(String endpoint) {
        response = given()
                .baseUri(baseUrl)
                .config(config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .header("accept", "*/*")
                .when()
                .get(endpoint);
    }

    @When("I send a GET request to {string} without headers")
    public void send_get_request_no_headers(String endpoint) {
        response = given()
                .baseUri(baseUrl)
                .config(config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .when()
                .get(endpoint);
    }

    @Then("the response status code should be {int}")
    public void verify_status_code(int statusCode) {
        assertThat(response.statusCode(), is(statusCode));
    }

    @Then("the response should contain message {string}")
    public void verify_response_message(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        assertThat(actualMessage, is(expectedMessage));
    }

    @Then("the Content-Type should be {string}")
    public void verify_content_type(String expectedType) {
        String contentType = response.header("Content-Type");
        assertThat(contentType.toLowerCase(), containsString(expectedType.toLowerCase()));
    }

    @Then("the response time should be less than {int} ms")
    public void verify_response_time(int maxMillis) {
        assertThat(response.getTime(), lessThan((long) maxMillis));
    }

    @Then("the response should match the expected schema")
    public void validate_json_schema() {
        File schema = new File("src/test/resources/schemas/hello_response_schema.json");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
    }



}
