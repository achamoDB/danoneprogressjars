// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import java.util.Date;
import java.text.ParseException;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Iterator;
import com.sonicsw.xqimpl.util.DOMUtils;
import java.util.ArrayList;
import org.w3c.dom.Element;
import java.util.GregorianCalendar;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGMetaData;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.proxygen.PGParam;
import com.sonicsw.xq.XQLog;

public class EsbDateTimeTZ
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
        if (n != 40) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATETIMETZ, got " + Parameter.proToName(n), 0);
        }
        return serializeDateTimeTZ((GregorianCalendar)o);
    }
    
    public static String serializeArrayParameter(final Object o, final String s, final int n, final int n2) throws ScriptEngineException {
        if (n != 40) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATETIMETZ, got " + Parameter.proToName(n), 0);
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
                sb.append(serializeDateTimeTZ(gregorianCalendar));
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
        if (n != 40) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATETIMETZ, got " + Parameter.proToName(n), 0);
        }
        return parseDateTimeTZ(s);
    }
    
    static Object[] parseArrayParameter(final Element element, final int n, final int n2) throws ScriptEngineException {
        final ArrayList<GregorianCalendar> list = new ArrayList<GregorianCalendar>();
        if (n != 40) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATETIMETZ, got " + Parameter.proToName(n), 0);
        }
        final Iterator<Element> iterator = DOMUtils.getImmediateChildElementsByName(element, "item").iterator();
        while (iterator.hasNext()) {
            list.add(parseDateTimeTZ(DOMUtils.getFirstTextChildValue((Element)iterator.next())));
        }
        final GregorianCalendar[] a = new GregorianCalendar[list.size()];
        list.toArray(a);
        return a;
    }
    
    private static String serializeDateTimeTZ(final GregorianCalendar gregorianCalendar) {
        String string = null;
        if (null != gregorianCalendar) {
            final DecimalFormat decimalFormat = new DecimalFormat("#0000;-#0000");
            final DecimalFormat decimalFormat2 = new DecimalFormat("00");
            final DecimalFormat decimalFormat3 = new DecimalFormat("000");
            final StringBuffer sb = new StringBuffer();
            sb.append(decimalFormat.format(gregorianCalendar.get(1)) + "-");
            sb.append(decimalFormat2.format(gregorianCalendar.get(2) + 1) + "-");
            sb.append(decimalFormat2.format(gregorianCalendar.get(5)) + "T");
            sb.append(decimalFormat2.format(gregorianCalendar.get(11)) + ":");
            sb.append(decimalFormat2.format(gregorianCalendar.get(12)) + ":");
            sb.append(decimalFormat2.format(gregorianCalendar.get(13)) + ".");
            sb.append(decimalFormat3.format(gregorianCalendar.get(14)));
            int n = (gregorianCalendar.get(15) + gregorianCalendar.get(16)) / 1000;
            if (n < 0) {
                sb.append("-");
                n *= -1;
            }
            else {
                sb.append("+");
            }
            sb.append(decimalFormat2.format(n / 3600) + ":");
            sb.append(decimalFormat2.format(n / 60 % 60));
            string = sb.toString();
        }
        return string;
    }
    
    private static GregorianCalendar parseDateTimeTZ(final String str) throws ScriptEngineException {
        int beginIndex = 0;
        if (str.charAt(0) == '-') {
            beginIndex = 1;
        }
        final String s = new String(str.substring(beginIndex, str.length()));
        if (s.length() < 19) {
            throw new IllegalArgumentException("Invalid dateTime value " + str + " sent to deserializer");
        }
        try {
            final String s2 = new String("GMT");
            int amount = 0;
            final int beginIndex2 = s.indexOf(".") + 1;
            String id;
            int endIndex;
            if (s.indexOf("Z") != -1) {
                id = s2.concat("+00:00");
                endIndex = s.indexOf("Z");
            }
            else if (s.indexOf("+") != -1) {
                id = s2.concat(s.substring(s.length() - 6, s.length()));
                endIndex = s.indexOf("+");
            }
            else {
                if (s.indexOf("-", s.indexOf("T")) == -1) {
                    s.length();
                    throw new IllegalArgumentException("Invalid dateTime value " + str + " sent to deserializer. Time zone is required");
                }
                id = s2.concat(s.substring(s.length() - 6, s.length()));
                endIndex = s.lastIndexOf("-");
            }
            if (beginIndex2 != 0) {
                int n = endIndex - beginIndex2;
                if (n >= 3) {
                    endIndex = beginIndex2 + 3;
                    if (n > 3) {
                        if (new Integer(s.substring(beginIndex2 + 4, beginIndex2 + 5)) >= 5) {
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
                amount = new Integer(s.substring(beginIndex2, endIndex)) + n;
            }
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            Date time;
            if (s.charAt(5) == '-') {
                time = new SimpleDateFormat("yyyyy-MM-dd'T'HH:mm:ssz").parse(s.substring(0, 20) + id);
            }
            else {
                time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz").parse(s.substring(0, 19) + id);
            }
            gregorianCalendar.setTime(time);
            gregorianCalendar.add(14, amount);
            if (id != null) {
                gregorianCalendar.setTimeZone(TimeZone.getTimeZone(id));
            }
            return gregorianCalendar;
        }
        catch (ParseException ex) {
            throw new ScriptEngineException("Invalid dateTimeTZ value '" + str + "' sent to deserializer", 1);
        }
    }
    
    static {
        EsbDateTimeTZ.m_log = XQLogImpl.getCategoryLog(64);
    }
}
