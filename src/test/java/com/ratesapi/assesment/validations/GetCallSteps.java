package com.ratesapi.assesment.validations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class GetCallSteps {

    public static Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "APPLICATION_JSON");
        headers.put("Access-Control-Allow-Methods", "GET");
        return headers;
    }

    String baseUrl;
    @Given("I want to get operation for {string}")
    public void i_want_to_get_operation_for(String url) {
        this.baseUrl=url;
        given().contentType("APPLICATION_JSON");
    }
    @When("endpoint is {string}")
    public void endpoint_is(String string) {
        when().get(String.format(baseUrl+string)).then().statusCode(HttpStatus.SC_OK);

    }

}
