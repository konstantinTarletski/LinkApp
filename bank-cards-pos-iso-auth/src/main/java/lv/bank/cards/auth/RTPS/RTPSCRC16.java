package lv.bank.cards.auth.RTPS;

/*
 * Created on Oct 1, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author ays
 * <p>
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RTPSCRC16 {
    static int calculate(byte[] b) {
        int XORer;
        int Rotor;
        int CRC = 0;

        for (int n = 0; n < b.length; n++) {
            Rotor = b[n];
            for (int i = 0; i < 8; i++) {
                if ((CRC & 0x8000) != 0)
                    XORer = 0x8005;
                else
                    XORer = 0;

                CRC <<= 1;
                CRC = CRC & 0xFFFF;

                if ((Rotor & 0x80) != 0) {
                    CRC++;
                    CRC = CRC & 0xFFFF;
                }
                Rotor <<= 1;

                CRC ^= XORer;
                CRC = CRC & 0xFFFF;
            }
        }

        for (int i = 0; i < 16; i++) {
            if ((CRC & 0x8000) != 0)
                XORer = 0x8005;
            else
                XORer = 0;
            CRC <<= 1;
            CRC = CRC & 0xFFFF;
            CRC ^= XORer;
            CRC = CRC & 0xFFFF;
        }
        return (CRC);
    }

}
