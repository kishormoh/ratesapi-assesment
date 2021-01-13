package curdOperations;

import client.RatesApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.testng.annotations.Test;
import validations.Assertions;
import validations.BaseValue;
import validations.ValidationResponse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

public class GetResponse {

    private RatesApiClient ratesApiClient = new RatesApiClient();

    public static Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "APPLICATION_JSON");
        headers.put("Access-Control-Allow-Methods", "GET");
        return headers;
    }

    @Test
    @DisplayName("Get response for latest and validating responses")
    public void test1() {
        Response validatableResponse =
                ratesApiClient.endPoints("latest").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        Assertions.responseAssertions(validatableResponse);

    }

    @Test
    @DisplayName("Get response for future date and validate against present date and other responses")
    public void test2() {
        Response validatableResponse =
                ratesApiClient.endPoints("2021-3-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        Assertions.responseAssertions(validatableResponse);

    }

    @Test
    @DisplayName("Get response for invalid date LEAP year and validate against Error")
    public void test3() {
        JsonPath jsonPath;
        String baseValueFromResponse;
        Map<String, Float> extractRates;
        Response validatableResponse =
                ratesApiClient.endPoints("2019-2-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        jsonPath = validatableResponse.jsonPath();
        baseValueFromResponse = jsonPath.get("error");
        extractRates = jsonPath.get("rates");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        assertThat(getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
        assertThat(baseValueFromResponse.equals(BaseValue.ERRORRESPONSE));
        ValidationResponse.responseTime_thenOK(validatableResponse);
        assertThat(extractRates == null).isTrue();
    }

    @Test
    @DisplayName("Get response for Back date and validate against responses")
    public void test4() throws JsonProcessingException {
        float value = (float) 36.641;
        Response validatableResponse =
                ratesApiClient.endPoints("2019-3-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        Assertions.responseAssertions(validatableResponse);
    }
}
