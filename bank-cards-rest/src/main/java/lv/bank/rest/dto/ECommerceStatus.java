package lv.bank.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum ECommerceStatus {

    ALLOWED("allowed"), NOT_ALLOWED("notAllowed");

    private static final Map<String, ECommerceStatus> values = new HashMap<>(values().length);

    static {
        Arrays.stream(values()).forEach(v -> values.put(v.getValue(), v));
    }

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static ECommerceStatus fromValue(String value) {
        return values.get(value);
    }
}
