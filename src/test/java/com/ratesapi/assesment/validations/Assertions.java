package com.ratesapi.assesment.validations;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class Assertions {



    public static void validateRates(ResponsePojo responsePojo){
        assertThat(responsePojo.getRates()).isNotNull();
    }

    public static void validateBase(ResponsePojo responsePojo,String baseName){
        assertThat(responsePojo).isNotNull();
        if (baseName.equals("") || baseName.equals(RatesValue.EUR.toString())) {
            assertThat(responsePojo.getBase().equals(RatesValue.EUR.toString())).isTrue();
        } else {
            assertThat(responsePojo.getBase().equals(baseName)).isTrue();
        }
    }

    public static void validateSymbol(ResponsePojo responsePojo,String symbol1){
        assertThat(responsePojo.getRates()).isNotNull();
        Iterator<Map.Entry<String, Float>> itr = responsePojo.getRates().entrySet().iterator();
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

    public static void validateDateInResponse(ResponsePojo responsePojo) {
        assertThat(responsePojo.getDates()).isNotNull();
    }
}
