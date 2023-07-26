// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import java.util.Iterator;
import com.sonicsw.xqimpl.util.DOMUtils;
import java.util.ArrayList;
import org.w3c.dom.Element;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Handle;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGMetaData;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.proxygen.PGParam;
import com.sonicsw.xq.XQLog;

public class EsbInt64
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
        if (n != 41 && n != 10 && n != 7 && n != 14) {
            throw new ScriptEngineException("Incorrect parameter type - Expected INT64, got " + Parameter.proToName(n), 0);
        }
        String s2 = null;
        switch (n) {
            case 10: {
                s2 = Long.toString(((Handle)o).getLong());
                break;
            }
            case 14: {
                s2 = Long.toString(((COMHandle)o).getLong());
                break;
            }
            default: {
                s2 = o.toString();
                break;
            }
        }
        return s2;
    }
    
    static String serializeArrayParameter(final Object o, final String s, final int n, final int n2) throws ScriptEngineException {
        if (n != 41 && n != 10 && n != 7 && n != 14) {
            throw new ScriptEngineException("Incorrect parameter type - Expected INT64, got " + Parameter.proToName(n), 0);
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
                String str = null;
                switch (n) {
                    case 10: {
                        str = Long.toString(((Handle)o2).getLong());
                        break;
                    }
                    case 14: {
                        str = Long.toString(((COMHandle)o2).getLong());
                        break;
                    }
                    default: {
                        str = o2.toString();
                        break;
                    }
                }
                sb.append(str);
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
        if (n != 41 && n != 10 && n != 7 && n != 14) {
            throw new ScriptEngineException("Incorrect parameter type - Expected INT64, got " + Parameter.proToName(n), 0);
        }
        Long value;
        try {
            value = Long.valueOf(s);
        }
        catch (NumberFormatException ex) {
            throw new ScriptEngineException("Invalid INT64 value '" + s + "' sent to deserializer", 1);
        }
        return value;
    }
    
    public static Object[] parseArrayParameter(final Element element, final int n, final int n2) throws ScriptEngineException {
        final ArrayList<Handle> list = new ArrayList<Handle>();
        if (n != 41 && n != 10 && n != 7 && n != 14) {
            throw new ScriptEngineException("Incorrect parameter type - Expected INT64, got " + Parameter.proToName(n), 0);
        }
        final Iterator<Element> iterator = DOMUtils.getImmediateChildElementsByName(element, "item").iterator();
        while (iterator.hasNext()) {
            final String firstTextChildValue = DOMUtils.getFirstTextChildValue((Element)iterator.next());
            Long value;
            try {
                value = Long.valueOf(firstTextChildValue);
            }
            catch (NumberFormatException ex) {
                throw new ScriptEngineException("Invalid INT64 value '" + firstTextChildValue + "' sent to deserializer", 1);
            }
            switch (n) {
                case 10: {
                    list.add(new Handle(value));
                    continue;
                }
                case 14: {
                    list.add((Handle)new COMHandle(value));
                    continue;
                }
                default: {
                    list.add((Handle)value);
                    continue;
                }
            }
        }
        final Object[] a = new Object[list.size()];
        list.toArray(a);
        return a;
    }
    
    static {
        EsbInt64.m_log = XQLogImpl.getCategoryLog(64);
    }
}
