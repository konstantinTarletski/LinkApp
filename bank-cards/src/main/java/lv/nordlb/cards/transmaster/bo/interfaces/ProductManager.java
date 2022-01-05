package lv.nordlb.cards.transmaster.bo.interfaces;

import lv.bank.cards.core.cms.dto.IzdOfferedProductDTO;
import lv.bank.cards.core.entity.cms.IzdOfferedProduct;
import lv.bank.cards.core.utils.DataIntegrityException;

import javax.ejb.Local;

@Local
public interface ProductManager {

    String COMP_NAME = "java:comp/env/ejb/ProductManager";
    String JNDI_NAME = "java:app/bankCards/ProductManagerBean";

    IzdOfferedProduct findExactlyOneProduct(IzdOfferedProductDTO mask) throws DataIntegrityException;

}
