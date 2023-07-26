// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsock;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;

public class ComMsg
{
    private String msgName;
    private byte[] data;
    
    public ComMsg(final String s) {
        this(s, null);
    }
    
    public ComMsg(final String msgName, final byte[] data) {
        this.data = data;
        final int length = msgName.length();
        if (length > 4) {
            this.msgName = msgName.substring(0, 4);
        }
        else {
            this.msgName = msgName;
        }
        if (length < 4) {
            for (int i = length; i < 4; ++i) {
                this.msgName += " ";
            }
        }
    }
    
    public ComMsg(final DataInputStream dataInputStream) throws IOException {
        final byte[] array = new byte[8];
        dataInputStream.readFully(array, 0, 4);
        final int int1 = dataInputStream.readInt();
        if (int1 > 0 && int1 < 512) {
            dataInputStream.readFully(this.data = new byte[int1], 0, int1);
        }
        this.msgName = new String(array, 0, 4);
    }
    
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(this.msgName);
        if (this.data != null) {
            dataOutputStream.writeInt(this.data.length);
            dataOutputStream.write(this.data);
        }
        else {
            dataOutputStream.writeInt(0);
        }
        dataOutputStream.flush();
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public String getMsgName() {
        return this.msgName;
    }
    
    public String toString() {
        if (this.data != null) {
            return "Name: " + this.msgName + "; Data length: " + this.data.length;
        }
        return "Name: " + this.msgName + "; Data length: 0";
    }
}
