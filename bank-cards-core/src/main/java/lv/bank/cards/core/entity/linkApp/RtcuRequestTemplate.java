package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "rtcu_request_templates")
public class RtcuRequestTemplate {

    @Id
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "xslt", length = 4000)
    private String xslt;

}
