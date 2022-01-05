/*
 * $Id: ProductDAO.java 1 2006-08-09 13:17:00Z just $
 * Created on 2005.24.2
 */
package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.cms.dto.IzdOfferedProductDTO;
import lv.bank.cards.core.entity.cms.IzdOfferedProduct;

import java.util.List;

public interface ProductDAO {

    List<IzdOfferedProduct> findAvailableProducts(IzdOfferedProductDTO mask);

}
