package lv.bank.cards.auth.RTPS;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static java.nio.charset.Charset.defaultCharset;

/**
 * Implements an RTPSChannel able to exchange messages with
 * RTPS POS-ISO-host over a TCP link, modified from BASE24TCPChannel
 * <p>
 * by Aleksey Shibanov (ays@one.lv) .<br>
 *
 * @author ays@one.lv
 */

public class PosISOChannel extends BaseChannel {

    int tTimerA = 7000;  // repeat every 3 sec.
    int tTimerC = 45000;  // repeat every 3 sec.

    static final byte STX = 02;     /* Start of text                */
    static final byte ETX = 03;    /* End of text                  */

    static final byte EOT = 04;     /* End of transmission          */
    static final byte ACK = 06;     /* Affirmative Acknowledgment   */

    static final byte NAK = 21;     /* Negative Acknowledgment      */
    static final byte DLE = 16;     /* Disconnect Line              */

    static final int MAX_NAK = 3;     /* Maximum NAK Count            */
    /* Timer C > Timer A * MAX NAKs */
    private int nakCount = 0;
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PosISOChannel.class.getName());

    /**
     * Public constructor (used by Class.forName("...").newInstance())
     */
    public PosISOChannel() {
        super();
    }

    /**
     * Construct client ISOChannel
     *
     * @param host server TCP Address
     * @param port server port number
     * @param p    an ISOPackager
     * @see ISOPackager
     */
    public PosISOChannel(String host, int port, ISOPackager p) {
        super(host, port, p);
    }

    /**
     * Construct server ISOChannel
     *
     * @param p an ISOPackager
     * @throws IOException
     * @see ISOPackager
     */
    public PosISOChannel(ISOPackager p) throws IOException {
        super(p);
    }

    /**
     * constructs a server ISOChannel associated with a Server Socket
     *
     * @param p            an ISOPackager
     * @param serverSocket where to accept a connection
     * @throws IOException
     * @see ISOPackager
     */
    public PosISOChannel(ISOPackager p, ServerSocket serverSocket)
            throws IOException {
        super(p, serverSocket);
    }

    /**
     * Waits and receive an ISOMsg over the TCP/IP session
     *
     * @return the Message received
     * @throws IOException
     * @throws ISOException
     */
    public ISOMsg receive() throws IOException, ISOException {
        log.debug("RTPSChannel.receive()");
        byte[] b = null, header = null;
        LogEvent evt = new LogEvent(this, "receive");
        ISOMsg m = null;
        try {
            if (!isConnected())
                throw new ISOException("unconnected ISOChannel");


            /**
             * <STX><MTI><bitmap><Packedfields><ETX><crc16>
             *
             * Thus, we should read till the ETX, then read 2 more bytes
             **/

            boolean gotSTX = false;
            this.setTimeout(tTimerC);
            int bPtr = 0;                // work pointer
            int bInitialSize = 50; //chunk size for allocation b
            byte[] tmpB = {0}; // Array for reading message by bytes
            nakCount = 0;
            while (true) {
                /* Preparations to read message
                 * First we should receive STX
                 */
                if (!gotSTX) {
                    bPtr = 0;
                    b = new byte[]{0}; // buffer for message bytes
                    try {
                        synchronized (serverIn) {
                            b[0] = serverIn.readByte();
                        }
                    } catch (InterruptedIOException e) {
//						 endOfSession();
                        throw e;
                    }

                    if (b[0] != STX) {
                        throw new ISOException("Awaited for STX, got " + Arrays.toString(b));
                    } else gotSTX = true;
                }
                /*
                 * If we're here, we'we got STX
                 */

                try {
                    synchronized (serverIn) {
                        int bytesRead = serverIn.read(tmpB);
                    }
                } catch (InterruptedIOException e) {
                    endOfSession();
                    throw e;
                }

                if (bPtr == b.length) b = ByteBuffer.allocate(b.length + bInitialSize).put(b).array();
                b[bPtr++] = tmpB[0];

                /*
                 * If this is the end of transmission, we should calculate and check CRC
                 */
                if (tmpB[0] == ETX) {
                    int CRC = RTPSCRC16.calculate(ByteBuffer.allocate(bPtr).put(b, 0, bPtr).array());


                    Integer iTmp1 = CRC & 0xFF;
                    Integer iTmp2 = (CRC >> 8) & 0xFF;
                    byte[] tmpB2 = {0};

                    int bytesReadTmpB = serverIn.read(tmpB);
                    int bytesReadTmpB2 = serverIn.read(tmpB2);

                    if ((iTmp1.byteValue() != tmpB[0]) || (iTmp2.byteValue() != tmpB2[0])) {
                        if (++nakCount >= MAX_NAK) {
                            throw new ISOException("Max NAK count during receiving message");
                        }
                        gotSTX = false;
                        synchronized (serverOut) {
                            sendNAK();
                        }

                        continue;
                    }
                    /* In result array, we need to eliminate ETX */
                    --bPtr;

                    b = ByteBuffer.allocate(bPtr).put(b, 0, bPtr).array();
                    m = new ISOMsg();
                    m.setSource(this);
                    m.setPackager(getDynamicPackager(b));
                    m.setHeader(getDynamicHeader(header));
                    if (b.length > 0) m.unpack(b);
                    synchronized (serverOut) {
                        sendACK();
                    }

                    if (m.getMTI().matches("1810")) {
                        /* This is HOLD message */
                        gotSTX = false;
                        continue;
                    }
                    break;
                }
            }
            m.setDirection(ISOMsg.INCOMING);
            m = applyIncomingFilters(m, evt);
            m.setDirection(ISOMsg.INCOMING);
            evt.addMessage(m);
            cnt[RX]++;
            setChanged();
            notifyObservers(m);
        } catch (ISOException e) {
            evt.addMessage(e);
            throw e;
        } catch (EOFException e) {
            Socket s = getSocket();
            if (s != null)
                s.close();
            evt.addMessage("<peer-disconnect/>");
            throw e;
        } catch (InterruptedIOException e) {
            Socket socket = getSocket();
            if (socket != null)
                socket.close();
            evt.addMessage("<io-timeout/>");
            throw e;
        } catch (IOException e) {
            Socket socket = getSocket();
            if (socket != null)
                socket.close();
            if (usable)
                evt.addMessage(e);
            throw e;
        } catch (Exception e) {
            evt.addMessage(e);
            throw new ISOException("unexpected exception", e);
        } finally {
            Logger.log(evt);
        }
        return m;
    }

    public void sendACK() throws IOException {
        serverOut.write(new byte[]{ACK});
        serverOut.flush();
    }

    public void sendNAK() throws IOException {
        serverOut.write(new byte[]{NAK});
        serverOut.flush();
    }

    public void endOfSession() {
        if (getSocket() == null) return;
        synchronized (serverOut) {
            try {
                serverOut.write(new byte[]{DLE, EOT});
                serverOut.flush();
            } catch (Exception e) {
            } finally {
                try {
                    disconnect();
                } catch (Exception e1) {
                }
            }
        }
    }


    public void send(ISOMsg m)
            throws IOException, ISOException {
        log.debug("RTPSChannel.send()");
        LogEvent evt = new LogEvent(this, "send");
        evt.addMessage(m);
        Timer timerA = null;
        try {
            if (!isConnected())
                throw new ISOException("unconnected ISOChannel");

            m.setDirection(ISOMsg.OUTGOING);
            m = applyOutgoingFilters(m, evt);
            m.setDirection(ISOMsg.OUTGOING); // filter may have drop this info
            m.setPackager(getDynamicPackager(m));
            byte[] b = m.pack();

            nakCount = 0;
            timerA = null;

            while (true) {
                if (log.isDebugEnabled()) {
                    String charsetName = defaultCharset().name();
                    log.debug("Whilling in PosISOChannel [" + new String(b, charsetName) + "]nakCount=" + nakCount);
                }
                synchronized (serverOut) {
                    sendMessageHeader(m, b.length);
                    //serverOut.write(STX);
                    //sendMessage (b, 0, b.length);
                    serverOut.write(b);
                    sendMessageTrailler(m, b.length);
                    serverOut.flush();
                }
                if (timerA == null) timerA = startTimerA();

                /*TIMER A starts here */
                this.setTimeout(tTimerA);

                /*Waiting for ACK */
                byte[] bb = {0};
                synchronized (serverIn) {

                    getBytes(bb);
                }

                if (bb[0] == NAK) {
                    log.debug("Received NAK");
                    if (nakCount++ >= PosISOChannel.MAX_NAK) {
                        endOfSession();
                        throw new ISOException("Too many NAKs");
                    }
                } else if ((bb[0] == DLE) || (bb[0] == EOT)) {
                    endOfSession();
                    throw new ISOException("RECEIVED " + bb[0]);
                } else if (bb[0] == ACK) {
                    log.debug("Received ACK");
                    break;
                } else {
                    log.warn("Received SOMETHING STRANGE: [" + bb[0] + "] : " + new String(bb, defaultCharset()));
                    throw new ISOException("RECEIVED " + bb[0]);
                }
            }
            setChanged();
            notifyObservers(m);
        } catch (ISOException e) {
            evt.addMessage(e);
            throw e;
        } catch (IOException e) {
            endOfSession();
            evt.addMessage(e);
            throw e;
        } catch (Exception e) {
            evt.addMessage(e);
            throw new ISOException("unexpected exception", e);
        } finally {
            if (timerA != null) {
                timerA.cancel();
                timerA = null;
            }
            Logger.log(evt);
        }
    }

    protected Timer startTimerA() {
        Timer timerA = new Timer();
        timerA.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // Task here ...
                nakCount++;
            }
        }, tTimerA, tTimerA);
        return timerA;
    }

    protected void sendMessageHeader(ISOMsg m, int len) throws IOException {
        log.debug("PosISOChannel.sendMessageHeader()");
        serverOut.writeByte(STX);
        log.debug("PosISOChannel.sendMessageHeader() done");
    }

    /**
     * @param m   the Message to send (in this case it is unused)
     * @param len message len (ignored)
     * @throws IOException
     */
    protected void sendMessageTrailler(ISOMsg m, int len) throws IOException {
        log.debug("RTPSChannel.sendMessageTrailer()");
        int CRC = 0;       // initial contents of LFBSR

        byte[] mbuf = {};
        try {
            mbuf = m.pack();
        } catch (ISOException e) {
            e.printStackTrace();
        }
        ByteBuffer bb;
        bb = java.nio.ByteBuffer.allocate(mbuf.length + 1);
        bb.put(mbuf).put(ETX);

        CRC = RTPSCRC16.calculate(bb.array());
        serverOut.writeByte(ETX);
        serverOut.writeByte(CRC & 0xFF);
        serverOut.writeByte((CRC >> 8) & 0xFF);
    }
}

