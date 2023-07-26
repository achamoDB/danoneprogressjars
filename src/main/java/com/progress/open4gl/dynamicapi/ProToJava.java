// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.GregorianCalendar;
import java.math.BigDecimal;
import com.progress.open4gl.Memptr;
import com.progress.open4gl.Rowid;
import com.progress.open4gl.Open4GLError;

class ProToJava
{
    private StreamReader reader;
    private byte[] buffer;
    private char[] charBuffer;
    private static final int MILLIS_PER_DAY = 86400000;
    
    ProToJava(final StreamReader reader) {
        this.buffer = new byte[100];
        this.charBuffer = new char[100];
        this.reader = reader;
    }
    
    boolean isNull() {
        final int columnType = this.reader.getColumnType();
        return columnType == 3 || columnType == 11;
    }
    
    boolean noReturnValue() {
        return this.reader.getColumnType() == 11;
    }
    
    String getProString() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        if (this.buffer.length < columnLength) {
            this.buffer = new byte[columnLength];
        }
        this.reader.readColumn(this.buffer, 0, columnLength);
        try {
            return new String(this.buffer, 0, columnLength, "UTF8");
        }
        catch (Exception ex) {
            throw new Open4GLError(66L, null);
        }
    }
    
    byte[] getProRaw() throws ClientException {
        return this.getProBytes();
    }
    
    Rowid getProRowid() throws ClientException {
        return new Rowid(this.getProBytes());
    }
    
    private byte[] getProBytes() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        final byte[] array = new byte[columnLength];
        this.reader.readColumn(array, 0, columnLength);
        return array;
    }
    
    Memptr getProMemptr() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        byte[] array;
        try {
            array = new byte[columnLength];
        }
        catch (Error error) {
            System.out.println("Output Memptr Parameter gets error: " + error.getClass().getName());
            throw error;
        }
        return new Memptr(array);
    }
    
    String getProLongchar() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        byte[] bytes;
        try {
            bytes = new byte[columnLength];
        }
        catch (Error error) {
            System.out.println("Output Memptr Parameter gets error: " + error.getClass().getName());
            throw error;
        }
        return new String(bytes);
    }
    
    int getProInt() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        int n;
        if (columnLength == 0) {
            n = 0;
        }
        else {
            this.reader.readColumn(this.buffer, 0, columnLength);
            if (this.buffer[0] < 0) {
                n = -1;
            }
            else {
                n = 0;
            }
            int n2 = 0;
            switch (columnLength) {
                case 4: {
                    n = (this.buffer[n2++] & 0xFF);
                }
                case 3: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 2: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 1: {
                    n = (n << 8) + (this.buffer[n2] & 0xFF);
                    break;
                }
            }
        }
        return n;
    }
    
    long getProInt64() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        long n;
        if (columnLength == 0) {
            n = 0L;
        }
        else {
            this.reader.readColumn(this.buffer, 0, columnLength);
            if (this.buffer[0] < 0) {
                n = -1L;
            }
            else {
                n = 0L;
            }
            int n2 = 0;
            switch (columnLength) {
                case 8: {
                    n = (this.buffer[n2++] & 0xFF);
                }
                case 7: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 6: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 5: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 4: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 3: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 2: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 1: {
                    n = (n << 8) + (this.buffer[n2] & 0xFF);
                    break;
                }
            }
        }
        return n;
    }
    
    BigDecimal getProDecimal(final Integer n) throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        if (columnLength != 0) {
            if (this.buffer.length < columnLength) {
                this.buffer = new byte[columnLength];
            }
            this.reader.readColumn(this.buffer, 0, columnLength);
            final int n2 = columnLength - 1;
            int n3 = n2 * 2;
            if ((this.buffer[n2] & 0xF) == 0xF) {
                --n3;
            }
            final boolean b = (this.buffer[0] & 0x80) != 0x0;
            final int n4 = this.buffer[0] & 0x7F;
            final int n5 = n3 - n4;
            int n6;
            if (n4 != 0) {
                n6 = n5 + (b ? 0 : 1);
            }
            else {
                n6 = -1;
            }
            int n7;
            if (n5 > 0) {
                n7 = (b ? 0 : 1);
            }
            else {
                n7 = n6 + 1;
            }
            int n8;
            if (n4 != 0) {
                n8 = n5 + (b ? 0 : 1);
            }
            else {
                n8 = -1;
            }
            if (this.charBuffer.length < n3 + 2) {
                this.charBuffer = new char[n3 + 2];
            }
            if (!b) {
                this.charBuffer[0] = '-';
            }
            if (n8 != -1) {
                this.charBuffer[n8] = '.';
            }
            int n9 = n7;
            for (int i = 1; i <= n2; ++i) {
                this.charBuffer[n9++] = (char)(48 + ((this.buffer[i] & 0xF0) >> 4));
                if (n9 == n8) {
                    ++n9;
                }
                if ((this.buffer[i] & 0xF) != 0xF) {
                    this.charBuffer[n9++] = (char)(48 + (this.buffer[i] & 0xF));
                }
                if (n9 == n8) {
                    ++n9;
                }
            }
            return new BigDecimal(new String(this.charBuffer, 0, (n8 > n9) ? n8 : n9));
        }
        return new BigDecimal(0);
    }
    
    double getProDouble() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        double n = 0.0;
        if (columnLength != 0) {
            this.reader.readColumn(this.buffer, 0, columnLength);
            final int n2 = columnLength - 1;
            final int n3 = this.buffer[0] & 0x80;
            final int n4 = this.buffer[0] & 0x7F;
            for (int i = 1; i <= n2; ++i) {
                n = n * 10.0 + ((this.buffer[i] & 0xF0) >> 4);
                if ((this.buffer[i] & 0xF) != 0xF) {
                    n = n * 10.0 + (this.buffer[i] & 0xF);
                }
            }
            int n5 = n4;
            while (--n5 >= 0) {
                n /= 10.0;
            }
            if (n3 == 0) {
                n = -n;
            }
        }
        return n;
    }
    
    boolean getProBoolean() throws ClientException {
        if (this.reader.getColumnLength() != 0) {
            this.reader.readColumn(this.buffer, 0, this.reader.getColumnLength());
        }
        return this.reader.getColumnType() != 6;
    }
    
    GregorianCalendar getProDate() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        int n;
        if (columnLength == 0) {
            n = 0;
        }
        else {
            this.reader.readColumn(this.buffer, 0, columnLength);
            if (this.buffer[0] < 0) {
                n = -1;
            }
            else {
                n = 0;
            }
            int n2 = 0;
            switch (columnLength) {
                case 4: {
                    n = (this.buffer[n2++] & 0xFF);
                }
                case 3: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 2: {
                    n = (n << 8) + (this.buffer[n2++] & 0xFF);
                }
                case 1: {
                    n = (n << 8) + (this.buffer[n2] & 0xFF);
                    break;
                }
            }
        }
        if (this.reader.getColumnType() != 4) {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar(1969, 11, 31, 12, 0, 0);
            gregorianCalendar.getTime();
            gregorianCalendar.add(5, -7183 + n);
            final GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
            gregorianCalendar2.set(10, 0);
            gregorianCalendar2.set(11, 0);
            gregorianCalendar2.set(9, 0);
            gregorianCalendar2.set(12, 0);
            gregorianCalendar2.set(13, 0);
            gregorianCalendar2.set(14, 0);
            gregorianCalendar2.set(1, gregorianCalendar.get(1));
            gregorianCalendar2.set(2, gregorianCalendar.get(2));
            gregorianCalendar2.set(5, gregorianCalendar.get(5));
            return gregorianCalendar2;
        }
        return new GregorianCalendar();
    }
    
    GregorianCalendar getProDateTime() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        int n = 0;
        int value = 0;
        int value2 = 0;
        int value3 = 0;
        int value4 = 0;
        int n2;
        if (columnLength == 0) {
            n2 = 0;
        }
        else {
            this.reader.readColumn(this.buffer, 0, columnLength);
            if (this.buffer[0] < 0) {
                n2 = -1;
            }
            else {
                n2 = 0;
            }
            int n3 = 0;
            switch (columnLength - 4) {
                case 4: {
                    n2 = (this.buffer[n3++] & 0xFF);
                }
                case 3: {
                    n2 = (n2 << 8) + (this.buffer[n3++] & 0xFF);
                }
                case 2: {
                    n2 = (n2 << 8) + (this.buffer[n3++] & 0xFF);
                }
                case 1: {
                    n2 = (n2 << 8) + (this.buffer[n3++] & 0xFF);
                    break;
                }
            }
            if (columnLength > 4) {
                n = ((((this.buffer[n3++] & 0xFF) << 8) + (this.buffer[n3++] & 0xFF) << 8) + (this.buffer[n3++] & 0xFF) << 8) + (this.buffer[n3++] & 0xFF);
            }
            value = n / 3600000;
            final int n4 = n % 3600000;
            value2 = n4 / 60000;
            final int n5 = n4 % 60000;
            value3 = n5 / 1000;
            value4 = n5 - value3 * 1000;
        }
        if (this.reader.getColumnType() != 4) {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar(1969, 11, 31, 12, 0, 0);
            gregorianCalendar.getTime();
            gregorianCalendar.add(5, -7183 + n2);
            final GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
            gregorianCalendar2.set(1, gregorianCalendar.get(1));
            gregorianCalendar2.set(2, gregorianCalendar.get(2));
            gregorianCalendar2.set(5, gregorianCalendar.get(5));
            gregorianCalendar2.set(11, value);
            gregorianCalendar2.set(12, value2);
            gregorianCalendar2.set(13, value3);
            gregorianCalendar2.set(14, value4);
            return gregorianCalendar2;
        }
        return new GregorianCalendar();
    }
    
    GregorianCalendar getProDateTimeTz() throws ClientException {
        final int columnLength = this.reader.getColumnLength();
        int n = 0;
        int value = 0;
        int value2 = 0;
        int value3 = 0;
        int value4 = 0;
        int value5 = 0;
        int n2 = 0;
        int n3;
        if (columnLength == 0) {
            n3 = 0;
        }
        else {
            this.reader.readColumn(this.buffer, 0, columnLength);
            if (this.buffer[0] < 0) {
                n3 = -1;
            }
            else {
                n3 = 0;
            }
            int n4 = 0;
            switch (columnLength - 8) {
                case 4: {
                    n = (this.buffer[n4++] & 0xFF);
                }
                case 3: {
                    n = (n << 8) + (this.buffer[n4++] & 0xFF);
                }
                case 2: {
                    n = (n << 8) + (this.buffer[n4++] & 0xFF);
                }
                case 1: {
                    n = (n << 8) + (this.buffer[n4++] & 0xFF);
                    break;
                }
            }
            if (columnLength > 4) {
                n3 = ((((this.buffer[n4++] & 0xFF) << 8) + (this.buffer[n4++] & 0xFF) << 8) + (this.buffer[n4++] & 0xFF) << 8) + (this.buffer[n4++] & 0xFF);
            }
            if (columnLength > 8) {
                value = ((((this.buffer[n4++] & 0xFF) << 8) + (this.buffer[n4++] & 0xFF) << 8) + (this.buffer[n4++] & 0xFF) << 8) + (this.buffer[n4++] & 0xFF);
            }
            n2 = value * 60 * 1000;
            int n5 = n + n2;
            if (n5 >= 86400000) {
                ++n3;
                n5 -= 86400000;
            }
            else if (n5 < 0) {
                --n3;
                n5 += 86400000;
            }
            value2 = n5 / 3600000;
            final int n6 = n5 % 3600000;
            value3 = n6 / 60000;
            final int n7 = n6 % 60000;
            value4 = n7 / 1000;
            value5 = n7 - value4 * 1000;
        }
        if (this.reader.getColumnType() == 4) {
            return new GregorianCalendar();
        }
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(1969, 11, 31, 12, 0, 0);
        gregorianCalendar.getTime();
        gregorianCalendar.add(5, -7183 + n3);
        final String[] availableIDs = TimeZone.getAvailableIDs(n2);
        if (null == availableIDs) {
            throw new ClientException(3, 7665970990714728444L, new Object[] { new Integer(value).toString() });
        }
        final GregorianCalendar gregorianCalendar2 = new GregorianCalendar(new SimpleTimeZone(n2, availableIDs[0]));
        gregorianCalendar2.set(16, 0);
        gregorianCalendar2.set(1, gregorianCalendar.get(1));
        gregorianCalendar2.set(2, gregorianCalendar.get(2));
        gregorianCalendar2.set(5, gregorianCalendar.get(5));
        gregorianCalendar2.set(11, value2);
        gregorianCalendar2.set(12, value3);
        gregorianCalendar2.set(13, value4);
        gregorianCalendar2.set(14, value5);
        return gregorianCalendar2;
    }
}
