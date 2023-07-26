// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Open4GLException;
import java.util.GregorianCalendar;

public class Util
{
    private static final long NUM_OF_DAY_MILLIS = 86400000L;
    
    public static int extractShort(final byte b, final byte b2) {
        return (b & 0xFF) * 256 + (b2 & 0xFF);
    }
    
    public static byte getLowByte(final short n) {
        return (byte)(n & 0xFF);
    }
    
    public static byte getHighByte(final short n) {
        return (byte)(n >> 8);
    }
    
    public static int extractLong(final byte b, final byte b2, final byte b3, final byte b4) {
        return ((b & 0xFF) << 24) + ((b2 & 0xFF) << 16) + ((b3 & 0xFF) << 8) + (b4 & 0xFF);
    }
    
    public static void insertLong(final byte[] array, int n) {
        array[3] = (byte)n;
        n >>= 8;
        array[2] = (byte)n;
        n >>= 8;
        array[1] = (byte)n;
        n >>= 8;
        array[0] = (byte)n;
    }
    
    public static long millisToDays(final long n, final long n2) {
        final long n3 = n - n2;
        final long n4 = n3 / 86400000L;
        long n5 = n3 - n4 * 86400000L;
        if (n5 < 0L) {
            n5 = -n5;
        }
        final boolean b = n5 / 8.64E7 > 0.5;
        if (b && n3 < 0L) {
            return n4 - 1L;
        }
        if (b && n3 > 0L) {
            return n4 + 1L;
        }
        return n4;
    }
    
    public static long getDaysFromBaseDate(final GregorianCalendar gregorianCalendar, final GregorianCalendar gregorianCalendar2) {
        gregorianCalendar.clear(11);
        gregorianCalendar.clear(10);
        gregorianCalendar.clear(9);
        gregorianCalendar.clear(12);
        gregorianCalendar.clear(13);
        gregorianCalendar.clear(14);
        gregorianCalendar2.clear(11);
        gregorianCalendar2.clear(10);
        gregorianCalendar2.clear(9);
        gregorianCalendar2.clear(12);
        gregorianCalendar2.clear(13);
        gregorianCalendar2.clear(14);
        return millisToDays(gregorianCalendar.getTime().getTime(), gregorianCalendar2.getTime().getTime());
    }
    
    public static String getMessageText(final long n) {
        return new Open4GLException(n, null).getMessage();
    }
    
    public static String getMessageText(final long n, final String s) {
        return new Open4GLException(n, new Object[] { s }).getMessage();
    }
    
    public static String getMessageText(final long n, final String s, final String s2) {
        return new Open4GLException(n, new Object[] { s, s2 }).getMessage();
    }
}
