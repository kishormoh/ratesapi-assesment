package com.ratesapi.assesment.validations;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class Assertions {
    private static JsonPath jsonPath;
    private static String baseValueFromResponse;
    private static Map<String, Float> extractRates;
    private static String date;

    public static void validateRates(Response validatableResponse) {
        jsonPath = validatableResponse.jsonPath();
        extractRates = jsonPath.get("rates");
        assertThat(extractRates).isNotNull();
    }

    public static void validateBase(Response validatableResponse, String baseName) {
        jsonPath = validatableResponse.jsonPath();
        baseValueFromResponse = jsonPath.get("base");
        assertThat(baseValueFromResponse).isNotNull();
        if (baseName.equals("") || baseName.equals(RatesValue.EUR.toString())) {
            assertThat(baseValueFromResponse.equals(RatesValue.EUR.toString())).isTrue();
        } else {
            assertThat(baseValueFromResponse.equals(baseName)).isTrue();
        }
    }

    public static void validateSymbol(Response validatableResponse, String symbol1) {
        jsonPath = validatableResponse.jsonPath();
        extractRates = jsonPath.get("rates");
        assertThat(extractRates).isNotNull();
        Iterator<Map.Entry<String, Float>> itr = extractRates.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Float> entry = itr.next();
            assertThat(entry.getKey().contains(symbol1));
        }
    }

    public static void validateResponseCode(Response validatableResponse) {
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    public static void validateResponseTime(Response actualResponse) {
        long timeInMS = actualResponse.time();
        long timeInS = actualResponse.timeIn(TimeUnit.SECONDS);
        assertThat(timeInS == timeInMS);
    }

    public static void validateDateInResponse(Response validatableResponse) {
        jsonPath = validatableResponse.jsonPath();
        date = jsonPath.get("date");
        assertThat(date).isNotNull();
    }
}
