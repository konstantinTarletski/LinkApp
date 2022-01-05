package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pcd_pp_cards")
public class PcdPpCard implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private static final Map<String, String> BIN_MAPPING;

    static {
        Map<String, String> binMappingInit = new HashMap<>();
        binMappingInit.put("4999628", "7101411114");
        binMappingInit.put("4999629", "7201411280");
        binMappingInit.put("492176", "7201411280");
        binMappingInit.put("489994", "7101411160");
        binMappingInit.put("408883", "7101412160");
        BIN_MAPPING = Collections.unmodifiableMap(binMappingInit);
    }

    @Id
    @GeneratedValue(generator = "ppCardSeq")
    @SequenceGenerator(name = "ppCardSeq", sequenceName = "PCD_PRIORITY_PASS", allocationSize = 1)
    @Column(name = "card_number", precision = 22, scale = 0)
    private Integer cardNumber;

    @ManyToOne
    @JoinColumn(name = "card")
    private PcdCard pcdCard;

    @Column(name = "status", precision = 22, scale = 0)
    private BigDecimal status;

    @Column(name = "operator", length = 30)
    private String operator;

    @Column(name = "coment", length = 100)
    private String comment;

    @Column(name = "email_status", precision = 22, scale = 0)
    private BigDecimal emailStatus;

    @Column(name = "ctime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    @Column(name = "reg_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    public PcdPpCard(PcdPpCard ppcard) {
        this.pcdCard = ppcard.getPcdCard();
        this.status = BigDecimal.valueOf(2);
        this.emailStatus = BigDecimal.ZERO;
        this.ctime = new Date();
    }

    private String getPriorityPassBIN(String cardNumber) {
        for (Map.Entry<String, String> entry : BIN_MAPPING.entrySet()) {
            if (cardNumber.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static boolean isPriorityPassEligible(String cardNumber) {
        for (Map.Entry<String, String> entry : BIN_MAPPING.entrySet()) {
            if (cardNumber.startsWith(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    public String getFullCardNr() {
        String res = String.valueOf(this.cardNumber);
        int limit = res.length();
        for (int i = 0; i < 7 - limit; i++) {
            res = "0" + res;
        }
        res = getPriorityPassBIN(this.pcdCard.getCard()) + res + getPriorityPassHash(this.pcdCard.getCard());
        return res;
    }

    private String getPriorityPassHash(String cardNrumber) {
        String hash = cardNrumber.substring(6);
        int res = 0, tmp;
        for (int i = 0; i < hash.length(); i++) {
            tmp = Integer.parseInt(String.valueOf(hash.charAt(i)));
            res += reduce(tmp * (i + 1));
            res = reduce(res);
        }
        return String.valueOf(res);
    }

    private int reduce(int what) {
        int res = what;
        while (res > 9) {
            res = res / 10 + res % 10;
        }
        return res;
    }

    public boolean checkHash(String num) {
        return getPriorityPassHash(this.pcdCard.getCard()).equals(num);
    }

}
