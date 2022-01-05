package lv.bank.cards.soap.api.rtps.soap.servise;

import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.CashInDNBNordeaQueryBodyLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.TypeT;
import org.apache.commons.lang.StringUtils;

public interface SetCashInBodyValue {

    String TYPE_VALUE = "4";

    CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value);

    class SetType implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (TYPE_VALUE.equals(StringUtils.substring(value, 1, 2)))
                body.setTYPE(TypeT.R);
            else
                body.setTYPE(TypeT.T);
            return body;
        }
    }

    class SetFLD_002 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_002");
            if (!StringUtils.isNumeric(value))
                throw new RuntimeException("Wrong format for FLD_002");
            body.setFLD002(CardUtils.maskCardNumber(value));
            return body;
        }
    }

    class SetFLD_004 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                return body;
            Long amount = null;
            try {
                amount = Long.parseLong(StringUtils.substringBefore(value, "."));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Wrong amount format " + value);
            }
            body.setFLD004(amount);
            return body;
        }
    }

    class SetFLD_004_and_006 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                return body;
            Long amount = null;
            try {
                amount = Long.parseLong(StringUtils.substringBefore(value, "."));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Wrong amount format " + value);
            }
            body.setFLD004(amount);
            body.setFLD006(amount);
            return body;
        }
    }

    class SetFLD_006 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                return body;
            Long amount = null;
            try {
                amount = Long.parseLong(StringUtils.substringBefore(value, "."));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Wrong amount format " + value);
            }
            body.setFLD006(amount);
            return body;
        }
    }

    class SetFLD_007 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_007");
            body.setFLD007(value);
            return body;
        }
    }

    class SetFLD_011 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_011");
            body.setFLD011(value);
            return body;
        }
    }

    class SetFLD_037 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_037");
            body.setFLD037(value);
            return body;
        }
    }

    class SetFLD_038 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_038");
            body.setFLD038(value);
            return body;
        }
    }

    class SetFLD_041 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_041");
            body.setFLD041(value);
            return body;
        }
    }

    class SetFLD_043 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_043");
            body.setFLD043(value);
            return body;
        }
    }

    class SetFLD_049 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_049");
            if (!StringUtils.isNumeric(value))
                throw new RuntimeException("Wrong format for FLD_049");
            PcdCurrency currency = cardsDAO.getCurrencyByIsoNum(value);
            if (currency == null)
                throw new RuntimeException("Cannot find currency " + value);
            body.setFLD049(currency.getIsoAlpha());
            return body;
        }
    }

    class SetFLD_051 implements SetCashInBodyValue {

        @Override
        public CashInDNBNordeaQueryBodyLVT setValue(CardsDAO cardsDAO, CashInDNBNordeaQueryBodyLVT body, String value) {
            if (StringUtils.isBlank(value))
                throw new RuntimeException("Missing value for FLD_051");
            if (!StringUtils.isNumeric(value))
                throw new RuntimeException("Wrong format for FLD_051");
            PcdCurrency currency = cardsDAO.getCurrencyByIsoNum(value);
            if (currency == null)
                throw new RuntimeException("Cannot find currency " + value);
            body.setFLD051(currency.getIsoAlpha());
            return body;
        }
    }

}
