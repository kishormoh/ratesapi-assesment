package validations;

import curdOperations.GetResponseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Assertions {
    private static JsonPath jsonPath;
    private static String baseValueFromResponse;
    private static Map<String, Float> extractRates;

    public static void responseAssertions(Response validatableResponse,String baseName) {
        jsonPath = validatableResponse.jsonPath();
        baseValueFromResponse = jsonPath.get("base");
        extractRates = jsonPath.get("rates");
        assertThat(validatableResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        if(baseName.equals("")){
            assertThat(baseValueFromResponse.equals(BaseValue.EUR.toString())).isTrue();
        }else {
            assertThat(baseValueFromResponse.equals(baseName)).isTrue();
        }
        assertThat(extractRates == null).isFalse();
        assertThat(extractRates.size() != 0 ).isTrue();
        ValidationResponse.responseTime_thenOK(validatableResponse);
    }
}
