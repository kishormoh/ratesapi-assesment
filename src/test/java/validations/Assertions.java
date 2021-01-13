package validations;

import curdOperations.GetResponse;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Assertions {
    private static JsonPath jsonPath;
    private static String baseValueFromResponse;
    private static Map<String, Float> extractRates;

    public static void responseAssertions(Response validatableResponse) {
        jsonPath = validatableResponse.jsonPath();
        baseValueFromResponse = jsonPath.get("base");
        extractRates = jsonPath.get("rates");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(GetResponse.getRequestHeaders().equals(ValidationResponse.headersValidator(validatableResponse)));
        assertThat(baseValueFromResponse.equals(BaseValue.EUR));
        ValidationResponse.responseTime_thenOK(validatableResponse);
        assertThat(extractRates == null).isFalse();
        assertThat(extractRates.size() == 32);
    }
}
