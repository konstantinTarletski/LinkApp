package lv.bank.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum EProduct {

    VISA("visa"), MASTER("master"), MAESTRO("maestro");

    private static final Map<String, EProduct> values = new HashMap<>(values().length);

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
    public static EProduct fromValue(String value) {
        return values.get(value);
    }

}
