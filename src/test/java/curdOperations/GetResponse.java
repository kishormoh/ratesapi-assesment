package curdOperations;

import client.RatesApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.testng.annotations.Test;
import validations.BaseValue;
import validations.ValidationResponse;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetResponse {
    private RatesApiClient ratesApiClient = new RatesApiClient();
    private Map<String, String> headers = new HashMap<>();
    private JsonPath jsonPath;
    private String baseValueFromResponse;
    private Map<String,Float> extractRates;

    public Map<String, String> getRequestHeaders() {
        headers.put("Content-Type", "APPLICATION_JSON");
        headers.put("Access-Control-Allow-Methods", "GET");
        return headers;
    }

    @Test
    public void test1() {
        Response validatableResponse =
                ratesApiClient.endPoints("latest").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        jsonPath = validatableResponse.jsonPath();
        baseValueFromResponse = jsonPath.get("base");
        extractRates=jsonPath.get("rates");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
        assertThat(baseValueFromResponse.equals(BaseValue.EUR));
        ValidationResponse.responseTime_thenOK(validatableResponse);
        assertThat(extractRates == null).isFalse();
        assertThat(extractRates.size()==32);
    }

    @Test
    public void test2() {
        Response validatableResponse =
                ratesApiClient.endPoints("2020-10-12").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        jsonPath = validatableResponse.jsonPath();
        baseValueFromResponse = jsonPath.get("base");
        extractRates=jsonPath.get("rates");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
        assertThat(baseValueFromResponse.equals(BaseValue.EUR));
        ValidationResponse.responseTime_thenOK(validatableResponse);
        assertThat(extractRates == null).isFalse();
        assertThat(extractRates.size()==32);

    }

    @Test
    public void test3() {
        Response validatableResponse =
                ratesApiClient.endPoints("2019-2-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        jsonPath = validatableResponse.jsonPath();
        baseValueFromResponse = jsonPath.get("error");
        extractRates=jsonPath.get("rates");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        assertThat(getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
        assertThat(baseValueFromResponse.equals(BaseValue.ERRORRESPONSE));
        ValidationResponse.responseTime_thenOK(validatableResponse);
        assertThat(extractRates == null).isTrue();
    }

    @Test
    public void test4() throws JsonProcessingException {
        float value = (float) 36.641;
        Response validatableResponse =
                ratesApiClient.endPoints("latest").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        jsonPath = validatableResponse.jsonPath();
        baseValueFromResponse = jsonPath.get("base");
        extractRates=jsonPath.get("rates");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
        assertThat(baseValueFromResponse.equals(BaseValue.EUR));
        ValidationResponse.responseTime_thenOK(validatableResponse);
        assertThat(extractRates == null).isFalse();
        assertThat(extractRates.size()==32);
    }
}
