// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import java.util.Iterator;
import com.sonicsw.xqimpl.util.DOMUtils;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.apache.soap.encoding.soapenc.Base64;
import com.progress.open4gl.Rowid;
import com.progress.open4gl.Memptr;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGMetaData;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.proxygen.PGParam;
import com.sonicsw.xq.XQLog;

public class EsbMemptr
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
        if (n != 11 && n != 8 && n != 13 && n != 18) {
            throw new ScriptEngineException("Incorrect parameter type - Expected MEMPTR, got " + Parameter.proToName(n), 0);
        }
        byte[] array = null;
        switch (n) {
            case 11: {
                array = ((Memptr)o).getBytes();
                break;
            }
            case 13: {
                array = ((Rowid)o).getBytes();
                break;
            }
            case 8:
            case 18: {
                array = (byte[])o;
                break;
            }
        }
        return Base64.encode(array);
    }
    
    static String serializeArrayParameter(final Object o, final String s, final int n, final int n2) throws ScriptEngineException {
        if (n != 11 && n != 8 && n != 13 && n != 18) {
            throw new ScriptEngineException("Incorrect parameter type - Expected MEMPTR, got " + Parameter.proToName(n), 0);
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(s);
        sb.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        final Object[] array = (Object[])o;
        for (int i = 0; i < array.length; ++i) {
            final Object o2 = array[i];
            if (null != o2) {
                byte[] array2 = null;
                switch (n) {
                    case 11: {
                        array2 = ((Memptr)o2).getBytes();
                        break;
                    }
                    case 13: {
                        array2 = ((Rowid)o2).getBytes();
                        break;
                    }
                    case 8:
                    case 18: {
                        array2 = (byte[])o2;
                        break;
                    }
                }
                if (null != array2 && array2.length > 0) {
                    sb.append("<item>");
                    sb.append(Base64.encode(array2));
                }
                else {
                    sb.append("<item xsi:nil=\"true\">");
                }
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
    
    static Object parseParameter(final String str, final int n) throws ScriptEngineException {
        final Object o = null;
        if (null == str) {
            return o;
        }
        if (n != 11 && n != 8 && n != 13 && n != 18) {
            throw new ScriptEngineException("Incorrect parameter type - Expected MEMPTR, got " + Parameter.proToName(n), 0);
        }
        Memptr memptr;
        try {
            memptr = new Memptr(Base64.decode(str));
        }
        catch (RuntimeException ex) {
            throw new ScriptEngineException("Invalid BASE64 encoded value '" + str + "' sent to deserializer", 1);
        }
        return memptr;
    }
    
    static Object[] parseArrayParameter(final Element element, final int n, final int n2) throws ScriptEngineException {
        Object e = null;
        final ArrayList<Object> list = new ArrayList<Object>();
        if (n != 11 && n != 8 && n != 13 && n != 18) {
            throw new ScriptEngineException("Incorrect parameter type - Expected MEMPTR, got " + Parameter.proToName(n), 0);
        }
        final Iterator<Element> iterator = (Iterator<Element>)DOMUtils.getImmediateChildElementsByName(element, "item").iterator();
        while (iterator.hasNext()) {
            final String firstTextChildValue = DOMUtils.getFirstTextChildValue((Element)iterator.next());
            try {
                final byte[] decode = Base64.decode(firstTextChildValue);
                switch (n) {
                    case 11: {
                        e = new Memptr(decode);
                        break;
                    }
                    case 8:
                    case 18: {
                        e = decode;
                        break;
                    }
                    case 13: {
                        e = new Rowid(decode);
                        break;
                    }
                }
            }
            catch (RuntimeException ex) {
                throw new ScriptEngineException("Invalid BASE64 encoded value '" + firstTextChildValue + "' sent to deserializer", 1);
            }
            list.add(e);
        }
        final Object[] a = new Object[list.size()];
        list.toArray(a);
        return a;
    }
    
    static {
        EsbMemptr.m_log = XQLogImpl.getCategoryLog(64);
    }
}
