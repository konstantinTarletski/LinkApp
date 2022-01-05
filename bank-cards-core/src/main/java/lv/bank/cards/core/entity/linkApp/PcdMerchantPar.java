package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "pcd_merchant_par")
public class PcdMerchantPar implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "merchant", length = 15)
    private String merchant;

    @Column(name = "n0", precision = 22, scale = 0)
    private BigDecimal n0;

    @Column(name = "n1", precision = 22, scale = 0)
    private BigDecimal n1;

    /**
     * Online merchant flag, 1 - online, 0,(NULL) - offline
     */
    @Column(name = "n2", precision = 22, scale = 0)
    private BigDecimal n2;

    @Column(name = "n3", precision = 22, scale = 0)
    private BigDecimal n3;

    @Column(name = "n4", precision = 22, scale = 0)
    private BigDecimal n4;

    @Column(name = "n5", precision = 22, scale = 0)
    private BigDecimal n5;

    @Column(name = "n6", precision = 22, scale = 0)
    private BigDecimal n6;

    @Column(name = "n7", precision = 22, scale = 0)
    private BigDecimal n7;

    @Column(name = "n8", precision = 22, scale = 0)
    private BigDecimal n8;

    @Column(name = "n9", precision = 22, scale = 0)
    private BigDecimal n9;

    @Column(name = "n10", precision = 22, scale = 0)
    private BigDecimal n10;

    @Column(name = "n11", precision = 22, scale = 0)
    private BigDecimal n11;

    @Column(name = "n12", precision = 22, scale = 0)
    private BigDecimal n12;

    @Column(name = "n13", precision = 22, scale = 0)
    private BigDecimal n13;

    @Column(name = "n14", precision = 22, scale = 0)
    private BigDecimal n14;

    @Column(name = "n15", precision = 22, scale = 0)
    private BigDecimal n15;

    /**
     * E-mail
     */
    @Column(name = "chr0", length = 256)
    private String chr0;

    /**
     * Add info for payment file
     */
    @Column(name = "chr1", length = 256)
    private String chr1;

    @Column(name = "chr2", length = 256)
    private String chr2;

    @Column(name = "chr3", length = 256)
    private String chr3;

    @Column(name = "chr4", length = 256)
    private String chr4;

    @Column(name = "chr5", length = 256)
    private String chr5;

    @Column(name = "chr6", length = 256)
    private String chr6;

    @Column(name = "chr7", length = 256)
    private String chr7;

    @Column(name = "chr8", length = 256)
    private String chr8;

    @Column(name = "chr9", length = 256)
    private String chr9;

    @Column(name = "chr10", length = 256)
    private String chr10;

    @Column(name = "chr11", length = 256)
    private String chr11;

    @Column(name = "chr12", length = 256)
    private String chr12;

    @Column(name = "chr13", length = 256)
    private String chr13;

    @Column(name = "chr14", length = 256)
    private String chr14;

    @Column(name = "chr15", length = 256)
    private String chr15;

}
