// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import java.util.Iterator;
import com.sonicsw.xqimpl.util.DOMUtils;
import java.util.ArrayList;
import java.text.ParseException;
import org.w3c.dom.Element;
import java.util.Date;
import java.util.GregorianCalendar;
import com.progress.open4gl.Parameter;
import java.text.SimpleDateFormat;
import com.progress.open4gl.proxygen.PGMetaData;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.proxygen.PGParam;
import com.sonicsw.xq.XQLog;

public class EsbDate
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
        String format = null;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (n != 2) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATE, got " + Parameter.proToName(n), 0);
        }
        if (null != o) {
            Date time;
            if (o instanceof GregorianCalendar) {
                time = ((GregorianCalendar)o).getTime();
            }
            else {
                time = (Date)o;
            }
            format = simpleDateFormat.format(time);
        }
        return format;
    }
    
    static String serializeArrayParameter(final Object o, final String s, final int n, final int n2) throws ScriptEngineException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (n != 2) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATE, got " + Parameter.proToName(n), 0);
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(s);
        sb.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        final Object[] array = (Object[])o;
        for (int i = 0; i < array.length; ++i) {
            final Object o2 = array[i];
            if (null != o2) {
                sb.append("<item>");
                Date time;
                if (o2 instanceof GregorianCalendar) {
                    time = ((GregorianCalendar)o2).getTime();
                }
                else {
                    time = (Date)o2;
                }
                sb.append(simpleDateFormat.format(time));
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
        if (n != 2) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATE, got " + Parameter.proToName(n), 0);
        }
        GregorianCalendar gregorianCalendar;
        try {
            final Date parse = simpleDateFormat.parse(s);
            gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(parse);
        }
        catch (ParseException ex) {
            throw new ScriptEngineException("Invalid date value '" + s + "' sent to deserializer", 1);
        }
        return gregorianCalendar;
    }
    
    static Object[] parseArrayParameter(final Element element, final int n, final int n2) throws ScriptEngineException {
        final ArrayList<GregorianCalendar> list = new ArrayList<GregorianCalendar>();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (n != 2) {
            throw new ScriptEngineException("Incorrect parameter type - Expected DATE, got " + Parameter.proToName(n), 0);
        }
        final Iterator<Element> iterator = (Iterator<Element>)DOMUtils.getImmediateChildElementsByName(element, "item").iterator();
        while (iterator.hasNext()) {
            final String firstTextChildValue = DOMUtils.getFirstTextChildValue((Element)iterator.next());
            Date parse;
            try {
                parse = simpleDateFormat.parse(firstTextChildValue);
            }
            catch (ParseException ex) {
                throw new ScriptEngineException("Invalid date value '" + firstTextChildValue + "' sent to deserializer", 1);
            }
            final GregorianCalendar e = new GregorianCalendar();
            e.setTime(parse);
            list.add(e);
        }
        final GregorianCalendar[] a = new GregorianCalendar[list.size()];
        list.toArray(a);
        return a;
    }
    
    static {
        EsbDate.m_log = XQLogImpl.getCategoryLog(64);
    }
}
