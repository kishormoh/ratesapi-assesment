package curdOperations;

import client.RatesApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.lessThan;
import validations.ValidationResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class GetResponse {
    private RatesApiClient ratesApiClient=new RatesApiClient();

    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "APPLICATION_JSON");
        return headers;
    }

    @Test
    public void test1() {

        Response validatableResponse=
                ratesApiClient.endPoints("latest").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

//        System.out.println(validatableResponse.extract().body().asString());
//        validatableResponse.assertThat().statusCode(HttpStatus.SC_OK);
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    @Test
    public void test2() {
        Response validatableResponse=
                ratesApiClient.endPoints("2020-10-12").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();;

        //System.out.println(validatableResponse.extract().body().asString());
       // validatableResponse.assertThat().statusCode(HttpStatus.SC_OK);

        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);


    }
    @Test
    public void test3() {
        Response validatableResponse=
                ratesApiClient.endPoints("2019-2-30").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();


        ValidationResponse.responseTime_thenOK(validatableResponse);
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);

    }

    @Test
    public void test4() throws JsonProcessingException {
        float value= (float) 36.641;
        Response validatableResponse=
                ratesApiClient.endPoints("latest").withHeaders(getRequestHeaders()).callGetRatesAPI().extract().response();

        assertThat(ValidationResponse.validateResponse(validatableResponse.asString(),value));

    }


}
