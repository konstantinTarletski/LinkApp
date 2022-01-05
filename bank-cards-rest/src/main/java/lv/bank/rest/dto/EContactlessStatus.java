package lv.bank.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum EContactlessStatus {

    DISABLED("disabled", 0),
    ENABLED("enabled", 1),
    PENDING_ACTIVATION("pendingActivation", 2),
    PENDING_BLCOKING("pendingBlocking", 3),
    PROCESS_ACTIVATION("processActivation", 4),
    PROCESS_BLCOKING("processBlocking", 5);

    private static final Map<String, EContactlessStatus> values = new HashMap<>(values().length);
    private static final Map<Integer, EContactlessStatus> indexValues = new HashMap<>(values().length);

    static {
        Arrays.stream(values()).forEach(v -> {
            values.put(v.getValue(), v);
            indexValues.put(v.getIndex(), v);
        });
    }

    private final String value;
    private final int index;

    @JsonValue
    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static EContactlessStatus fromValue(String value) {
        return values.get(value);
    }

    public static EContactlessStatus fromIndex(Integer index) {
        return indexValues.get(index);
    }
}
