package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import lv.bank.cards.core.entity.linkApp.PcdMerchantPar;
import lv.nordlb.cards.pcdabaNG.interfaces.MerchantManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import org.apache.commons.lang.StringUtils;

public class MerchantParams extends SubRequestHandler{
	private MerchantManager cardManager = null;
	public MerchantParams() throws RequestPreparationException {
		super();
		try {
			cardManager = (MerchantManager) new InitialContext().lookup(MerchantManager.JNDI_NAME);
		} catch (NamingException e) {
			throw new RequestPreparationException(e, this);
		}
}
	public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
		super.handle(r);

		String param = getStringFromNode("/do/cif");
		
		if (StringUtils.isBlank(param)) throw new RequestFormatException("Specify cif number", this);
		
		List<PcdMerchantPar> smerhpar = cardManager.GetDisMerchantPar(param);

		for (PcdMerchantPar mrchpar : smerhpar) {
			ResponseElement element = createElement("element");
			element.createElement("Chr1").addText(mrchpar.getChr1());
		}
	}

	}
