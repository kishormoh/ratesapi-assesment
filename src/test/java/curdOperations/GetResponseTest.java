package curdOperations;

import client.RatesApiClient;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import validations.Assertions;
import validations.RatesValue;
import validations.ValidationResponse;
import org.junit.jupiter.params.provider.Arguments;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

public class GetResponseTest {

    private static String errorResponse = "day is out of range for month";
    private RatesApiClient ratesApiClient = new RatesApiClient();
    private static String errorResponseForSymbols="Symbols 'EUR,BRL' are invalid for date 2021-01-13.";

    public static Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "APPLICATION_JSON");
        headers.put("Access-Control-Allow-Methods", "GET");
        return headers;
    }

    private static Stream<Arguments> baseCurrencyValues() {
        return Stream.of(
                Arguments.of(RatesValue.USD.toString()),
                Arguments.of(RatesValue.HKD.toString())
        );
    }

    private static Stream<Arguments> symbolAndBaseValues() {
        return Stream.of(
                Arguments.of(RatesValue.THB.toString(), RatesValue.GBP.toString()),
                Arguments.of(RatesValue.USD.toString(), RatesValue.GBP.toString())
        );
    }

    @ParameterizedTest(name = "BaseValue Type: {0}")
    @MethodSource("baseCurrencyValues")
    @DisplayName("Get response for latest and validating responses with base")
    public void test1(String baseName) {

        Response validatableResponse =
                ratesApiClient.endPoints("latest?base=" + baseName).withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        Assertions.validateRates(validatableResponse);
        Assertions.validateBase(validatableResponse, baseName);
        Assertions.validateDateInResponse(validatableResponse);
        Assertions.validateResponseCode(validatableResponse);
        Assertions.validateResponseTime(validatableResponse);
    }

    @ParameterizedTest(name = "BaseValue Type: {0} and symbolValue: {1}")
    @MethodSource("symbolAndBaseValues")
    @DisplayName("Get response for latest and validating responses with symbols")
    public void test5(String symbolname_1, String symbolname) {
        String urlEndpoint = "latest?symbols={0},{1}";
        urlEndpoint = urlEndpoint.replace("{0}", symbolname_1).replace("{1}", symbolname);

        Response validatableResponse =
                ratesApiClient.endPoints(urlEndpoint).withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        Assertions.validateRates(validatableResponse);
        Assertions.validateSymbol(validatableResponse, symbolname_1);
        Assertions.validateBase(validatableResponse, "");
        Assertions.validateDateInResponse(validatableResponse);
        Assertions.validateResponseCode(validatableResponse);
        Assertions.validateResponseTime(validatableResponse);
    }

    @Test
    @DisplayName("Get response for future date and validate against present date and other responses")
    public void test2() {
        Response validatableResponse =
                ratesApiClient.endPoints("2021-3-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        Assertions.validateRates(validatableResponse);
        Assertions.validateResponseTime(validatableResponse);
        Assertions.validateBase(validatableResponse, "");
        Assertions.validateDateInResponse(validatableResponse);
        Assertions.validateResponseCode(validatableResponse);
    }
    @Test
    @DisplayName("Get response for Back date and validate against responses")
    public void test4() {
        float value = (float) 36.641;
        Response validatableResponse =
                ratesApiClient.endPoints("2019-3-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        Assertions.validateRates(validatableResponse);
        Assertions.validateResponseTime(validatableResponse);
        Assertions.validateBase(validatableResponse, "");
        Assertions.validateDateInResponse(validatableResponse);
        Assertions.validateResponseCode(validatableResponse);
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
        assertThat(baseValueFromResponse.equals(errorResponse));
        Assertions.validateResponseTime(validatableResponse);
        assertThat(extractRates == null).isTrue();
    }
    @DisplayName("Get response for latest and validating responses with symbols and dates")
    @Test
    public void test6() {
        Response validatableResponse =
                ratesApiClient.endPoints("2010-01-12?symbols=EUR,BLR").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        assertThat(getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
    }
}
