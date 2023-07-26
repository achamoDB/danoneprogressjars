// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import java.text.ParseException;
import java.util.TimeZone;
import java.util.Date;
import java.util.Iterator;
import com.sonicsw.xqimpl.util.DOMUtils;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import org.w3c.dom.Element;
import java.util.GregorianCalendar;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGMetaData;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.proxygen.PGParam;
import com.sonicsw.xq.XQLog;

public class EsbDateTime
{
    private static XQLog m_log;
    
    public static String serializeParameter(final Object o, final PGParam pgParam, String s) throws ScriptEngineException {
        final int paramType = pgParam.getParamType();
        final int extent = pgParam.getExtent();
        final String paramName = pgParam.getParamName();
        if (null == s) {
            s = paramName;
        }
        String s2;
        if (!pgParam.isExtentField()) {
            s2 = serializeParameter(o, s, paramType);
        }
        else {
            s2 = serializeArrayParameter(o, s, paramType, extent);
        }
        return s2;
    }
    
    public static String serializeParameter(final Object o, final PGMetaData pgMetaData, String s) throws ScriptEngineException {
        final int type = pgMetaData.getType();
        final int extent = pgMetaData.getExtent();
        final String fieldName = pgMetaData.getFieldName();
        if (null == s) {
            s = fieldName;
        }
        String s2;
        if (extent == 0) {
            s2 = serializeParameter(o, s, type);
        }
        else {
            s2 = serializeArrayParameter(o, s, type, extent);
        }
        return s2;
    }
    
    static String serializeParameter(final Object o, final String s, final int n) throws ScriptEngineException {
        if (n != 34) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATETIME, got " + Parameter.proToName(n), 0);
        }
        return serializeDate((GregorianCalendar)o);
    }
    
    static String serializeArrayParameter(final Object o, final String s, final int n, final int n2) throws ScriptEngineException {
        if (n != 34) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATETIME, got " + Parameter.proToName(n), 0);
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(s);
        sb.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        final GregorianCalendar[] array = (GregorianCalendar[])o;
        for (int i = 0; i < array.length; ++i) {
            final GregorianCalendar gregorianCalendar = array[i];
            if (null != gregorianCalendar) {
                sb.append("<item>");
                sb.append(serializeDate(gregorianCalendar));
            }
            else {
                sb.append("<item xsi:nil=\"true\">");
            }
            sb.append("</item>");
        }
        sb.append("</");
        sb.append(s);
        sb.append(">");
        return sb.toString();
    }
    
    public static Object parseParameter(final Object o, final PGParam pgParam) throws ScriptEngineException {
        final int paramType = pgParam.getParamType();
        final int extent = pgParam.getExtent();
        if (!pgParam.isExtentField()) {
            return parseParameter((String)o, paramType);
        }
        return parseArrayParameter((Element)o, paramType, extent);
    }
    
    public static Object parseParameter(final Object o, final PGMetaData pgMetaData) throws ScriptEngineException {
        final int type = pgMetaData.getType();
        final int extent = pgMetaData.getExtent();
        if (extent == 0) {
            return parseParameter((String)o, type);
        }
        return parseArrayParameter((Element)o, type, extent);
    }
    
    static Object parseParameter(final String s, final int n) throws ScriptEngineException {
        final Object o = null;
        if (null == s) {
            return o;
        }
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (n != 34) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATETIME, got " + Parameter.proToName(n), 0);
        }
        return parseDateTime(s);
    }
    
    static Object[] parseArrayParameter(final Element element, final int n, final int n2) throws ScriptEngineException {
        final ArrayList<GregorianCalendar> list = new ArrayList<GregorianCalendar>();
        if (n != 34) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATETIME, got " + Parameter.proToName(n), 0);
        }
        final Iterator<Element> iterator = DOMUtils.getImmediateChildElementsByName(element, "item").iterator();
        while (iterator.hasNext()) {
            list.add(parseDateTime(DOMUtils.getFirstTextChildValue((Element)iterator.next())));
        }
        final GregorianCalendar[] a = new GregorianCalendar[list.size()];
        list.toArray(a);
        return a;
    }
    
    private static String serializeDate(final GregorianCalendar gregorianCalendar) {
        String format = null;
        if (null != gregorianCalendar) {
            final Date time = gregorianCalendar.getTime();
            SimpleDateFormat simpleDateFormat;
            if (gregorianCalendar.get(1) > 9999) {
                simpleDateFormat = new SimpleDateFormat("yyyyy-MM-dd'T'HH:mm:ss'.'SSS");
            }
            else {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS");
            }
            format = simpleDateFormat.format(time);
        }
        return format;
    }
    
    private static GregorianCalendar parseDateTime(final String str) throws ScriptEngineException {
        int beginIndex = 0;
        try {
            final String s = new String("GMT");
            boolean b = false;
            if (str.charAt(0) == '-') {
                beginIndex = 1;
            }
            final String s2 = new String(str.substring(beginIndex, str.length()));
            if (beginIndex == 1) {
                if (str.charAt(6) == '-') {
                    b = true;
                }
            }
            else if (str.charAt(5) == '-') {
                b = true;
            }
            String s3;
            if (b) {
                s3 = new String("yyyyy-MM-dd'T'HH:mm:ss");
            }
            else {
                s3 = new String("yyyy-MM-dd'T'HH:mm:ss");
            }
            int amount = 0;
            final int beginIndex2 = s2.indexOf(".") + 1;
            String id;
            int endIndex;
            if (s2.indexOf("Z") != -1) {
                id = s.concat("+00:00");
                endIndex = s2.indexOf("Z");
                s3 += "z";
            }
            else if (s2.indexOf("+") != -1) {
                id = s.concat(s2.substring(s2.length() - 6, s2.length()));
                endIndex = s2.indexOf("+");
                s3 += "z";
            }
            else if (s2.indexOf("-", s2.indexOf("T")) != -1) {
                id = s.concat(s2.substring(s2.length() - 6, s2.length()));
                endIndex = s2.lastIndexOf("-");
                s3 += "z";
            }
            else {
                id = null;
                endIndex = s2.length();
            }
            if (beginIndex2 != 0) {
                int n = endIndex - beginIndex2;
                if (n >= 3) {
                    endIndex = beginIndex2 + 3;
                    if (n > 3) {
                        if (new Integer(s2.substring(beginIndex2 + 4, beginIndex2 + 5)) >= 5) {
                            n = 1;
                        }
                        else {
                            n = 0;
                        }
                    }
                    else {
                        n = 0;
                    }
                }
                amount = new Integer(s2.substring(beginIndex2, endIndex)) + n;
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s3);
            Date time;
            if (b) {
                time = simpleDateFormat.parse(s2.substring(0, 20) + id);
            }
            else {
                time = simpleDateFormat.parse(s2.substring(0, 19) + id);
            }
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(time);
            gregorianCalendar.add(14, amount);
            if (id != null) {
                gregorianCalendar.setTimeZone(TimeZone.getTimeZone(id));
            }
            return gregorianCalendar;
        }
        catch (ParseException ex) {
            throw new ScriptEngineException("Invalid dateTime value '" + str + "' sent to deserializer", 1);
        }
    }
    
    static {
        EsbDateTime.m_log = XQLogImpl.getCategoryLog(64);
    }
}
