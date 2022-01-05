package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class TCallAPIResultCollection<E> extends LinkedList<E> {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int p_more;

    public TCallAPIResultCollection(List<E> arg0) {
        super(arg0);
    }

}
