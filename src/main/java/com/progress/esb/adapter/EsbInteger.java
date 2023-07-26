// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import java.util.Iterator;
import com.sonicsw.xqimpl.util.DOMUtils;
import java.util.ArrayList;
import org.w3c.dom.Element;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGMetaData;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.proxygen.PGParam;
import com.sonicsw.xq.XQLog;

public class EsbInteger
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
        if (n != 4) {
            throw new ScriptEngineException("Incorrect parameter type - Expected INTEGER, got " + Parameter.proToName(n), 0);
        }
        return o.toString();
    }
    
    static String serializeArrayParameter(final Object o, final String s, final int n, final int n2) throws ScriptEngineException {
        if (n != 4) {
            throw new ScriptEngineException("Incorrect parameter type - Expected INTEGER, got " + Parameter.proToName(n), 0);
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(s);
        sb.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        final Integer[] array = (Integer[])o;
        for (int i = 0; i < array.length; ++i) {
            final Integer n3 = array[i];
            if (null != n3) {
                sb.append("<item>");
                sb.append(n3.toString());
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
        if (n != 4) {
            throw new ScriptEngineException("Incorrect parameter type - Expected INTEGER, got " + Parameter.proToName(n), 0);
        }
        Integer value;
        try {
            value = Integer.valueOf(s);
        }
        catch (NumberFormatException ex) {
            throw new ScriptEngineException("Invalid INTEGER value '" + s + "' sent to deserializer", 1);
        }
        return value;
    }
    
    static Object[] parseArrayParameter(final Element element, final int n, final int n2) throws ScriptEngineException {
        final ArrayList<Integer> list = new ArrayList<Integer>();
        if (n != 4) {
            throw new ScriptEngineException("Incorrect parameter type - Expected INTEGER, got " + Parameter.proToName(n), 0);
        }
        final Iterator<Element> iterator = (Iterator<Element>)DOMUtils.getImmediateChildElementsByName(element, "item").iterator();
        while (iterator.hasNext()) {
            final String firstTextChildValue = DOMUtils.getFirstTextChildValue((Element)iterator.next());
            Integer value;
            try {
                value = Integer.valueOf(firstTextChildValue);
            }
            catch (NumberFormatException ex) {
                throw new ScriptEngineException("Invalid INTEGER value '" + firstTextChildValue + "' sent to deserializer", 1);
            }
            list.add(value);
        }
        final Integer[] a = new Integer[list.size()];
        list.toArray(a);
        return a;
    }
    
    static {
        EsbInteger.m_log = XQLogImpl.getCategoryLog(64);
    }
}
