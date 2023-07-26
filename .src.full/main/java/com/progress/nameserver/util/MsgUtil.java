// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver.util;

import com.progress.common.exception.ProException;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class MsgUtil
{
    private static final int versionIndex = 0;
    private static final int typeIndex = 2;
    private static final int srcIndex = 3;
    private static final int rqIndex = 4;
    private static final int rqExtIndex = 8;
    private static final int rspIndex = 12;
    private static final int rspExtIndex = 16;
    private static ByteArrayOutputStream dataOut;
    private static DataOutputStream dataStreamOut;
    
    public static void writeHeader(final DataOutputStream dataOutputStream, final short v, final int v2) throws IOException {
        dataOutputStream.writeShort(v);
        dataOutputStream.writeByte(4);
        dataOutputStream.writeByte(2);
        dataOutputStream.writeInt(10);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeShort(102);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeShort(0);
        dataOutputStream.writeShort(1);
        dataOutputStream.writeByte(0);
        dataOutputStream.writeByte(v2);
    }
    
    public static void setRq(final byte[] array, final int v, final int v2) throws IOException {
        MsgUtil.dataOut.reset();
        MsgUtil.dataStreamOut.writeInt(v);
        MsgUtil.dataStreamOut.writeInt(v2);
        final byte[] byteArray = MsgUtil.dataOut.toByteArray();
        System.arraycopy(byteArray, 0, array, 4, byteArray.length);
    }
    
    public static void setRqExt(final byte[] array, final int v) throws IOException {
        MsgUtil.dataOut.reset();
        MsgUtil.dataStreamOut.writeInt(v);
        final byte[] byteArray = MsgUtil.dataOut.toByteArray();
        System.arraycopy(byteArray, 0, array, 8, byteArray.length);
    }
    
    public static Header readHeader(final DataInputStream dataInputStream) throws InvalidMsgVersionException, IOException {
        final short short1 = dataInputStream.readShort();
        final byte byte1 = dataInputStream.readByte();
        final byte byte2 = dataInputStream.readByte();
        final int int1 = dataInputStream.readInt();
        final int int2 = dataInputStream.readInt();
        final int int3 = dataInputStream.readInt();
        final int int4 = dataInputStream.readInt();
        dataInputStream.readShort();
        dataInputStream.readInt();
        dataInputStream.readShort();
        dataInputStream.readShort();
        dataInputStream.readByte();
        final byte byte3 = dataInputStream.readByte();
        if (short1 < 108 || short1 > 109) {
            throw new InvalidMsgVersionException(short1, (short)109);
        }
        return new Header(short1, byte1, byte2, int1, int2, int3, int4, byte3);
    }
    
    static {
        MsgUtil.dataOut = new ByteArrayOutputStream();
        MsgUtil.dataStreamOut = new DataOutputStream(MsgUtil.dataOut);
    }
    
    public static class InvalidMsgVersionException extends ProException
    {
        private short gotVer;
        private short expectedVer;
        
        public InvalidMsgVersionException(final short n, final short n2) {
            super(7665970990714724670L, new Object[] { new Integer(n), new Integer(n2) });
            this.gotVer = 0;
            this.expectedVer = 0;
            this.gotVer = n;
            this.expectedVer = n2;
        }
        
        public short gotVersion() {
            return this.gotVer;
        }
        
        public short expectedVersion() {
            return this.expectedVer;
        }
    }
    
    public static class Header
    {
        private short version;
        private byte type;
        private byte src;
        private int rq;
        private int rqExt;
        private int rsp;
        private int rspExt;
        private byte msgCode;
        
        Header(final short version, final byte type, final byte src, final int rq, final int rqExt, final int rsp, final int rspExt, final byte msgCode) {
            this.version = version;
            this.type = type;
            this.src = src;
            this.rq = rq;
            this.rqExt = rqExt;
            this.rsp = rsp;
            this.rspExt = rspExt;
            this.msgCode = msgCode;
        }
        
        public int getRq() {
            return this.rq;
        }
        
        public int getRqExt() {
            return this.rqExt;
        }
        
        public byte getMsgCode() {
            return this.msgCode;
        }
        
        public short getVersion() {
            return this.version;
        }
    }
}
