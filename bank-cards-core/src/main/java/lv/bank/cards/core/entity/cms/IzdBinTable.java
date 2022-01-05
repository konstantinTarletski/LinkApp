package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_bin_table")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdBinTable.to.lv.bank.cards.core.entity.linkApp.PcdBin",
        query = "select b from IzdBinTable b where b.ctime between :lastWaterMark and :currentWaterMark")
public class IzdBinTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdBinTablePK comp_id;

    @Column(name = "card_name", length = 30, nullable = false)
    private String cardName;

    @Column(name = "service_code", length = 3)
    private String serviceCode;

    @Column(name = "risk_level", length = 5)
    private String riskLevel;

    @Column(name = "o_seq_name1", length = 15, nullable = false)
    private String OSeqName1;

    @Column(name = "o_seq_name2", length = 15)
    private String OSeqName2;

    @Column(name = "val0")
    private Long val0;

    @Column(name = "val1")
    private Long val1;

    @Column(name = "val2")
    private Long val2;

    @Column(name = "val3")
    private Long val3;

    @Column(name = "val4")
    private Long val4;

    @Column(name = "val5")
    private Long val5;

    @Column(name = "val6")
    private Long val6;

    @Column(name = "val7")
    private Long val7;

    @Column(name = "val8")
    private Long val8;

    @Column(name = "val9")
    private Long val9;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "o_seq_name_bsc", length = 15)
    private String OSeqNameBsc;

    @Column(name = "emb_name_len", length = 2)
    private Integer embNameLen;

    @Column(name = "dki", length = 3)
    private String dki;

    @Column(name = "kek", length = 10)
    private String kek;

    @Column(name = "mb", length = 1)
    private Integer mb;

    @Column(name = "mb_oper_code", length = 20)
    private String mbOperCode;

    @Column(name = "mb_oper_name", length = 50)
    private String mbOperName;

    @Column(name = "mb_oper_comment", length = 500)
    private String mbOperComment;

    @Column(name = "paysys_code", length = 12)
    private Long paysysCode;

    @Column(name = "ctype_corp", length = 1)
    private Integer ctypeCorp;

    @Column(name = "ctype_deb_cred", length = 1)
    private Integer ctypeDebCred;

    @Column(name = "v_31", length = 2)
    private String v31;

    @Column(name = "branch", length = 3)
    private String branch;

    @Column(name = "bin_group", length = 4)
    private String binGroup;

    @Column(name = "emb_file_prefix", length = 8)
    private String embFilePrefix;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "card_group_code", insertable = false, updatable = false, referencedColumnName = "groupc")})
    private IzdCardGroup izdCardGroup;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "card_num", insertable = false, updatable = false, referencedColumnName = "bin_code"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "card_group_code")})
    private Set<IzdCard> izdCards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "card_num", insertable = false, updatable = false, referencedColumnName = "bin_code"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "card_group_code")})
    private Set<IzdAgreement> izdAgreements;

}
