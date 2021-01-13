package validations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions.*;
import org.testng.Assert;

public class ValidationResponse {
    public static void responseTime_thenOK(Response actualResponse){
        long timeInMS = actualResponse.time();
        long timeInS = actualResponse.timeIn(TimeUnit.SECONDS);
        Assert.assertEquals(timeInS, timeInMS/1000);
    }
    public static Map<String,String> headersValidator(Response response){
        Map<String,String> responseHeaders= new HashMap<>();
        Headers allHeaders = response.headers();
        for(Header headers : allHeaders)
        {
            responseHeaders.put(headers.getName(),headers.getValue());
        }
        return responseHeaders;
    }
}
