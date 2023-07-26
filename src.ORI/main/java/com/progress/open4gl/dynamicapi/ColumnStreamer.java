// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.math.BigDecimal;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.Rowid;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Handle;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Clob;
import com.progress.open4gl.Memptr;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.sql.Timestamp;
import com.progress.open4gl.Parameter;
import java.sql.Date;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.io.OutputStream;

public class ColumnStreamer
{
    private OutputStream stream;
    private int count;
    private byte[] buffer;
    private char[] charBuffer;
    private GregorianCalendar gregorianTmp;
    private GregorianCalendar normalizedInput;
    private boolean serializeDatasetsAsXml;
    static GregorianCalendar GREG_BASE;
    
    ColumnStreamer() {
        this.serializeDatasetsAsXml = false;
        this.buffer = new byte[100];
        this.charBuffer = new char[100];
        this.gregorianTmp = new GregorianCalendar();
        (this.normalizedInput = new GregorianCalendar()).set(10, 0);
        this.normalizedInput.set(11, 0);
        this.normalizedInput.set(9, 0);
        this.normalizedInput.set(12, 0);
        this.normalizedInput.set(13, 0);
        this.normalizedInput.set(14, 0);
    }
    
    ColumnStreamer(final OutputStream stream) {
        this();
        this.stream = stream;
    }
    
    ColumnStreamer(final OutputStream outputStream, final boolean serializeDatasetsAsXml) {
        this(outputStream);
        this.serializeDatasetsAsXml = serializeDatasetsAsXml;
    }
    
    void streamColumn(final OutputStream stream, final Object o, final int n) throws ClientException {
        this.stream = stream;
        this.streamColumn(o, n);
    }
    
    public void streamColumn(final Object o, final int n) throws ClientException {
        this.streamColumn(o, n, new Integer(0));
    }
    
    public void streamColumn(final Object o, final int value, final Integer n) throws ClientException {
        final short n2 = 31744;
        Label_0047: {
            if (o == null) {
                if (value == 36 || value == 37) {
                    if (this.serializeDatasetsAsXml) {
                        break Label_0047;
                    }
                }
                try {
                    this.stream.write(3);
                }
                catch (IOException ex) {
                    ExceptionConverter.convertIOException(ex);
                }
                return;
            }
        }
        switch (value) {
            case 2:
            case 34:
            case 40: {
                int n3 = 0;
                int n4 = 0;
                GregorianCalendar gregorianCalendar;
                if (o instanceof Date) {
                    if (value != 2) {
                        throw new ClientException(3, 7665970990714728442L, new Object[] { "java.sql.Date", Parameter.proToName(value) });
                    }
                    this.gregorianTmp.setTime((java.util.Date)o);
                    gregorianCalendar = this.gregorianTmp;
                }
                else if (o instanceof Timestamp) {
                    if (value != 34) {
                        throw new ClientException(3, 7665970990714728442L, new Object[] { "java.sql.Timestamp", Parameter.proToName(value) });
                    }
                    this.gregorianTmp.setTime((java.util.Date)o);
                    gregorianCalendar = this.gregorianTmp;
                }
                else {
                    gregorianCalendar = (GregorianCalendar)o;
                }
                if (value == 40) {
                    n4 = (gregorianCalendar.get(15) + gregorianCalendar.get(16)) / 60000;
                    final GregorianCalendar gregorianCalendar2 = new GregorianCalendar(new SimpleTimeZone(0, TimeZone.getAvailableIDs(0)[0]));
                    gregorianCalendar2.setTimeInMillis(gregorianCalendar.getTimeInMillis());
                    gregorianCalendar = gregorianCalendar2;
                }
                this.normalizedInput.set(1, gregorianCalendar.get(1));
                this.normalizedInput.set(2, gregorianCalendar.get(2));
                this.normalizedInput.set(5, gregorianCalendar.get(5));
                final int n5 = (int)Util.millisToDays(this.normalizedInput.getTime().getTime(), ColumnStreamer.GREG_BASE.getTime().getTime());
                if (value == 34 || value == 40) {
                    n3 = gregorianCalendar.get(11) * 60 * 60 * 1000 + gregorianCalendar.get(12) * 60 * 1000 + gregorianCalendar.get(13) * 1000 + gregorianCalendar.get(14);
                }
                if (value == 2) {
                    this.count = this.writeInt(n5, this.buffer);
                }
                else if (value == 34) {
                    this.count = this.writeInt(n5, this.buffer);
                    this.count += this.write4ByteInt(n3, this.buffer, this.count);
                }
                else {
                    this.count = this.writeInt(n3, this.buffer);
                    this.count += this.write4ByteInt(n5, this.buffer, this.count);
                    this.count += this.write4ByteInt(n4, this.buffer, this.count);
                }
                this.streamBuffer(this.buffer, this.count);
                break;
            }
            case 4: {
                this.count = this.writeInt((int)o, this.buffer);
                this.streamBuffer(this.buffer, this.count);
                break;
            }
            case 41: {
                this.count = this.writeInt64((long)o, this.buffer);
                this.streamBuffer(this.buffer, this.count);
                break;
            }
            case 11: {
                try {
                    final byte[] array = new byte[4];
                    final byte[] bytes = ((Memptr)o).getBytes();
                    if (bytes.length == 0) {
                        this.stream.write(6);
                    }
                    else {
                        this.stream.write(15);
                        Util.insertLong(array, bytes.length);
                        for (int i = 0; i < 4; ++i) {
                            this.stream.write(array[i]);
                        }
                        this.stream.write(Util.getHighByte(n2));
                        this.stream.write(Util.getLowByte(n2));
                    }
                }
                catch (IOException ex2) {
                    ExceptionConverter.convertIOException(ex2);
                }
                break;
            }
            case 39: {
                this.streamLongString(o, n2, 18);
                break;
            }
            case 19: {
                try {
                    final byte[] array2 = new byte[4];
                    byte[] array3;
                    if (o instanceof Clob) {
                        final Clob clob = (Clob)o;
                        try {
                            array3 = clob.getSubString(1L, (int)clob.length()).getBytes("UTF8");
                        }
                        catch (SQLException ex5) {
                            throw new ClientException(3, 0L, new Object[] { "Error reading java.sql.Clob Object" });
                        }
                    }
                    else {
                        array3 = ((String)o).getBytes("UTF8");
                    }
                    this.stream.write(19);
                    Util.insertLong(array2, array3.length);
                    for (int j = 0; j < 4; ++j) {
                        this.stream.write(array2[j]);
                    }
                    this.stream.write(Util.getHighByte(n2));
                    this.stream.write(Util.getLowByte(n2));
                    this.stream.write(0);
                    final byte[] bytes2 = "UTF-8".getBytes();
                    for (int k = 0; k < bytes2.length; ++k) {
                        this.stream.write(bytes2[k]);
                    }
                    for (int n6 = 20 - bytes2.length, l = 0; l < n6; ++l) {
                        this.stream.write(0);
                    }
                    for (int n7 = 0; n7 < array3.length; ++n7) {
                        this.stream.write(array3[n7]);
                    }
                }
                catch (IOException ex3) {
                    ExceptionConverter.convertIOException(ex3);
                }
                break;
            }
            case 18: {
                try {
                    final byte[] array4 = new byte[4];
                    byte[] bytes3;
                    if (o instanceof Blob) {
                        final Blob blob = (Blob)o;
                        try {
                            bytes3 = blob.getBytes(1L, (int)blob.length());
                        }
                        catch (SQLException ex6) {
                            throw new ClientException(3, 0L, new Object[] { "Error reading java.sql.Blob Object" });
                        }
                    }
                    else {
                        bytes3 = (byte[])o;
                    }
                    this.stream.write(19);
                    Util.insertLong(array4, bytes3.length);
                    for (int n8 = 0; n8 < 4; ++n8) {
                        this.stream.write(array4[n8]);
                    }
                    this.stream.write(Util.getHighByte(n2));
                    this.stream.write(Util.getLowByte(n2));
                    this.stream.write(0);
                    for (int n9 = 0; n9 < 20; ++n9) {
                        this.stream.write(0);
                    }
                    for (int n10 = 0; n10 < bytes3.length; ++n10) {
                        this.stream.write(bytes3[n10]);
                    }
                }
                catch (IOException ex4) {
                    ExceptionConverter.convertIOException(ex4);
                }
                break;
            }
            case 7:
            case 10:
            case 14: {
                long n11;
                if (o instanceof Handle) {
                    n11 = ((Handle)o).getLong();
                }
                else if (o instanceof COMHandle) {
                    n11 = ((COMHandle)o).getLong();
                }
                else {
                    n11 = (long)o;
                }
                this.count = this.writeInt64(n11, this.buffer);
                this.streamBuffer(this.buffer, this.count);
                break;
            }
            case 8:
            case 13: {
                byte[] bytes4;
                if (o instanceof byte[]) {
                    bytes4 = (byte[])o;
                }
                else {
                    bytes4 = ((Rowid)o).getBytes();
                }
                this.streamBuffer(bytes4, bytes4.length);
                break;
            }
            case 1: {
                byte[] bytes5;
                try {
                    bytes5 = ((String)o).getBytes("UTF8");
                }
                catch (Exception ex7) {
                    throw new Open4GLError(66L, null);
                }
                this.streamBuffer(bytes5, bytes5.length);
                break;
            }
            case 15:
            case 17: {
                this.buffer[0] = 3;
                this.streamBuffer(this.buffer, 1);
                break;
            }
            case 36:
            case 37: {
                if (this.serializeDatasetsAsXml) {
                    this.streamLongString(o, n2, 20, n);
                    break;
                }
                this.buffer[0] = 3;
                this.streamBuffer(this.buffer, 1);
                break;
            }
            case 3: {
                if (o) {
                    this.count = 1;
                    this.buffer[0] = 1;
                }
                else {
                    this.count = 0;
                }
                this.streamBuffer(this.buffer, this.count);
                break;
            }
            case 5: {
                this.count = this.writeDecimal((BigDecimal)o, this.buffer);
                this.streamBuffer(this.buffer, this.count);
                break;
            }
            default: {
                throw new Open4GLError(15L, new Object[] { new Integer(value).toString() });
            }
        }
    }
    
    private void streamLongString(final Object o, final short n, final int n2) throws ClientException {
        this.streamLongString(o, n, n2, 0);
    }
    
    private void streamLongString(final Object o, final short n, final int n2, final int n3) throws ClientException {
        final byte[] array = new byte[4];
        int length = 0;
        int length2 = 0;
        try {
            final String s = (String)o;
            if (null != s) {
                length2 = s.length();
                length = s.getBytes("UTF8").length;
            }
            if (length == 0 && n2 != 20) {
                this.stream.write(6);
                return;
            }
            this.stream.write((byte)n2);
            Util.insertLong(array, length);
            for (int i = 0; i < 4; ++i) {
                this.stream.write(array[i]);
            }
            this.stream.write(Util.getHighByte(n));
            this.stream.write(Util.getLowByte(n));
            for (int j = 0; j < 4; ++j) {
                this.stream.write(array[j]);
            }
            Util.insertLong(array, length2);
            for (int k = 0; k < 4; ++k) {
                this.stream.write(array[k]);
            }
            Util.insertLong(array, n3);
            for (int l = 0; l < 4; ++l) {
                this.stream.write(array[l]);
            }
            final byte[] bytes = "UTF-8".getBytes();
            final int length3 = bytes.length;
            for (int n4 = 0; n4 < length3; ++n4) {
                this.stream.write(bytes[n4]);
            }
            for (int n5 = 20 - length3, n6 = 0; n6 < n5; ++n6) {
                this.stream.write(0);
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
    
    private int writeDecimal(final BigDecimal obj, final byte[] array) throws ClientException {
        String string;
        try {
            string = (String)Class.forName("java.math.BigDecimal").getMethod("toPlainString", (Class<?>[])null).invoke(obj, (Object[])null);
        }
        catch (Exception ex) {
            string = obj.toString();
        }
        final int length = string.length();
        if (this.charBuffer.length < length) {
            this.charBuffer = new char[length];
        }
        string.getChars(0, length, this.charBuffer, 0);
        boolean b = true;
        int n = 0;
        int n2 = -1;
        switch (this.charBuffer[0]) {
            case '-': {
                b = false;
            }
            case '+': {
                n = 1;
                break;
            }
        }
        boolean b2 = true;
        for (int i = n; i < length; ++i) {
            if (this.charBuffer[i] == '.') {
                n2 = i;
            }
            else if (this.charBuffer[i] != '0') {
                b2 = false;
            }
        }
        if (b2) {
            return 0;
        }
        int value = length - n;
        if (n2 != -1) {
            --value;
        }
        int n3 = (n2 == -1) ? 0 : (value + n - n2);
        if (n3 > 15) {
            final int n4 = n3 - 15;
            n3 -= n4;
            value -= n4;
        }
        if (n3 > 0) {
            int n5 = n + value;
            while (this.charBuffer[n5--] == '0') {
                --n3;
                --value;
            }
        }
        if (value > 50) {
            throw new ClientException(5, 16L, new Object[] { new Integer(value).toString() });
        }
        final int n6 = (value % 2 + value) / 2 + 1;
        if (this.buffer.length < n6) {
            this.buffer = new byte[n6];
        }
        this.buffer[0] = (byte)(b ? -128 : 0);
        final byte[] buffer = this.buffer;
        final int n7 = 0;
        buffer[n7] |= (byte)((byte)n3 & 0x7F);
        int n8 = 1;
        int n9 = n;
        for (int j = 0; j < value; ++j) {
            if (this.charBuffer[n9] == '.') {
                ++n9;
            }
            this.buffer[n8] = (byte)((byte)(this.charBuffer[n9++] - '0') << 4);
            ++j;
            if (this.charBuffer[n9] == '.') {
                ++n9;
            }
            if (j == value) {
                final byte[] buffer2 = this.buffer;
                final int n10 = n8;
                buffer2[n10] |= 0xF;
                break;
            }
            final int n11 = this.charBuffer[n9++] - '0';
            final byte[] buffer3 = this.buffer;
            final int n12 = n8++;
            buffer3[n12] |= (byte)n11;
        }
        return n6;
    }
    
    private int writeInt(int n, final byte[] array) throws ClientException {
        int n2 = 0;
        if (n == 0) {
            return 0;
        }
        if (n > 0) {
            if (n < 128) {
                array[n2] = (byte)n;
                return 1;
            }
            final byte b = (byte)n;
            n >>= 8;
            if (n < 127) {
                array[n2++] = (byte)n;
                array[n2] = b;
                return 2;
            }
            final byte b2 = (byte)n;
            n >>= 8;
            if (n < 127) {
                array[n2++] = (byte)n;
                array[n2++] = b2;
                array[n2] = b;
                return 3;
            }
            final byte b3 = (byte)n;
            array[n2++] = (byte)(n >> 8);
            array[n2++] = b3;
            array[n2++] = b2;
            array[n2] = b;
            return 4;
        }
        else {
            if (n > -128) {
                array[n2] = (byte)n;
                return 1;
            }
            final byte b4 = (byte)n;
            n >>= 8;
            if (n > -128) {
                array[n2++] = (byte)n;
                array[n2] = b4;
                return 2;
            }
            final byte b5 = (byte)n;
            n >>= 8;
            if (n > -128) {
                array[n2++] = (byte)n;
                array[n2++] = b5;
                array[n2] = b4;
                return 3;
            }
            final byte b6 = (byte)n;
            array[n2++] = (byte)(n >> 8);
            array[n2++] = b6;
            array[n2++] = b5;
            array[n2] = b4;
            return 4;
        }
    }
    
    private int writeInt64(long n, final byte[] array) throws ClientException {
        int n2 = 0;
        if (n == 0L) {
            return 0;
        }
        if (n > 0L) {
            if (n < 128L) {
                array[n2] = (byte)n;
                return 1;
            }
            final byte b = (byte)n;
            n >>= 8;
            if (n < 127L) {
                array[n2++] = (byte)n;
                array[n2] = b;
                return 2;
            }
            final byte b2 = (byte)n;
            n >>= 8;
            if (n < 127L) {
                array[n2++] = (byte)n;
                array[n2++] = b2;
                array[n2] = b;
                return 3;
            }
            final byte b3 = (byte)n;
            n >>= 8;
            if (n < 127L) {
                array[n2++] = (byte)n;
                array[n2++] = b3;
                array[n2++] = b2;
                array[n2] = b;
                return 4;
            }
            final byte b4 = (byte)n;
            n >>= 8;
            if (n < 127L) {
                array[n2++] = (byte)n;
                array[n2++] = b4;
                array[n2++] = b3;
                array[n2++] = b2;
                array[n2] = b;
                return 5;
            }
            final byte b5 = (byte)n;
            n >>= 8;
            if (n < 127L) {
                array[n2++] = (byte)n;
                array[n2++] = b5;
                array[n2++] = b4;
                array[n2++] = b3;
                array[n2++] = b2;
                array[n2] = b;
                return 6;
            }
            final byte b6 = (byte)n;
            n >>= 8;
            if (n < 127L) {
                array[n2++] = (byte)n;
                array[n2++] = b6;
                array[n2++] = b5;
                array[n2++] = b4;
                array[n2++] = b3;
                array[n2++] = b2;
                array[n2] = b;
                return 7;
            }
            final byte b7 = (byte)n;
            array[n2++] = (byte)(n >> 8);
            array[n2++] = b7;
            array[n2++] = b6;
            array[n2++] = b5;
            array[n2++] = b4;
            array[n2++] = b3;
            array[n2++] = b2;
            array[n2] = b;
            return 8;
        }
        else {
            if (n > -128L) {
                array[n2] = (byte)n;
                return 1;
            }
            final byte b8 = (byte)n;
            n >>= 8;
            if (n > -128L) {
                array[n2++] = (byte)n;
                array[n2] = b8;
                return 2;
            }
            final byte b9 = (byte)n;
            n >>= 8;
            if (n > -128L) {
                array[n2++] = (byte)n;
                array[n2++] = b9;
                array[n2] = b8;
                return 3;
            }
            final byte b10 = (byte)n;
            n >>= 8;
            if (n > -128L) {
                array[n2++] = (byte)n;
                array[n2++] = b10;
                array[n2++] = b9;
                array[n2] = b8;
                return 4;
            }
            final byte b11 = (byte)n;
            n >>= 8;
            if (n > -128L) {
                array[n2++] = (byte)n;
                array[n2++] = b11;
                array[n2++] = b10;
                array[n2++] = b9;
                array[n2] = b8;
                return 5;
            }
            final byte b12 = (byte)n;
            n >>= 8;
            if (n > -128L) {
                array[n2++] = (byte)n;
                array[n2++] = b12;
                array[n2++] = b11;
                array[n2++] = b10;
                array[n2++] = b9;
                array[n2] = b8;
                return 6;
            }
            final byte b13 = (byte)n;
            n >>= 8;
            if (n > -128L) {
                array[n2++] = (byte)n;
                array[n2++] = b13;
                array[n2++] = b12;
                array[n2++] = b11;
                array[n2++] = b10;
                array[n2++] = b9;
                array[n2] = b8;
                return 7;
            }
            final byte b14 = (byte)n;
            array[n2++] = (byte)(n >> 8);
            array[n2++] = b14;
            array[n2++] = b13;
            array[n2++] = b12;
            array[n2++] = b11;
            array[n2++] = b10;
            array[n2++] = b9;
            array[n2] = b8;
            return 8;
        }
    }
    
    private int write4ByteInt(int n, final byte[] array, final int n2) throws ClientException {
        int n3 = n2;
        if (n >= 0) {
            final byte b = (byte)n;
            n >>= 8;
            final byte b2 = (byte)n;
            n >>= 8;
            final byte b3 = (byte)n;
            array[n3++] = (byte)(n >> 8);
            array[n3++] = b3;
            array[n3++] = b2;
            array[n3] = b;
            return 4;
        }
        final byte b4 = (byte)n;
        n >>= 8;
        final byte b5 = (byte)n;
        n >>= 8;
        final byte b6 = (byte)n;
        array[n3++] = (byte)(n >> 8);
        array[n3++] = b6;
        array[n3++] = b5;
        array[n3] = b4;
        return 4;
    }
    
    private void streamBuffer(final byte[] array, final int n) throws ClientException {
        try {
            if (n == 0) {
                this.stream.write(6);
                return;
            }
            if (n >= 32768) {
                throw new ClientException(3, 7665970990714728616L, null);
            }
            this.stream.write(5);
            this.stream.write(Util.getHighByte((short)n));
            this.stream.write(Util.getLowByte((short)n));
            for (int i = 0; i < n; ++i) {
                this.stream.write(array[i]);
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
    
    static {
        ColumnStreamer.GREG_BASE = null;
        (ColumnStreamer.GREG_BASE = new GregorianCalendar(1950, 4, 2)).set(10, 0);
        ColumnStreamer.GREG_BASE.set(11, 0);
        ColumnStreamer.GREG_BASE.set(9, 0);
        ColumnStreamer.GREG_BASE.set(12, 0);
        ColumnStreamer.GREG_BASE.set(13, 0);
        ColumnStreamer.GREG_BASE.set(14, 0);
    }
}
