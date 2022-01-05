package lv.bank.cards.core.entity.cms;

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
public class NordlbFileIdPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "file_type", length = 10, nullable = false)
    private String fileType;

    @Column(name = "j_day", length = 3, nullable = false)
    private String JDay;

}
