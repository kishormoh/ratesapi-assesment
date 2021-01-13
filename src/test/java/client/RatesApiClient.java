package client;

import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RatesApiClient{
        private String baseUrl="https://api.ratesapi.io/api/";
        private String endPoint;
        private Map<String, String> headers = new HashMap<>();

        public RatesApiClient endPoints(String endPoint) {
            this.endPoint = endPoint;
            return this;
        }

        public RatesApiClient withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public ValidatableResponse callGetRatesAPI() {
            String url = baseUrl + endPoint ;
            return given()
                    .headers(headers)
                    .when()
                    .get(url)
                    .then();
        }
    }
