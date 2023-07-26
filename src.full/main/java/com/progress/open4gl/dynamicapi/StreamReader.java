// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.IOException;
import java.io.InputStream;

public class StreamReader
{
    static final int END_OF_COLUMN_STATE = 1;
    static final int COLUMN_READING_STATE = 2;
    static final int END_OF_STREAM_STATE = 3;
    static final int END_OF_ROW_STATE = 4;
    private InputStream stream;
    private int tag;
    private int colLength;
    private int leftToRead;
    private int state;
    private boolean gotSchemaHeader;
    private int m_marshalLevel;
    
    public StreamReader(final InputStream stream) {
        this.stream = stream;
        this.state = 1;
        this.gotSchemaHeader = false;
        this.m_marshalLevel = 0;
    }
    
    public StreamReader(final InputStream stream, final int n) {
        this.stream = stream;
        this.state = 1;
        this.gotSchemaHeader = false;
        this.m_marshalLevel = 0;
    }
    
    void close() throws ClientException {
        try {
            this.stream.close();
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
    
    public boolean hasSchemaHeader() {
        return this.gotSchemaHeader;
    }
    
    private int getNextItem() throws ClientException {
        if (this.state == 3 || this.state == 4) {
            return -1;
        }
        try {
            this.tag = this.stream.read();
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
        switch (this.tag) {
            case 82: {
                try {
                    this.gotSchemaHeader = true;
                    this.stream.read();
                    this.stream.read();
                    this.tag = this.stream.read();
                    if (this.tag == 1) {
                        this.stream.read();
                        this.m_marshalLevel = this.stream.read();
                    }
                }
                catch (IOException ex2) {
                    ExceptionConverter.convertIOException(ex2);
                }
                this.state = 3;
                return this.tag;
            }
            case 1: {
                try {
                    this.stream.read();
                    this.stream.read();
                }
                catch (IOException ex3) {
                    ExceptionConverter.convertIOException(ex3);
                }
            }
            case -1: {
                this.state = 3;
                return this.tag;
            }
            case 2:
            case 16:
            case 17: {
                this.state = 4;
                return this.tag;
            }
            case 14: {
                this.state = 4;
                return this.tag;
            }
            case 3:
            case 4:
            case 6:
            case 11: {
                this.state = 1;
                this.colLength = 0;
                this.leftToRead = 0;
                return this.tag;
            }
            case 15: {
                try {
                    this.colLength = Util.extractLong((byte)this.stream.read(), (byte)this.stream.read(), (byte)this.stream.read(), (byte)this.stream.read());
                    this.stream.read();
                    this.stream.read();
                }
                catch (IOException ex4) {
                    ExceptionConverter.convertIOException(ex4);
                }
                this.leftToRead = 0;
                this.state = 2;
                return this.tag;
            }
            case 18:
            case 20: {
                try {
                    this.colLength = Util.extractLong((byte)this.stream.read(), (byte)this.stream.read(), (byte)this.stream.read(), (byte)this.stream.read());
                    this.stream.read();
                    this.stream.read();
                    for (int i = 0; i < 12; ++i) {
                        final byte b = (byte)this.stream.read();
                    }
                    final byte[] array = new byte[20];
                    for (int j = 0; j < 20; ++j) {
                        array[j] = (byte)this.stream.read();
                    }
                    String s = null;
                    if (array[0] != 0) {
                        int n;
                        for (n = 0; array[n] != 0; ++n) {}
                        final byte[] bytes = new byte[n];
                        for (int k = 0; k < n; ++k) {
                            bytes[k] = array[k];
                        }
                        s = new String(bytes, "UTF8");
                    }
                    if (s == null) {
                        s = "Unknown";
                    }
                    if (!s.equalsIgnoreCase("UTF-8")) {
                        throw new ClientException(2, 7665970990714728445L, new Object[] { s });
                    }
                }
                catch (IOException ex5) {
                    ExceptionConverter.convertIOException(ex5);
                }
                this.leftToRead = 0;
                this.state = 2;
                return this.tag;
            }
            case 19: {
                try {
                    this.colLength = Util.extractLong((byte)this.stream.read(), (byte)this.stream.read(), (byte)this.stream.read(), (byte)this.stream.read());
                    this.stream.read();
                    this.stream.read();
                    final byte b2 = (byte)this.stream.read();
                    final byte[] array2 = new byte[20];
                    for (int l = 0; l < 20; ++l) {
                        array2[l] = (byte)this.stream.read();
                    }
                    String s2 = null;
                    if (array2[0] != 0) {
                        int n2;
                        for (n2 = 0; array2[n2] != 0; ++n2) {}
                        final byte[] bytes2 = new byte[n2];
                        for (int n3 = 0; n3 < n2; ++n3) {
                            bytes2[n3] = array2[n3];
                        }
                        s2 = new String(bytes2, "UTF8");
                    }
                    if (s2 != null && !s2.equalsIgnoreCase("UTF-8")) {
                        throw new ClientException(2, 7665970990714728445L, new Object[] { s2 });
                    }
                }
                catch (IOException ex6) {
                    ExceptionConverter.convertIOException(ex6);
                }
                this.leftToRead = this.colLength;
                this.state = 2;
                return this.tag;
            }
            case 5: {
                try {
                    this.colLength = Util.extractShort((byte)this.stream.read(), (byte)this.stream.read());
                }
                catch (IOException ex7) {
                    ExceptionConverter.convertIOException(ex7);
                }
                this.leftToRead = this.colLength;
                this.state = 2;
                return this.tag;
            }
            default: {
                throw new ClientException(2, 16L, new Object[] { new Integer(this.tag).toString() });
            }
        }
    }
    
    boolean getNextColumn() throws ClientException {
        this.getNextItem();
        return this.state != 3 && this.state != 4;
    }
    
    void skipColumn() throws ClientException {
        try {
            for (int i = 0; i < this.leftToRead; ++i) {
                this.stream.read();
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
        this.state = 1;
        this.leftToRead = 0;
    }
    
    boolean getNextRow() throws ClientException {
        if (this.state == 4) {
            this.state = 1;
            return true;
        }
        if (this.state == 2) {
            this.skipColumn();
        }
        while (true) {
            final int nextItem = this.getNextItem();
            switch (nextItem) {
                case -1:
                case 1:
                case 14: {
                    return false;
                }
                case 2:
                case 16:
                case 17: {
                    this.state = 1;
                    return true;
                }
                case 3:
                case 4:
                case 6:
                case 11: {
                    continue;
                }
                case 5:
                case 15: {
                    this.skipColumn();
                    continue;
                }
                default: {
                    throw new ClientException(2, 16L, new Object[] { new Integer(nextItem).toString() });
                }
            }
        }
    }
    
    int getColumnLength() {
        return this.colLength;
    }
    
    int getColumnType() {
        return this.tag;
    }
    
    int getMarshalLevel() {
        return this.m_marshalLevel;
    }
    
    int readColumn(final byte[] array, final int n, final int n2) throws ClientException {
        int n3 = 0;
        int i;
        if (n2 <= this.leftToRead) {
            i = n2;
        }
        else {
            i = this.leftToRead;
        }
        try {
            for (n3 = n; i > 0; --i, ++n3) {
                array[n3] = (byte)this.stream.read();
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
        final int n4 = n3 - n;
        this.leftToRead -= n4;
        if (this.leftToRead == 0) {
            this.state = 1;
        }
        return n4;
    }
    
    public void readNextTableHdr() throws ClientException {
        try {
            this.tag = this.stream.read();
            if (this.tag != 1) {
                throw new ClientException(2, 16L, new Object[] { this.tag + ". Expected table tag" });
            }
            this.stream.read();
            this.stream.read();
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
}
