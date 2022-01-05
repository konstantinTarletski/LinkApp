package lv.bank.cards.core.cms.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum TokenStatus {

    UNDEFINED(Integer.MIN_VALUE, null),
    PENDING(-1, "pending"),
    INACTIVE(0, "inactive"),
    ACTIVE(1, "active"),
    SUSPENDED(2, "suspended"),
    DEACTIVATED(3, "deactivated"),
    EXPIRED(4, "expired");

    @Getter
    private final int status;

    @Getter
    private final String name;

    public static TokenStatus fromStatus(int status) {
        return Arrays.stream(values()).filter(v -> v.getStatus() == (status)).findFirst().orElse(UNDEFINED);
    }

}
