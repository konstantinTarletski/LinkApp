package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_countries")
public class IzdCountry implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "country", length = 3, nullable = false)
    private String country;

    /**
     * Country code - NUMERIC
     */
    @Column(name = "country_code", length = 3)
    private String countryCode;

    /**
     * Country short code - CHAR 2
     */
    @Column(name = "country_short", length = 2)
    private String countryShort;

    /**
     * Country name
     */
    @Column(name = "country_name", length = 256)
    private String countryName;

    /**
     * User identification number
     */
    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    /**
     * Insert or last update date, time
     */
    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    /**
     * Country group for reports
     */
    @Column(name = "country_group", length = 2)
    private String countryGroup;

    public IzdCountry(String country, String countryCode, String usrid, Date ctime) {
        this.country = country;
        this.countryCode = countryCode;
        this.usrid = usrid;
        this.ctime = (ctime == null) ? null : new Date(ctime.getTime());
    }

}
