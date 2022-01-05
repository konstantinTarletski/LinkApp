package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PcdClientPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "client", length = 8, nullable = false)
    private String client;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

}
