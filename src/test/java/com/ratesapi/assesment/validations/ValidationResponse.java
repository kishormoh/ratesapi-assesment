package com.ratesapi.assesment.validations;

import java.util.HashMap;
import java.util.Map;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class ValidationResponse {


    public static Map<String, String> headersValidator(Response response) {
        Map<String, String> responseHeaders = new HashMap<>();
        Headers allHeaders = response.headers();
        for (Header headers : allHeaders) {
            responseHeaders.put(headers.getName(), headers.getValue());
        }

        return responseHeaders;
    }
}
