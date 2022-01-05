package lv.bank.cards.soap.handlers;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.linkApp.PcdabaNGConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Date;

@Slf4j
public class QueryHandler extends SubRequestHandler {

    protected static SessionFactory sf;

    public QueryHandler() {
        super();
        try {
            Context ctx = new InitialContext();
            sf = (SessionFactory) ctx.lookup(PcdabaNGConstants.HibernateSessionFactory);
        } catch (NamingException e) {
            log.warn("can not create DB session", e);
        }
    }

    @Override
    public void handle(SubRequest request) throws RequestFormatException, RequestProcessingException {
        super.handle(request);
        String query = getStringFromNode("/do/query");
        ResponseElement queryElement = null;
        if ((query == null) || (query.length() < 6 /*minimal possible length of query */))
            throw new RequestFormatException("Specify query tag", this);
        if ((query.substring(0, 6)).equalsIgnoreCase("DELETE") ||
                (query.substring(0, 6)).equalsIgnoreCase("UPDATE") ||
                (query.substring(0, 6)).equalsIgnoreCase("INSERT"))
            throw new RequestFormatException("Restricted tag in query", this);
        Query hQuery;
        try {
            hQuery = sf.getCurrentSession().createQuery(query);
            try {
                if (this.getCalledFunctionName() != null) {
                    queryElement = createElement(this.getCalledFunctionName());
                    String[] aliases = hQuery.getReturnAliases();
                    for (Object o : hQuery.list()) {
                        this.unPackObject(aliases, o, queryElement.createElement("record"), request);
                    }
                } else throw new RequestProcessingException("Error while creating response element", this);
            } catch (RuntimeException e) {
                log.warn("RuntimeException", e);
                throw new RequestProcessingException("Error processing query: " + e.getMessage(), this);
            }
        } catch (HibernateException e) {
            log.warn("HibernateException", e);
            throw new RequestProcessingException("Error in query: " + e.getMessage(), this);
        }
    }

    private void unPackArrayObject(String[] aliases, Object[] tuple, ResponseElement record, SubRequest request) {
        int aliasNr = 0;
        for (Object obj : tuple) {
            log.debug("WORKING WITH NEXT OBJECT:[" + obj + "]");
            log.debug("Next argument to add to response:[" + obj + "]");
            if (obj == null) {
                record.createElement(aliases[aliasNr], "");
            } else if (obj instanceof Date) {
                record.createElement(aliases[aliasNr], request.getDateFormat().format((Date) obj));
            } else {
                record.createElement(aliases[aliasNr], obj.toString());
            }
            aliasNr++;
        }
    }

    private void unPackJavaObject(String[] aliases, Object tuple, ResponseElement record, SubRequest request) {
        log.debug("WORKING WITH NEXT OBJECT:[" + tuple + "]");
        log.debug("Next argument to add to response:[" + tuple + "]");
        if (tuple == null) {
            record.createElement(aliases[0], "");
        } else if (tuple instanceof Date) {
            record.createElement(aliases[0], request.getDateFormat().format((Date) tuple));
        } else {
            record.createElement(aliases[0], tuple.toString());
        }
    }

    private void unPackHibernateObject(Object tuple, ResponseElement record, SubRequest request) throws RequestProcessingException {
        Field[] fields = tuple.getClass().getDeclaredFields();
        for (Field field : fields) {
            log.debug("WORKING WITH NEXT FIELD:[" + field + "]");
            if (!Modifier.isStatic(field.getModifiers())) {
                try {
                    try {
                        Object resObj = tuple.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)
                        ).invoke(tuple, (Object[]) null);
                        log.debug("Next argument to add to response:[" + resObj + "]");
                        if (resObj == null) {
                            record.createElement(field.getName(), "");
                        } else if (field.getName().equals("comp_id")) {
                            this.unPackHibernateObject(resObj, record, request);
                        } else if (resObj instanceof Date) {
                            record.createElement(field.getName(), request.getDateFormat().format((Date) resObj));
                        } else {

                            record.createElement(field.getName(), resObj.toString());
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RequestProcessingException(e, this);
                    }
                } catch (NoSuchMethodException e) {
                    throw new RequestProcessingException(e, this);
                }
            }
        }
    }

    private void unPackObject(String[] aliases, Object tuple, ResponseElement record, SubRequest request)
            throws RequestProcessingException {
        log.debug("unPackHibernateObject[" + tuple.getClass() + "] : isArray=" + tuple.getClass().isArray());
        if (tuple.getClass().isArray()) {
            unPackArrayObject(aliases, (Object[]) tuple, record, request);
        } else if (tuple.getClass().getName().startsWith("java.lang.")) {
            unPackJavaObject(aliases, tuple, record, request);
        } else {
            unPackHibernateObject(tuple, record, request);
        }

    }

    public String getCalledFunctionName() {
        return "pcdabaNG-query";
    }
}
