package com.ratesapi.assesment.currency;

import com.ratesapi.assesment.client.RatesApiClient;
import com.ratesapi.assesment.validations.ResponsePojo;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.ratesapi.assesment.validations.Assertions;
import com.ratesapi.assesment.validations.RatesValue;
import com.ratesapi.assesment.validations.ValidationResponse;
import org.junit.jupiter.params.provider.Arguments;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyValidationTests {
    private static final String errorResponse = "day is out of range for month";
    private static final String errorResponseForSymbols = "Symbols 'EUR,BRL' are invalid for date 2021-01-13.";
    RatesApiClient ratesApiClient = new RatesApiClient();
    ResponsePojo responsePojo;
    JsonPath jsonPath ;

    public static Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "APPLICATION_JSON");
        headers.put("Access-Control-Allow-Methods", "GET");
        return headers;
    }

    private static Stream<Arguments> baseCurrencyValues() {
        return Stream.of(
                Arguments.of(RatesValue.USD.toString()),
                Arguments.of(RatesValue.AUD.toString())
        );
    }

    private static Stream<Arguments> symbolAndBaseValues() {
        return Stream.of(
                Arguments.of(RatesValue.THB.toString(), RatesValue.GBP.toString()),
                Arguments.of(RatesValue.USD.toString(), RatesValue.GBP.toString())
        );
    }

    @DisplayName("Get response for latest rates")
    @Test
    public void validUrlWithEndPointAsLatestValidateResponses() {

        Response validatableResponse =
                ratesApiClient.endPoints("latest").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        jsonPath=validatableResponse.jsonPath();
        responsePojo=new ResponsePojo(jsonPath.get("base"),jsonPath.get("date"),jsonPath.get("rates"));

        Assertions.validateRates(responsePojo);
        Assertions.validateBase(responsePojo, "");
        Assertions.validateDateInResponse(responsePojo);
        Assertions.validateResponseCode(validatableResponse);
        Assertions.validateResponseTime(validatableResponse);
    }

    @ParameterizedTest(name = "BaseValue Type: {0}")
    @MethodSource("baseCurrencyValues")
    @DisplayName("Get response for latest and validating responses with base")
    public void validUrlWithEndPointAsLatestAndBaseValidateResponses(String baseName) {

        Response validatableResponse =
                ratesApiClient.endPoints("latest?base=" + baseName).withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        jsonPath=validatableResponse.jsonPath();
        responsePojo=new ResponsePojo(jsonPath.get("base"),jsonPath.get("date"),jsonPath.get("rates"));

        Assertions.validateRates(responsePojo);
        Assertions.validateBase(responsePojo, baseName);
        Assertions.validateDateInResponse(responsePojo);
        Assertions.validateResponseCode(validatableResponse);
        Assertions.validateResponseTime(validatableResponse);
    }

    @ParameterizedTest(name = "BaseValue Type: {0} and symbolValue: {1}")
    @MethodSource("symbolAndBaseValues")
    @DisplayName("Get response for latest and validating responses with symbols")
    public void validUrlWithEndPointAsSymbolsValidateResponses(String symbolname_1, String symbolname) {
        String urlEndpoint = "latest?symbols={0},{1}";
        urlEndpoint = urlEndpoint.replace("{0}", symbolname_1).replace("{1}", symbolname);

        Response validatableResponse =
                ratesApiClient.endPoints(urlEndpoint).withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        jsonPath=validatableResponse.jsonPath();
        responsePojo=new ResponsePojo(jsonPath.get("base"),jsonPath.get("date"),jsonPath.get("rates"));

        Assertions.validateRates(responsePojo);
        Assertions.validateSymbol(responsePojo, symbolname_1);
        Assertions.validateBase(responsePojo, "");
        Assertions.validateDateInResponse(responsePojo);
        Assertions.validateResponseCode(validatableResponse);
        Assertions.validateResponseTime(validatableResponse);
    }

    @Test
    @DisplayName("Get response for future date and validate against present date and other responses")
    public void getResponseForFutureDateAndValidateThedateAsLatest() {
        Response validatableResponse =
                ratesApiClient.endPoints("2021-3-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        jsonPath=validatableResponse.jsonPath();
        responsePojo=new ResponsePojo(jsonPath.get("base"),jsonPath.get("date"),jsonPath.get("rates"));

        Assertions.validateRates(responsePojo);
        Assertions.validateResponseTime(validatableResponse);
        Assertions.validateBase(responsePojo, "");
        Assertions.validateDateInResponse(responsePojo);
        Assertions.validateResponseCode(validatableResponse);
    }

    @Test
    @DisplayName("Get response for Back date and validate against responses")
    public void getResponseForBackDateAndValidateRate() {
        float value = (float) 36.641;
        Response validatableResponse =
                ratesApiClient.endPoints("2019-3-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        jsonPath=validatableResponse.jsonPath();
        responsePojo=new ResponsePojo(jsonPath.get("base"),jsonPath.get("date"),jsonPath.get("rates"));

        Assertions.validateRates(responsePojo);
        Assertions.validateResponseTime(validatableResponse);
        Assertions.validateBase(responsePojo, "");
        Assertions.validateDateInResponse(responsePojo);
        Assertions.validateResponseCode(validatableResponse);
    }

    @Test
    @DisplayName("Get response for invalid date LEAP year and validate against Error")
    public void validateAgainstLeapYearAndInvalidUrlShouldThrowErrorvalidatingError() {
        Response validatableResponse =
                ratesApiClient.endPoints("2019-2-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        JsonPath jsonPath = validatableResponse.jsonPath();
        String  baseValueFromResponse = jsonPath.get("error");
        Map<String, Float> extractRates = jsonPath.get("rates");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        assertThat(getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
        assertThat(baseValueFromResponse.equals(errorResponse));
        Assertions.validateResponseTime(validatableResponse);
        assertThat(extractRates).isNull();
    }

    @DisplayName("Get response for latest and validating responses with symbols and dates")
    @Test
    public void validateAgainstInvalidSymbols() {
        Response validatableResponse =
                ratesApiClient.endPoints("2010-01-12?symbols=EUR,BRL").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();
        JsonPath jsonPath = validatableResponse.jsonPath();
        String  baseValueFromResponse = jsonPath.get("error");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        assertThat(getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
        assertThat(baseValueFromResponse.equals(errorResponseForSymbols));
    }
}
