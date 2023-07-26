// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.message;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.text.FieldPosition;
import java.text.MessageFormat;
import com.progress.international.resources.TranslatableString;
import java.util.Locale;

public class ProMessageFormat
{
    public static final String NEWLINE;
    public static final int PROMSG_CUROUT = 1;
    public static final int PROMSG_STDOUT = 2;
    public static final int PROMSG_LOG = 4;
    public static final int PROMSG_DUMP = 8;
    public static final int PROMSG_WARNING = 16;
    public static final int PROMSG_FATAL = 32;
    String m_pattern;
    Locale m_locale;
    int m_messageFlags;
    
    public ProMessageFormat(final String s) {
        this.applyPattern(s);
    }
    
    public static String format(final String s, final Object[] array) {
        return format(s, array, null, null);
    }
    
    public static String format(final String s, final Object[] array, final Locale locale) {
        return format(s, array, locale, null);
    }
    
    public static String format(final String s, final Object[] array, final int[] array2) {
        return format(s, array, null, array2);
    }
    
    public static String format(final String s, final Object[] obj, final Locale locale, final int[] array) {
        String str = parsePattern(s, array);
        String s2;
        try {
            if (obj != null) {
                for (int i = 0; i < obj.length; ++i) {
                    if (obj[i] != null) {
                        obj[i] = obj[i].toString();
                        final int index;
                        if ((index = ((String)obj[i]).indexOf(39)) > -1) {
                            obj[i] = TranslatableString.formatSingleQuotes((String)obj[i], index);
                        }
                    }
                    else {
                        obj[i] = "null";
                    }
                }
            }
            final int index2;
            if ((index2 = str.indexOf(39)) > -1) {
                str = TranslatableString.formatSingleQuotes(str, index2);
            }
            if (locale == null) {
                s2 = MessageFormat.format(str, obj);
            }
            else {
                final MessageFormat messageFormat = new MessageFormat(str);
                final StringBuffer result = new StringBuffer();
                messageFormat.setLocale(locale);
                messageFormat.format(obj, result, null);
                s2 = result.toString();
            }
        }
        catch (Exception ex) {
            if (obj != null && obj.length > 0) {
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                ex.printStackTrace(new PrintWriter(out, true));
                s2 = "Can't format message: \"" + str + "\" Args: " + obj + "\nException: " + out.toString();
            }
            else {
                s2 = s;
            }
        }
        return s2;
    }
    
    private static String parsePattern(final String s, final int[] array) {
        final char[] charArray = s.toCharArray();
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        final int length = charArray.length;
        int n = 0;
        int n2 = 0;
        while (i < length) {
            final char c = charArray[i++];
            if (c == '\0') {
                break;
            }
            if (c != '%') {
                sb.append(c);
            }
            else {
                boolean b = true;
                if (i >= length) {
                    break;
                }
                final char c2 = charArray[i++];
                if (c2 == '\0') {
                    break;
                }
                switch (c2) {
                    case 67:
                    case 68:
                    case 69:
                    case 70:
                    case 75:
                    case 78:
                    case 88:
                    case 98:
                    case 99:
                    case 100:
                    case 105:
                    case 107:
                    case 108:
                    case 110:
                    case 112:
                    case 115:
                    case 116:
                    case 120: {
                        sb.append("{" + n++ + "}");
                        break;
                    }
                    case 66: {
                        n2 |= 0x5;
                        break;
                    }
                    case 103: {
                        n2 |= 0x25;
                        break;
                    }
                    case 71: {
                        n2 |= 0x2D;
                        break;
                    }
                    case 76: {
                        n2 |= 0x4;
                        break;
                    }
                    case 77: {
                        break;
                    }
                    case 79: {
                        n2 |= 0x2;
                        break;
                    }
                    case 114: {
                        sb.append(ProMessageFormat.NEWLINE);
                        break;
                    }
                    case 84: {
                        break;
                    }
                    case 87: {
                        n2 |= 0x10;
                        break;
                    }
                    default: {
                        b = false;
                        sb.append('%');
                        sb.append(c2);
                        break;
                    }
                }
                if (!b || i >= length || charArray[i] != '<') {
                    continue;
                }
                while (++i < length && charArray[i] != '>') {}
                if (i >= length) {
                    continue;
                }
                ++i;
            }
        }
        if (array != null) {
            array[0] = n2;
        }
        return sb.toString();
    }
    
    public void applyPattern(final String s) {
        final int[] array = { 0 };
        this.m_pattern = parsePattern(s, array);
        this.m_messageFlags = array[0];
    }
    
    public String format(final Object[] array) {
        return this.format(array, null);
    }
    
    public String format(final Object[] array, final int[] array2) {
        String s;
        if (this.m_locale == null) {
            s = MessageFormat.format(this.m_pattern, array);
        }
        else {
            final MessageFormat messageFormat = new MessageFormat(this.m_pattern);
            final StringBuffer result = new StringBuffer();
            messageFormat.setLocale(this.m_locale);
            messageFormat.format(array, result, null);
            s = result.toString();
        }
        if (array2 != null) {
            array2[0] = this.m_messageFlags;
        }
        return s;
    }
    
    public Locale getLocale() {
        return this.m_locale;
    }
    
    public void setLocale(final Locale locale) {
        this.m_locale = locale;
    }
    
    public String toPattern() {
        return this.m_pattern;
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
}
