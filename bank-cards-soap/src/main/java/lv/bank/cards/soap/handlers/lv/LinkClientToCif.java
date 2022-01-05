package lv.bank.cards.soap.handlers.lv;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.impl.CardDAOHibernate;
import lv.bank.cards.core.cms.impl.ClientDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdClientPK;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateClientXML;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;

@Slf4j
public class LinkClientToCif extends SubRequestHandler {

    protected CardDAO cardDAO;
    protected ClientDAO clientDAO;
    protected CMSCallAPIWrapper wrap;

    public LinkClientToCif() {
        super();
        wrap = new CMSCallAPIWrapper();
        cardDAO = new CardDAOHibernate();
        clientDAO = new ClientDAOHibernate();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String client = getStringFromNode("/do/client");
        String cif = getStringFromNode("/do/cif");

        if (client == null || StringUtils.isBlank(client)) {
            throw new RequestFormatException("Specify client", this);
        }

        if (cif == null || StringUtils.isBlank(cif)) {
            throw new RequestFormatException("Specify CIF", this);
        }

        IzdClientPK izdClientPK = new IzdClientPK();
        izdClientPK.setClient(client);
        izdClientPK.setBankC(LinkAppProperties.getCmsBankCode());
        IzdClient izdClient = (IzdClient) clientDAO.getObject(IzdClient.class, izdClientPK);

        if (izdClient == null) {
            throw new RequestProcessingException("Could not find izdClient with ID: " + client, this);
        }

        UpdateClientXML updateClientXML = wrap.new UpdateClientXML(client, LinkAppProperties.getCmsBankCode());
        updateClientXML.setElement("CLIENT_B", cif);

        UpdateDBWork work = wrap.new UpdateDBWork();
        work.setInputXML(updateClientXML.getDocument());
        String response = cardDAO.doWork(work);
        log.info("handle: doWork response = {}", response);
        if (!"success".equals(response)) {
            throw new RequestProcessingException(StringUtils.substring(
                    StringUtils.substringBetween(response, "<ERROR_DESC>", "</ERROR_DESC>"), 0, 200), this);
        }

        createElement("link-client-to-cif", "done");
    }
}
