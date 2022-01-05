package lv.bank.cards.auth.RTPS;

import org.jpos.iso.IFA_AMOUNT;
import org.jpos.iso.IFA_BINARY;
import org.jpos.iso.IFA_BITMAP;
import org.jpos.iso.IFA_LCHAR;
import org.jpos.iso.IFA_LLCHAR;
import org.jpos.iso.IFA_LLLBINARY;
import org.jpos.iso.IFA_LLLCHAR;
import org.jpos.iso.IFA_LLLNUM;
import org.jpos.iso.IFA_LLNUM;
import org.jpos.iso.IFA_NUMERIC;
import org.jpos.iso.IF_CHAR;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOFieldPackager;

/**
 * ISO 8583 RTPSauth Packager
 *
 * @author ays@one.lv
 * @version $Id: ISO87APackager.java,v 1.5 2003/05/16 04:15:15 alwyns Exp $
 * @see ISOPackager
 * @see ISOBasePackager
 * @see ISOComponent
 */
public class PosISOPackager extends ISOBasePackager {
    protected ISOFieldPackager fld[] = {
            /* 0  */ new IFA_NUMERIC(4, "Message Type Identifier (MTI)"),
            /*001*/ new IFA_BITMAP(16, "Bit Map/Extended"),
            /*002*/ new IFA_LLNUM(19, "Primary Account Number"),
            /*003*/ new IFA_NUMERIC(6, "Processing Code"),
            /*004*/ new IFA_NUMERIC(12, "Amount, Transaction"),
            /*005*/ new IFA_NUMERIC(12, "Amount, Reconciliation"),
            /*006*/ new IFA_NUMERIC(12, "Amount, Cardholder Billing"),
            /*007*/ new IFA_NUMERIC(10, "Date and Time, Transmission"),
            /*008*/ new IFA_NUMERIC(8, "Amount, Cardholder Billing Fee"),
            /*009*/ new IFA_NUMERIC(8, "Conversion Rate, Reconciliation"),
            /*010*/ new IFA_NUMERIC(8, "Conversion Rate, Cardholder Billing"),
            /*011*/ new IFA_NUMERIC(6, "Systems Trace Audit Number (STAN)"),
            /*012*/ new IFA_NUMERIC(12, "Date and Time, Local Transaction"),
            /*013*/ new IFA_NUMERIC(4, "Date, Effective"),
            /*014*/ new IFA_NUMERIC(4, "Date, Expiration"),
            /*015*/ new IFA_NUMERIC(4, "Date, Settlement"),
            /*016*/ new IFA_LLNUM(14, "Data Capture datetime (ITS internal)"),
            /*017*/ new IFA_LLNUM(99, "Action Byte (ITS internal)"),
            /*018*/ new IFA_LLNUM(99, "Message (ITS internal)"),
            /*019*/ new IFA_LLNUM(99, "Answer format (ITS internal)"),
            /*020*/ new IFA_NUMERIC(3, "Country Code, Primary Account Number"),
            /*021*/ new IFA_NUMERIC(3, "Country Code, Forwarding Institution"),
            /*022*/ new IF_CHAR(12, "Point of Service Data Code"),
            /*023*/ new IFA_NUMERIC(3, "Card Sequence Number"),
            /*024*/ new IFA_NUMERIC(3, "Function Code"),
            /*025*/ new IFA_NUMERIC(4, "Message Reason Code"),
            /*026*/ new IFA_NUMERIC(4, "Card Acceptor Business Code"),
            /*027*/ new IFA_NUMERIC(1, "Approval Code Length"),
            /*028*/ new IFA_NUMERIC(6, "Date, Reconciliation"),
            /*029*/ new IFA_NUMERIC(3, "Reconciliation Indicator"),
            /*030*/ new IFA_AMOUNT(24, "Amounts, Original"),
            /*031*/ new IFA_LLNUM(99, "Acquirer Reference Data"),
            /*032*/ new IFA_LLNUM(11, "Acquirer Institution ID Code"),
            /*033*/ new IFA_LLNUM(11, "Forwarding Institution ID Code"),
            /*034*/ new IFA_LLNUM(28, "Primary Account Number, Extended"),
            /*035*/ new IFA_LLLCHAR(37, "Track 2 Data"),
            /*036*/ new IFA_LLLCHAR(104, "Track 3 Data"),
            /*037*/ new IF_CHAR(12, "Retrieval Reference Number"),
            /*038*/ new IF_CHAR(6, "Approval Code"),
            /*039*/ new IFA_NUMERIC(3, "Action Code"),
            /*040*/ new IFA_NUMERIC(3, "Service Code"),
            /*041*/ new IF_CHAR(8, "Card Acceptor Terminal ID"),
            /*042*/ new IF_CHAR(15, "Card Acceptor ID Code"),
            /*043*/ new IFA_LLCHAR(99, "Card Acceptor Name/Location"),
            /*044*/ new IFA_LLCHAR(99, "Additional Response Data"),
            /*045*/ new IFA_LLCHAR(76, "Track 1 Data"),
            /*046*/ new IFA_LLLCHAR(204, "Amounts, Fees"),
            /*047*/ new IFA_LLLCHAR(999, "Additional Data - National"),
            /*048*/ new IFA_LLLCHAR(999, "Additional Data - Private"),
            /*049*/ new IF_CHAR(3, "Currency Code, Transaction"),
            /*050*/ new IF_CHAR(3, "Currency Code, Reconciliation"),
            /*051*/ new IF_CHAR(3, "Currency Code, Cardholder Billing"),
            /*052*/ new IFA_BINARY(8, "Personal ID (PIN) Data"),
            /*053*/ new IFA_LLNUM(16, "Security Related Control Information"),
            /*054*/ new IFA_LLLCHAR(120, "Amounts, Additional"),
            /*055*/ new IFA_LLLCHAR(510, "Integrated Circuit Card System Related Data"),
            /*056*/ new IFA_LLCHAR(35, "Original Data Elements"),
            /*057*/ new IF_CHAR(3, "Authorization Life Cycle Code"),
            /*058*/ new IFA_LLCHAR(11, "Authorizing Agent Institution ID Code"),
            /*059*/ new IFA_LLLCHAR(999, "Transport Data"),
            /*060*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*061*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*062*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*063*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*064*/ new IFA_BINARY(16, "Message Authentication Code Field"),
            /*065*/ new IFA_BINARY(16, "Reserved for ISO"),
            /*066*/ new IFA_LLLNUM(204, "Amounts, Original Fees"),
            /*067*/ new IFA_NUMERIC(2, "Extended Payment Data"),
            /*068*/ new IFA_NUMERIC(3, "Country Code, Receiving Institution"),
            /*069*/ new IFA_NUMERIC(3, "Country Code, Settlement Institution"),
            /*070*/ new IFA_NUMERIC(3, "Country Code, Authorizing Agent Institution"),
            /*071*/ new IFA_NUMERIC(8, "Message Number"),
            /*072*/ new IFA_LLLCHAR(999, "Data, Record"),
            /*073*/ new IFA_LCHAR(6, "Date, Action"),
            /*074*/ new IFA_NUMERIC(10, "Credits, Number"),
            /*075*/ new IFA_NUMERIC(10, "Credits, Reversal Number"),
            /*076*/ new IFA_NUMERIC(10, "Debits, Number"),
            /*077*/ new IFA_NUMERIC(10, "Debits, Reversal Number"),
            /*078*/ new IFA_NUMERIC(10, "Transfer, Number"),
            /*079*/ new IFA_NUMERIC(10, "Transfer, Reversal Number"),
            /*080*/ new IFA_NUMERIC(10, "Inquiries, Number"),
            /*081*/ new IFA_NUMERIC(10, "Authorizations, Number"),
            /*082*/ new IFA_NUMERIC(10, "Inquiries, Reversal Number"),
            /*083*/ new IFA_NUMERIC(10, "Payments, Number"),
            /*084*/ new IFA_NUMERIC(10, "Payments, Reversal Number"),
            /*085*/ new IFA_NUMERIC(10, "Fee Collections, Number"),
            /*086*/ new IFA_NUMERIC(16, "Credits, Amount"),
            /*087*/ new IFA_NUMERIC(16, "Credits, Reversal Amount"),
            /*088*/ new IFA_NUMERIC(16, "Debits, Amount"),
            /*089*/ new IFA_NUMERIC(16, "Debits, Reversal Amount"),
            /*090*/ new IFA_NUMERIC(10, "Authorizations, Reversal Number"),
            /*091*/ new IFA_NUMERIC(3, "Country Code, Transaction Destination Institution"),
            /*092*/ new IFA_NUMERIC(3, "Country Code, Transaction Originator Institution"),
            /*093*/ new IFA_LLNUM(11, "Transaction Destination Institution ID Code"),
            /*094*/ new IFA_LLNUM(11, "Transaction Originator Institution ID Code"),
            /*095*/ new IFA_LLLBINARY(999, "Card Issuer Reference Data"),
            /*096*/ new IFA_LLLBINARY(999, "Key Management Data"),
            /*097*/ new IFA_AMOUNT(17, "Amount, Net Reconciliation"),
            /*098*/ new IF_CHAR(25, "Payee"),
            /*099*/ new IFA_LLNUM(11, "Settlement Institution ID Code"),
            /*100*/ new IFA_LLNUM(11, "Receiving Institution ID Code"),
            /*101*/ new IFA_LLCHAR(17, "File Name"),
            /*102*/ new IFA_LLCHAR(28, "Account ID 1"),
            /*103*/ new IFA_LLCHAR(28, "Account ID 2"),
            /*104*/ new IFA_LLLCHAR(100, "Transaction Description"),
            /*105*/ new IFA_AMOUNT(16, "Credits, Chargeback Amount"),
            /*106*/ new IFA_AMOUNT(16, "Debits, Chargeback Amount"),
            /*107*/ new IFA_NUMERIC(10, "Credits, Chargeback Number"),
            /*108*/ new IFA_NUMERIC(10, "Debits, Chargeback Number"),
            /*109*/ new IFA_LLNUM(84, "Credits, Fee Amounts"),
            /*110*/ new IFA_LLNUM(84, "Debits, Fee Amounts"),
            /*111*/ new IFA_LLLCHAR(999, "Reserved for ISO Use"),
            /*112*/ new IFA_LLLCHAR(999, "Reserved for ISO Use"),
            /*113*/ new IFA_LLLCHAR(999, "Reserved for ISO Use"),
            /*114*/ new IFA_LLLCHAR(999, "Reserved for ISO Use"),
            /*115*/ new IFA_LLLCHAR(999, "Reserved for ISO Use"),
            /*116*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*117*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*118*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*119*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*120*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*121*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*122*/ new IFA_LLLCHAR(999, "Reserved for National Use"),
            /*123*/ new IFA_LLLNUM(4, "CVC2 Code"),
            /*124*/ new IFA_LLLCHAR(999, "Reserved for Private Use"),
            /*125*/ new IFA_LLLCHAR(999, "Reserved for Private Use"),
            /*126*/ new IFA_LLLCHAR(999, "Reserved for Private Use"),
            /*127*/ new IFA_LLLCHAR(999, "Reserved for Private Use"),
            /*128*/ new IFA_BINARY(16, "Message Authentication Code Field"),
    };

    public PosISOPackager() {
        super();
        setFieldPackager(fld);
    }
}
