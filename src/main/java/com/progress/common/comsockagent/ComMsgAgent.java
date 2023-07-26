// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsockagent;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import com.progress.common.crypto.Cipher4;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class ComMsgAgent
{
    private int msgType;
    private String msgName;
    private int msgCode;
    private int msgSessionID;
    private int msgStatus;
    private int msgSequence;
    private byte[] msgMAC;
    private byte[] cryptKey;
    private int msgLen;
    private byte[] msgHdr;
    private byte[] encryptedData;
    private byte[] data;
    private static int MSGNAME_LEN;
    private static int MSGMAC_LEN;
    private static int CRYPTKEY_LEN;
    private static int HDR_LEN;
    private static int MSGNAME_OFFSET;
    private static int MAC_OFFSET;
    private static int LEN_OFFSET;
    public static int AMPROTO;
    public static int AMDBPROTO;
    private static int AMWSPROTO;
    private static int AMASPROTO;
    public static int AMDBPROTOV2;
    public static int CONN_REQ;
    public static int CONN_ACK;
    public static int MAX_MSG_SIZE;
    
    public ComMsgAgent(final String s) {
        this(0, s, null);
    }
    
    public ComMsgAgent(final int n) {
        this(n, null, null);
    }
    
    public ComMsgAgent(final String s, final byte[] array) {
        this(0, s, array);
    }
    
    public ComMsgAgent(final int n, final byte[] array) {
        this(n, null, array);
    }
    
    public ComMsgAgent(final int n, final String s, final byte[] array) {
        try {
            this.createHeaderInfo(n, s, array);
            final ByteArrayOutputStream out = new ByteArrayOutputStream(ComMsgAgent.HDR_LEN);
            final DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeInt(this.msgType);
            dataOutputStream.writeInt(this.msgCode);
            dataOutputStream.writeBytes(this.msgName.substring(0, ComMsgAgent.MSGNAME_LEN));
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.write(this.msgMAC, 0, ComMsgAgent.MSGMAC_LEN);
            dataOutputStream.writeInt(this.msgLen);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            out.flush();
            this.msgHdr = out.toByteArray();
            if (this.msgHdr.length != this.msgLen - this.getMsgDataLen()) {
                System.out.println("Error ComMsgAgent Constructor: length error: dataLen=" + this.getMsgDataLen() + " msgHdrLen=" + this.msgHdr.length);
            }
            if (this.data != null) {
                final int msgDataLen = this.getMsgDataLen();
                this.encryptedData = new byte[msgDataLen];
                new Cipher4(this.cryptKey).encrypt(array, 0, this.encryptedData, 0, msgDataLen);
            }
        }
        catch (Throwable obj) {
            System.out.println("Error in ComMsgAgent Constructor: " + obj);
        }
    }
    
    public ComMsgAgent(final DataInputStream dataInputStream) throws IOException {
        final byte[] bytes = new byte[ComMsgAgent.HDR_LEN];
        dataInputStream.readFully(bytes, 0, ComMsgAgent.HDR_LEN);
        this.getHeaderInfo(new DataInputStream(new ByteArrayInputStream(bytes)));
        final int msgDataLen = this.getMsgDataLen();
        if (msgDataLen > 0) {
            if (msgDataLen > ComMsgAgent.MAX_MSG_SIZE - ComMsgAgent.HDR_LEN) {
                throw new IOException("Message too large: Maximum allowed=" + ComMsgAgent.MAX_MSG_SIZE + " Message=" + (msgDataLen + ComMsgAgent.HDR_LEN) + ".");
            }
            this.encryptedData = new byte[msgDataLen];
            this.data = new byte[msgDataLen];
            dataInputStream.readFully(this.encryptedData, 0, msgDataLen);
            new Cipher4(this.cryptKey).decrypt(this.encryptedData, 0, this.data, 0, msgDataLen);
            try {
                final MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.reset();
                byte[] digesta;
                if (this.data == null) {
                    digesta = instance.digest();
                }
                else {
                    digesta = instance.digest(this.data);
                }
                if (!MessageDigest.isEqual(digesta, this.getMsgMAC())) {
                    final StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < digesta.length; ++i) {
                        if ((0xFF & digesta[i]) < 16) {
                            sb.append("0" + Integer.toHexString(0xFF & digesta[i]));
                        }
                        else {
                            sb.append(Integer.toHexString(0xFF & digesta[i]));
                        }
                    }
                    final String string = sb.toString();
                    final StringBuffer sb2 = new StringBuffer();
                    for (int j = 0; j < this.msgMAC.length; ++j) {
                        if ((0xFF & this.msgMAC[j]) < 16) {
                            sb2.append("0" + Integer.toHexString(0xFF & this.msgMAC[j]));
                        }
                        else {
                            sb2.append(Integer.toHexString(0xFF & this.msgMAC[j]));
                        }
                    }
                    throw new IOException("Corrupt data: DataMAC=" + string + " MSGMAC=" + sb2.toString());
                }
            }
            catch (Throwable x) {
                System.out.println(x);
                throw new IOException("Unable to create ComMsgAgent message: + exp");
            }
        }
        this.msgName = new String(bytes, ComMsgAgent.MSGNAME_OFFSET, ComMsgAgent.MSGNAME_LEN);
    }
    
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(this.msgHdr);
        if (this.encryptedData != null) {
            dataOutputStream.write(this.encryptedData);
        }
        dataOutputStream.flush();
    }
    
    public boolean processAMMesg(final DataInputStream dataInputStream, final DataOutputStream dataOutputStream) throws IOException {
        if (this.getMsgCode() == ComMsgAgent.CONN_REQ) {
            new ComMsgAgent(ComMsgAgent.CONN_ACK, null).write(dataOutputStream);
        }
        dataOutputStream.flush();
        return true;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public int getMsgType() {
        return this.msgType;
    }
    
    public int getMsgCode() {
        return this.msgCode;
    }
    
    public String getMsgName() {
        return this.msgName;
    }
    
    public int getMsgSessionID() {
        return this.msgSessionID;
    }
    
    public int getMsgSequence() {
        return this.msgSequence;
    }
    
    public byte[] getMsgMAC() {
        return this.msgMAC;
    }
    
    public int getMsgLen() {
        return this.msgLen;
    }
    
    public int getMsgDataLen() {
        return this.msgLen - ComMsgAgent.HDR_LEN;
    }
    
    private void getHeaderInfo(final DataInputStream dataInputStream) throws IOException {
        this.msgMAC = new byte[ComMsgAgent.MSGMAC_LEN];
        this.cryptKey = new byte[ComMsgAgent.CRYPTKEY_LEN];
        final byte[] array = new byte[ComMsgAgent.MSGNAME_LEN];
        this.msgType = dataInputStream.readInt();
        this.msgCode = dataInputStream.readInt();
        dataInputStream.readFully(array, 0, ComMsgAgent.MSGNAME_LEN);
        this.msgSessionID = dataInputStream.readInt();
        this.msgStatus = dataInputStream.readInt();
        this.msgSequence = dataInputStream.readInt();
        dataInputStream.readFully(this.msgMAC);
        this.msgLen = dataInputStream.readInt();
        this.msgName = new String(array);
        for (int i = 0; i < ComMsgAgent.CRYPTKEY_LEN; ++i) {
            this.cryptKey[i] = this.msgMAC[i];
        }
    }
    
    private void createHeaderInfo(final int msgCode, final String msgName, final byte[] data) throws IOException {
        this.msgMAC = new byte[ComMsgAgent.MSGNAME_LEN];
        this.cryptKey = new byte[ComMsgAgent.CRYPTKEY_LEN];
        this.data = data;
        if (msgCode == ComMsgAgent.CONN_REQ) {
            this.msgType = ComMsgAgent.AMPROTO;
        }
        if (msgName != null) {
            final int length = msgName.length();
            if (length > ComMsgAgent.MSGNAME_LEN) {
                this.msgName = msgName.substring(0, ComMsgAgent.MSGNAME_LEN);
            }
            else {
                this.msgName = msgName;
            }
            if (length < ComMsgAgent.MSGNAME_LEN) {
                for (int i = length; i < ComMsgAgent.MSGNAME_LEN; ++i) {
                    this.msgName += " ";
                }
            }
        }
        else {
            for (int j = 0; j < ComMsgAgent.MSGNAME_LEN; ++j) {
                this.msgName += " ";
            }
        }
        this.msgType = ComMsgAgent.AMPROTO;
        this.msgCode = msgCode;
        this.msgSessionID = 0;
        this.msgStatus = 0;
        this.msgSequence = 0;
        this.msgMAC = this.createMD5(data);
        for (int k = 0; k < ComMsgAgent.CRYPTKEY_LEN; ++k) {
            this.cryptKey[k] = this.msgMAC[k];
        }
        if (data != null && data.length > 0) {
            this.msgLen = ComMsgAgent.HDR_LEN + data.length;
        }
        else {
            this.msgLen = ComMsgAgent.HDR_LEN;
        }
        if (this.msgLen > ComMsgAgent.MAX_MSG_SIZE) {
            throw new IOException("Message too large: Maximum allowed=" + ComMsgAgent.MAX_MSG_SIZE + " Message=" + this.msgLen + ".");
        }
    }
    
    private byte[] createMD5(final byte[] input) {
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            if (input == null) {
                return instance.digest();
            }
            return instance.digest(input);
        }
        catch (NoSuchAlgorithmException obj) {
            System.out.println("Error in ComMsgAgent Constructor: " + obj);
            return null;
        }
    }
    
    public String toString() {
        if (this.data != null) {
            return "Name: " + this.msgName + "; Data length: " + this.data.length;
        }
        return "Name: " + this.msgName + "; Data length: 0";
    }
    
    static {
        ComMsgAgent.MSGNAME_LEN = 16;
        ComMsgAgent.MSGMAC_LEN = 16;
        ComMsgAgent.CRYPTKEY_LEN = 6;
        ComMsgAgent.HDR_LEN = 68;
        ComMsgAgent.MSGNAME_OFFSET = 8;
        ComMsgAgent.MAC_OFFSET = 36;
        ComMsgAgent.LEN_OFFSET = 52;
        ComMsgAgent.AMPROTO = 1;
        ComMsgAgent.AMDBPROTO = 2;
        ComMsgAgent.AMWSPROTO = 3;
        ComMsgAgent.AMASPROTO = 4;
        ComMsgAgent.AMDBPROTOV2 = 5;
        ComMsgAgent.CONN_REQ = 1;
        ComMsgAgent.CONN_ACK = 2;
        ComMsgAgent.MAX_MSG_SIZE = 32767 + ComMsgAgent.HDR_LEN;
    }
}
