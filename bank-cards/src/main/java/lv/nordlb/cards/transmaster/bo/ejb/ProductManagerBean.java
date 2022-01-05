package lv.nordlb.cards.transmaster.bo.ejb;

import lombok.Setter;
import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.cms.dto.IzdOfferedProductDTO;
import lv.bank.cards.core.cms.impl.ProductDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdOfferedProduct;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.nordlb.cards.transmaster.bo.interfaces.ProductManager;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ProductManagerBean implements ProductManager {

    @Setter
    protected ProductDAO productDAO;

    public ProductManagerBean() {
        productDAO = new ProductDAOHibernate();
    }

    /**
     * Used for JUnit tests
     */
    protected ProductManagerBean(boolean test) {
    }

    public IzdOfferedProduct findExactlyOneProduct(IzdOfferedProductDTO mask) throws DataIntegrityException {
        List<IzdOfferedProduct> products = productDAO.findAvailableProducts(mask);

        /* now we'll see: if we've found many products it is very bad ! We need only one !*/
        if ((products != null) && (!products.isEmpty())) {
            if (products.size() != 1) {
                throw new DataIntegrityException("There should be only 1 record");
            }
            return products.get(0);
        }
        return null;
    }

}
