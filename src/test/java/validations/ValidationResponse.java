package validations;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions.*;
import org.testng.Assert;

public class ValidationResponse {
    public static boolean validateResponse(String responsePayloads, float values)throws JsonProcessingException {
        JsonNode payload = new ObjectMapper().readTree(responsePayloads);
        return validatePlaceOrderResponse(values).test(payload);
    }
    private static Predicate<JsonNode> validatePlaceOrderResponse(float values) throws JsonProcessingException {
        return body ->
                body.get("rates").textValue().equals(values);
    }
    public static void responseTime_thenOK(Response actualResponse){
        long timeInMS = actualResponse.time();
        long timeInS = actualResponse.timeIn(TimeUnit.SECONDS);
        System.out.println(timeInMS);
        System.out.println(timeInS);
        Assert.assertEquals(timeInS, timeInMS/1000);
    }
}
