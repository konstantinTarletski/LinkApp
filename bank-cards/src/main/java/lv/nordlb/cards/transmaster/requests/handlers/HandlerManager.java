package lv.nordlb.cards.transmaster.requests.handlers;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.handlers.AddCardToStoplist;
import lv.bank.cards.soap.handlers.CardData;
import lv.bank.cards.soap.handlers.CardStatus;
import lv.bank.cards.soap.handlers.CardStatusBO;
import lv.bank.cards.soap.handlers.CardStatusRMS;
import lv.bank.cards.soap.handlers.GetVtsPayloadForApple;
import lv.bank.cards.soap.handlers.GetVtsPayloadForGoogle;
import lv.bank.cards.soap.handlers.Otb;
import lv.bank.cards.soap.handlers.QueryHandler;
import lv.bank.cards.soap.handlers.RereadLinkAppPropertiesHandler;
import lv.bank.cards.soap.handlers.SonicNotificationHandler;
import lv.bank.cards.soap.handlers.WriteLog;
import lv.bank.cards.soap.handlers.lv.CardDataHandoff;
import lv.bank.cards.soap.handlers.lv.CardInfoIB;
import lv.bank.cards.soap.handlers.lv.GetTokenHandler;
import lv.bank.cards.soap.handlers.lv.IssConfig;
import lv.bank.cards.soap.handlers.lv.LinkClientToCif;
import lv.bank.cards.soap.handlers.lv.MakeDormant;
import lv.bank.cards.soap.handlers.lv.RtpsConfig;
import lv.bank.cards.soap.handlers.lv.SetChipTagHandler;
import lv.bank.cards.soap.handlers.lv.UpdateCardDeliveryDetailsHandler;
import lv.bank.cards.soap.handlers.CardInfoAcs;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.requests.SubResponse;
import lv.nordlb.cards.transmaster.requests.handlers.cms.AccountCreditLimit;
import lv.nordlb.cards.transmaster.requests.handlers.cms.ActivateDormantAccountHandler;
import lv.nordlb.cards.transmaster.requests.handlers.cms.AuthBonusHandler;
import lv.nordlb.cards.transmaster.requests.handlers.cms.DeletePreProcPayment;
import lv.nordlb.cards.transmaster.requests.handlers.cms.ExecutePPPayment;
import lv.nordlb.cards.transmaster.requests.handlers.cms.ExecutePayment;
import lv.nordlb.cards.transmaster.requests.handlers.cms.ExecuteTransaction;
import lv.nordlb.cards.transmaster.requests.handlers.cms.GetComissionAccounts;
import lv.nordlb.cards.transmaster.requests.handlers.cms.GetComissionsByCards;
import lv.nordlb.cards.transmaster.requests.handlers.cms.ManageSMSDestination;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.AtmAdvertTestHandler;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.CardInfo;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.CustomerInfo;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.DBConectionTestHandler;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.GetCardAccountBalances;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.GetCardsByPersonalCode;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.InformationChangeHandler;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.MerchantParams;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.OrderPinReminderHandler;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.PPBlock;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.PPCardInfo;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.PPNew;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.PPRenew;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.RetrievePinCodes;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.SetPinDeliveryStatusHandler;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.SlipInfo;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.UnlinkAccountHandler;
import lv.nordlb.cards.transmaster.requests.handlers.rtps.AddCardToRMS;
import lv.nordlb.cards.transmaster.requests.handlers.rtps.Outstandings;
import lv.nordlb.cards.transmaster.requests.handlers.rtps.RemoveCardFromRMS;
import lv.nordlb.cards.transmaster.requests.handlers.rtps.RemoveCardFromStoplist;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HandlerManager {

    public static final Set<String> INFORMATIVE_SERVICE_WITHOUT_ROLLBACK;

    // All possible implementations
    private static final Map<String, Class<? extends SubRequestHandler>> AVAILABLE_HANDLERS;

    static {
        Set<String> informativeServiceWithoutRollback = new HashSet<>();
        Map<String, Class<? extends SubRequestHandler>> availableHandlers = new HashMap<>();

        availableHandlers.put("outstandings", Outstandings.class);
        informativeServiceWithoutRollback.add("outstandings");
        availableHandlers.put("otb", Otb.class);
        informativeServiceWithoutRollback.add("otb");
        availableHandlers.put("card-info", CardInfo.class);
        informativeServiceWithoutRollback.add("card-info");
        availableHandlers.put("card-info-ib", CardInfoIB.class);
        informativeServiceWithoutRollback.add("card-info-ib");
        availableHandlers.put("credit-limit", AccountCreditLimit.class);
        availableHandlers.put("card-status", CardStatus.class);
        informativeServiceWithoutRollback.add("card-status");
        availableHandlers.put("check-cvc-retrieve-pin-codes", RetrievePinCodes.class);
        informativeServiceWithoutRollback.add("check-cvc-retrieve-pin-codes");
        availableHandlers.put("set-pin-delivery-status", SetPinDeliveryStatusHandler.class);
        availableHandlers.put("update-card-delivery-details", UpdateCardDeliveryDetailsHandler.class);
        availableHandlers.put("order-pin-reminder", OrderPinReminderHandler.class);
        availableHandlers.put("card-status-bo", CardStatusBO.class); // NF
        informativeServiceWithoutRollback.add("card-status-bo");
        availableHandlers.put("card-status-rms", CardStatusRMS.class);
        informativeServiceWithoutRollback.add("card-status-rms");
        availableHandlers.put("add-card-to-rms", AddCardToRMS.class);
        availableHandlers.put("remove-card-from-rms", RemoveCardFromRMS.class);
        availableHandlers.put("add-card-to-stoplist", AddCardToStoplist.class);
        availableHandlers.put("remove-card-from-stoplist", RemoveCardFromStoplist.class);
        availableHandlers.put("write-log", WriteLog.class);
        availableHandlers.put("pcdabaNG-query", QueryHandler.class);
        availableHandlers.put("card-data", CardData.class);
        informativeServiceWithoutRollback.add("card-data");
        availableHandlers.put("manage-sms", ManageSMSDestination.class);
        availableHandlers.put("card-data-handoff", CardDataHandoff.class);
        availableHandlers.put("execute-payment", ExecutePayment.class);
        availableHandlers.put("get-cards-by-personal-code", GetCardsByPersonalCode.class);
        informativeServiceWithoutRollback.add("get-cards-by-personal-code");
        availableHandlers.put("get-pp-info", PPCardInfo.class);
        informativeServiceWithoutRollback.add("get-pp-info");
        availableHandlers.put("renew-pp", PPRenew.class);
        availableHandlers.put("block-pp", PPBlock.class);
        availableHandlers.put("new-pp", PPNew.class);
        availableHandlers.put("execute-pp-payment", ExecutePPPayment.class);
        availableHandlers.put("delete-pre-proc-payment", DeletePreProcPayment.class);
        availableHandlers.put("slip-info", SlipInfo.class);
        informativeServiceWithoutRollback.add("slip-info");
        availableHandlers.put("get-comissions", GetComissionsByCards.class);
        informativeServiceWithoutRollback.add("get-comissions");
        availableHandlers.put("get-comission-accounts", GetComissionAccounts.class);
        informativeServiceWithoutRollback.add("get-comission-accounts");
        availableHandlers.put("make-dormant", MakeDormant.class);
        availableHandlers.put("rtps-config", RtpsConfig.class);
        informativeServiceWithoutRollback.add("rtps-config");
        availableHandlers.put("izd-config", IssConfig.class);
        informativeServiceWithoutRollback.add("izd-config");
        availableHandlers.put("disp-merchantpar", MerchantParams.class);
        informativeServiceWithoutRollback.add("disp-merchantpar");
        availableHandlers.put("customer-info", CustomerInfo.class);
        informativeServiceWithoutRollback.add("customer-info");
        availableHandlers.put("atm-avert-test", AtmAdvertTestHandler.class); // Used only for testing. Don't put in production
        availableHandlers.put("reread-link-app-properties", RereadLinkAppPropertiesHandler.class);
        availableHandlers.put("sonic-notification", SonicNotificationHandler.class);
        informativeServiceWithoutRollback.add("sonic-notification");
        availableHandlers.put("information-change", InformationChangeHandler.class);
        availableHandlers.put("unlink-account", UnlinkAccountHandler.class);
        availableHandlers.put("activate-dormant", ActivateDormantAccountHandler.class);
        availableHandlers.put("db-connection-test", DBConectionTestHandler.class);
        informativeServiceWithoutRollback.add("db-connection-test");
        availableHandlers.put("set-chip-tag-value", SetChipTagHandler.class);
        availableHandlers.put("auth-bonus", AuthBonusHandler.class);
        availableHandlers.put("card-info-acs", CardInfoAcs.class);
        informativeServiceWithoutRollback.add("card-info-acs");
        availableHandlers.put("get-card-account-balances", GetCardAccountBalances.class);
        informativeServiceWithoutRollback.add("get-card-account-balances");
        availableHandlers.put("link-client-to-cif", LinkClientToCif.class);
        availableHandlers.put("execute-transaction", ExecuteTransaction.class);
        availableHandlers.put("get-vts-payload-for-google", GetVtsPayloadForGoogle.class);
        availableHandlers.put("get-vts-payload-for-apple", GetVtsPayloadForApple.class);
        availableHandlers.put("get-token", GetTokenHandler.class);

        INFORMATIVE_SERVICE_WITHOUT_ROLLBACK = Collections.unmodifiableSet(informativeServiceWithoutRollback);
        AVAILABLE_HANDLERS = Collections.unmodifiableMap(availableHandlers);
    }

    // Instantiated implementations
    private final Map<String, SubRequestHandler> handlers = new HashMap<>();

    public void prepareHandlerForFunction(String s) throws RequestPreparationException {
        if (!AVAILABLE_HANDLERS.containsKey(s))
            throw new RequestPreparationException("Don't know how to handle function " + s);

        if (!handlers.containsKey(s)) {
            try {
                handlers.put(s, (AVAILABLE_HANDLERS.get(s)).getDeclaredConstructor((Class[]) null).newInstance((Object[]) null));
            } catch (SecurityException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException e) {
                log.error("Can't create handler for function:" + s, e);
                throw new RequestPreparationException("Can't create handler for function:" + s);
            } catch (InstantiationException e) {
                log.error("InstantiationException Can't create handler for function:" + s, e);
                throw new RequestPreparationException("Can't create handler for function:" + s);
            }
        }
    }

    public SubResponse handle(SubRequest req) throws RequestFormatException, RequestProcessingException, RequestPreparationException {
        if (log.isDebugEnabled()) log.debug("handling request " + req.getReq().asXML());
        prepareHandlerForFunction(req.getFunction());
        SubRequestHandler h = handlers.get(req.getFunction());
        h.handle(req);
        return h.compileResponse();
    }

    public void collectGarbage(int seconds) {
        List<String> handlersToRemove = new ArrayList<>();
        for (Map.Entry<String, SubRequestHandler> entry : handlers.entrySet()) {
            if (entry.getValue().notUsedForSeconds(seconds)) {
                handlersToRemove.add(entry.getKey());
            }
        }

        if (handlersToRemove.size() > 0) {
            for (String key : handlersToRemove) {
                log.debug("Removing handler: " + key);
                handlers.remove(key);
            }
        }
    }
}
