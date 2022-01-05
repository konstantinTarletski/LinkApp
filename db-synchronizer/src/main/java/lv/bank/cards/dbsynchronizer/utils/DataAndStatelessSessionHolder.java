package lv.bank.cards.dbsynchronizer.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.StatelessSession;

@Data
@RequiredArgsConstructor
public class DataAndStatelessSessionHolder<T>{

    protected StatelessSession statelessSession;
    protected T data;

}
