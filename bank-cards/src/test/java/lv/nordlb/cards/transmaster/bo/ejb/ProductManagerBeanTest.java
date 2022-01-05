package lv.nordlb.cards.transmaster.bo.ejb;

import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.cms.dto.IzdOfferedProductDTO;
import lv.bank.cards.core.entity.cms.IzdOfferedProduct;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductManagerBeanTest {
	
	private ProductDAO productDAO = mock(ProductDAO.class);
	
	private ProductManagerBean manager = new ProductManagerBean(true);
	
	@Before
	public void initTest(){
		manager.setProductDAO(productDAO);
	}
	
	@Test
	public void findExactlyOneProduct() throws DataIntegrityException {
		IzdOfferedProductDTO mask = new IzdOfferedProductDTO();
		List<IzdOfferedProduct> products = new ArrayList<IzdOfferedProduct>();
		when(productDAO.findAvailableProducts(mask)).thenReturn(products);
		
		assertNull(manager.findExactlyOneProduct(new IzdOfferedProductDTO()));
		assertNull(manager.findExactlyOneProduct(mask));
		
		IzdOfferedProduct product = new IzdOfferedProduct();
		products.add(product);
		
		assertEquals(product, manager.findExactlyOneProduct(mask));
		
		products.add(new IzdOfferedProduct());
		boolean hadError = false;
		try{
			manager.findExactlyOneProduct(mask);
		} catch(DataIntegrityException e){
			hadError = true;
		}
		assertTrue(hadError);
	}
}
