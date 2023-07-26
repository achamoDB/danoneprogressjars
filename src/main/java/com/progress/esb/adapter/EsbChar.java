// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import java.util.Iterator;
import com.sonicsw.xqimpl.util.DOMUtils;
import java.util.ArrayList;
import org.w3c.dom.Element;
import com.sonicsw.xqimpl.util.xml.XMLUtils;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGMetaData;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.proxygen.PGParam;
import com.sonicsw.xq.XQLog;

public class EsbChar
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
        if (n != 39 && n != 1 && n != 19) {
            throw new ScriptEngineException("Incorrect parameter type - Expected character, got " + Parameter.proToName(n), 0);
        }
        return o.toString();
    }
    
    static String serializeArrayParameter(final Object o, final String s, final int n, final int n2) throws ScriptEngineException {
        if (n != 39 && n != 1 && n != 19) {
            throw new ScriptEngineException("Incorrect parameter type - Expected character, got " + Parameter.proToName(n), 0);
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(s);
        sb.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        final String[] array = (String[])o;
        for (int i = 0; i < array.length; ++i) {
            final String s2 = array[i];
            if (null != s2) {
                sb.append("<item>");
                sb.append(XMLUtils.escapeElementContent(s2.toString()));
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
        if (n != 39 && n != 1 && n != 19) {
            throw new ScriptEngineException("Incorrect parameter type - Expected character, got " + Parameter.proToName(n), 0);
        }
        return s;
    }
    
    static Object[] parseArrayParameter(final Element element, final int n, final int n2) throws ScriptEngineException {
        final ArrayList<String> list = new ArrayList<String>();
        if (n != 39 && n != 1 && n != 19) {
            throw new ScriptEngineException("Incorrect parameter type - Expected character, got " + Parameter.proToName(n), 0);
        }
        final Iterator<Element> iterator = DOMUtils.getImmediateChildElementsByName(element, "item").iterator();
        while (iterator.hasNext()) {
            list.add(DOMUtils.getFirstTextChildValue((Element)iterator.next()));
        }
        final String[] a = new String[list.size()];
        list.toArray(a);
        return a;
    }
    
    static {
        EsbChar.m_log = XQLogImpl.getCategoryLog(64);
    }
}
