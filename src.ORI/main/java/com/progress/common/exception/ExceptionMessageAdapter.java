// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.exception;

import com.progress.common.message.ProMessageFormat;
import java.util.Locale;
import com.progress.common.message.IProMessage;

public class ExceptionMessageAdapter
{
    private static IProMessage m_proMessage;
    private static Locale m_locale;
    
    public static String getMessage(final long n, final Object o) {
        return getMessage(n, new Object[] { o });
    }
    
    public static String getMessage(final long n, final Object o, final Object o2) {
        return getMessage(n, new Object[] { o, o2 });
    }
    
    public static String getMessage(final long n, final Object o, final Object o2, final Object o3) {
        return getMessage(n, new Object[] { o, o2, o3 });
    }
    
    public static String getMessage(final long lng, final Object[] array) {
        String s3;
        try {
            String s2;
            if (ExceptionMessageAdapter.m_proMessage == null) {
                String s = "Error accessing message #" + lng;
                if (array != null && array.length > 0) {
                    s += ", arguments: ";
                    for (int i = 0; i < array.length; ++i) {
                        s += array[i];
                        if (i < array.length - 1) {
                            s += ", ";
                        }
                    }
                }
                s2 = s + ".";
            }
            else {
                s2 = ExceptionMessageAdapter.m_proMessage.getMessage(lng);
            }
            if (array != null) {
                adjustArgs(array);
            }
            s3 = ProMessageFormat.format(s2, array, ExceptionMessageAdapter.m_locale);
        }
        catch (IProMessage.ProMessageException ex) {
            s3 = ex.toString();
        }
        return s3;
    }
    
    public static String getMessage(final String s, final Object o) {
        return getMessage(s, new Object[] { o });
    }
    
    public static String getMessage(final String s, final Object o, final Object o2) {
        return getMessage(s, new Object[] { o, o2 });
    }
    
    public static String getMessage(final String s, final Object o, final Object o2, final Object o3) {
        return getMessage(s, new Object[] { o, o2, o3 });
    }
    
    public static String getMessage(final String str, final Object[] array) {
        String s;
        try {
            if (array != null) {
                adjustArgs(array);
            }
            s = ProMessageFormat.format(str, array, ExceptionMessageAdapter.m_locale);
        }
        catch (Throwable t) {
            String s2 = "Error formatting message: " + str;
            if (array != null && array.length > 0) {
                s2 += ", arguments: ";
                for (int i = 0; i < array.length; ++i) {
                    s2 += array[i];
                    if (i < array.length - 1) {
                        s2 += ", ";
                    }
                }
            }
            s = s2 + ".";
        }
        return s;
    }
    
    public static void setMessageSubsystem(final IProMessage proMessage) {
        if (ExceptionMessageAdapter.m_proMessage == null) {
            ExceptionMessageAdapter.m_proMessage = proMessage;
        }
        ExceptionMessageAdapter.m_locale = null;
    }
    
    public static void setMessageSubsystem(final IProMessage proMessage, final Locale locale) {
        ExceptionMessageAdapter.m_proMessage = proMessage;
        ExceptionMessageAdapter.m_locale = locale;
    }
    
    static void adjustArgs(final Object[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == null) {
                array[i] = "NULL";
            }
        }
    }
    
    static {
        ExceptionMessageAdapter.m_proMessage = null;
        ExceptionMessageAdapter.m_locale = null;
    }
}
