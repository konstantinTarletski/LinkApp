package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IzdTrtypeNameId implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "tran_type", length = 3, nullable = false)
    private String tranType;

    @Column(name = "tran_lang", length = 1, nullable = false)
    private String tranLang;

    @Column(name = "proc_code", length = 2, nullable = false)
    private String procCode;

}
