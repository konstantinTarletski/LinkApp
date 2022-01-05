package lv.bank.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum ECardType {

    CREDIT("credit"), DEBIT("debit"), BUSINESS_CREDIT("business credit"), BUSINESS_DEBIT("business debit");

    private static final Map<String, ECardType> values = new HashMap<>(values().length);

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
    public static ECardType fromValue(String value) {
        return values.get(value);
    }
}
