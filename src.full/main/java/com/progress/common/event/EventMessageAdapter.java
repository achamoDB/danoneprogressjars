// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.event;

import com.progress.common.message.ProMessageFormat;
import java.util.Locale;
import com.progress.common.message.IProMessage;

public class EventMessageAdapter
{
    private static IProMessage m_proMessage;
    private static Locale m_locale;
    
    public static String getMessage(final long lng, final Object[] array) {
        String s3;
        try {
            String s2;
            if (EventMessageAdapter.m_proMessage == null) {
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
                s2 = EventMessageAdapter.m_proMessage.getMessage(lng);
            }
            s3 = ProMessageFormat.format(s2, array, EventMessageAdapter.m_locale);
        }
        catch (IProMessage.ProMessageException ex) {
            s3 = ex.toString();
        }
        return s3;
    }
    
    public static String getMessage(final String str, final Object[] array) {
        String s;
        try {
            s = ProMessageFormat.format(str, array, EventMessageAdapter.m_locale);
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
        EventMessageAdapter.m_proMessage = proMessage;
        EventMessageAdapter.m_locale = null;
    }
    
    public static void setMessageSubsystem(final IProMessage proMessage, final Locale locale) {
        EventMessageAdapter.m_proMessage = proMessage;
        EventMessageAdapter.m_locale = locale;
    }
    
    static {
        EventMessageAdapter.m_proMessage = null;
        EventMessageAdapter.m_locale = null;
    }
}
