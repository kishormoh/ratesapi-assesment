package validations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BaseValue {
    EUR("EUR"),
    ERRORRESPONSE("day is out of range for month");

    @Getter
    private String baseName;
}
