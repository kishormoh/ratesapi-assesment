package curdOperations;

import client.RatesApiClient;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import validations.Assertions;
import validations.BaseValue;
import validations.ValidationResponse;
import org.junit.jupiter.params.provider.Arguments;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

public class GetResponseTest {

    private RatesApiClient ratesApiClient = new RatesApiClient();

    public static Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "APPLICATION_JSON");
        headers.put("Access-Control-Allow-Methods", "GET");
        return headers;
    }

    private static Stream<Arguments> baseCurrencyValues() {
        return Stream.of(
                Arguments.of(BaseValue.USD.toString()),
                Arguments.of(BaseValue.EUR.toString())
        );
    }

    @ParameterizedTest (name = "BaseValue Type: {0}")
    @MethodSource("baseCurrencyValues")
    @DisplayName("Get response for latest and validating responses")
   public void test1(String baseName){

        Response validatableResponse =
                ratesApiClient.endPoints("latest?base="+baseName).withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        Assertions.responseAssertions(validatableResponse,baseName);
        System.out.println(validatableResponse.body().asString());

    }
    @Test
    @DisplayName("Get response for future date and validate against present date and other responses")
    public void test2() {
        Response validatableResponse =
                ratesApiClient.endPoints("2021-3-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        Assertions.responseAssertions(validatableResponse,"");
    }
    @Test
    @DisplayName("Get response for Back date and validate against responses")
    public void test4(){
        float value = (float) 36.641;
        Response validatableResponse =
                ratesApiClient.endPoints("2019-3-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        Assertions.responseAssertions(validatableResponse,"");
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
}
