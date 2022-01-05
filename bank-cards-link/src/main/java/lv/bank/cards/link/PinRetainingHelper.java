package lv.bank.cards.link;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PinRetainingHelper {

    @Getter
    private final String cardNumber;
    @Getter
    private final String pinBlock;
    @Getter
    private final String pinKeyId;

    public String getData() {
        StringBuilder data = new StringBuilder();
        data.append(StringUtils.rightPad(StringUtils.trimToEmpty(cardNumber), 19, " "));
        data.append(StringUtils.rightPad(StringUtils.trimToEmpty(pinBlock), 32, " "));
        data.append(StringUtils.rightPad(StringUtils.trimToEmpty(pinKeyId), 3, " "));
        return data.toString();
    }

    public void checkData() throws DataIntegrityException {
        List<String> missing = new ArrayList<>();
        if (StringUtils.isBlank(cardNumber)) {
            missing.add("card number");
        }
        if (StringUtils.isBlank(pinBlock)) {
            missing.add("PIN block");
        }
        if (StringUtils.isBlank(pinKeyId)) {
            missing.add("PIN Key ID");
        }
        if (missing.size() > 0 && missing.size() < 3) {
            StringBuilder missingFields = new StringBuilder();
            for (String value : missing) {
                if (missingFields.length() > 0)
                    missingFields.append(", ");
                missingFields.append(value);
            }
            throw new DataIntegrityException("Missing value for PIN retaining of migrated card: " + missingFields);
        }
        if (StringUtils.isNotBlank(cardNumber) && !cardNumber.matches("^\\d{16}$")) {
            throw new DataIntegrityException("Incorrect migrated card number: " + cardNumber);
        }
        if (StringUtils.isNotBlank(pinBlock) && !(pinBlock.length() >= 16 && pinBlock.length() <= 32)) {
            throw new DataIntegrityException("Incorrect size of migrated card PIN block: " + pinBlock);
        }
        if (StringUtils.isNotBlank(pinKeyId) && pinKeyId.length() > 3) {
            throw new DataIntegrityException("Incorrect migrated card PIN Key ID: " + pinKeyId);
        }
    }
}
