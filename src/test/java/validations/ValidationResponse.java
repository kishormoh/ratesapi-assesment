package validations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;
public class ValidationResponse {

    public static void responseTime_thenOK(Response actualResponse) {
        long timeInMS = actualResponse.time();
        long timeInS = actualResponse.timeIn(TimeUnit.SECONDS);
        assertThat(timeInS==timeInMS);
    }

    public static Map<String, String> headersValidator(Response response) {
        Map<String, String> responseHeaders = new HashMap<>();
        Headers allHeaders = response.headers();
        for (Header headers : allHeaders) {
            responseHeaders.put(headers.getName(), headers.getValue());
        }
        return responseHeaders;
    }
}
