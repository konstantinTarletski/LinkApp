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
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_in_file")
public class IzdInFile implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "infile_num", nullable = false, precision = 20, scale = 0)
    private BigInteger infileNum;

    @Column(name = "in_file", length = 256, nullable = false)
    private String inFile;

    @Column(name = "resiev_dat", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date resievDat;

    @Column(name = "ctrl", length = 14)
    private String ctrl;

    @Column(name = "proc_id", nullable = false, precision = 14, scale = 0)
    private Long procId;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "last_line", nullable = false, precision = 8, scale = 0)
    private Integer lastLine;

    @Column(name = "proc_rez", length = 1)
    private String procRez;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

}
