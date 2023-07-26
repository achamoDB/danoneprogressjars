// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.rpc.Parameter;
import com.progress.open4gl.DatasetHandleAsXml;
import com.progress.open4gl.DatasetAsXml;
import com.progress.open4gl.Memptr;
import com.progress.open4gl.Rowid;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Handle;
import java.util.GregorianCalendar;
import java.math.BigDecimal;
import org.w3c.dom.Element;
import org.apache.soap.rpc.RPCConstants;
import org.apache.soap.util.xml.Serializer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.QName;
import org.apache.soap.encoding.SOAPMappingRegistry;

public class SOAP4glXMR extends SOAPMappingRegistry
{
    protected ElementDeserializer m_xmlElementDeserializer;
    private String m_defaultEncodingStyle;
    
    public SOAP4glXMR() {
        this.m_xmlElementDeserializer = new ElementDeserializer();
        this.m_defaultEncodingStyle = null;
        this.mapProgressTypes();
    }
    
    public SOAP4glXMR(final String s) {
        this.m_xmlElementDeserializer = new ElementDeserializer();
        this.m_defaultEncodingStyle = null;
        this.mapProgressTypes();
    }
    
    public void setDefaultEncodingStyle(final String defaultEncodingStyle) {
        super.setDefaultEncodingStyle(this.m_defaultEncodingStyle = defaultEncodingStyle);
    }
    
    public Deserializer queryDeserializer(final QName qName, String defaultEncodingStyle) throws IllegalArgumentException {
        try {
            if (defaultEncodingStyle == null || defaultEncodingStyle.equals("")) {
                defaultEncodingStyle = this.m_defaultEncodingStyle;
            }
            return super.queryDeserializer(qName, defaultEncodingStyle);
        }
        catch (IllegalArgumentException ex) {
            return (Deserializer)this.m_xmlElementDeserializer;
        }
    }
    
    public Class queryJavaType(final QName qName, final String s) throws IllegalArgumentException {
        try {
            return super.queryJavaType(qName, s);
        }
        catch (IllegalArgumentException ex) {
            return Element.class;
        }
    }
    
    public QName queryElementType(final Class clazz, final String s) throws IllegalArgumentException {
        try {
            final QName queryElementType = super.queryElementType(clazz, s);
            final String localPart = queryElementType.getLocalPart();
            if (localPart.equals("stringPrgs")) {
                queryElementType.setLocalPart("string");
            }
            else if (localPart.equals("intPrgs")) {
                queryElementType.setLocalPart("int");
            }
            else if (localPart.equals("booleanPrgs")) {
                queryElementType.setLocalPart("boolean");
            }
            else if (localPart.equals("decimalPrgs")) {
                queryElementType.setLocalPart("decimal");
            }
            else if (localPart.equals("datePrgs")) {
                queryElementType.setLocalPart("date");
            }
            else if (localPart.equals("rowid") || localPart.equals("memptr") || localPart.equals("raw")) {
                queryElementType.setLocalPart("base64Binary");
            }
            else if (localPart.equals("recid") || localPart.equals("handle") || localPart.equals("comhandle")) {
                queryElementType.setLocalPart("long");
            }
            return queryElementType;
        }
        catch (IllegalArgumentException ex) {
            throw ex;
        }
    }
    
    protected void mapProgressTypes() {
        try {
            final Class<?> forName = Class.forName("com.progress.wsa.xmr.ElementDeserializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "string"), Element.class, (Serializer)null, (Deserializer)forName.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "boolean"), Element.class, (Serializer)null, (Deserializer)forName.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "int"), Element.class, (Serializer)null, (Deserializer)forName.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "decimal"), Element.class, (Serializer)null, (Deserializer)forName.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "date"), Element.class, (Serializer)null, (Deserializer)forName.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "long"), Element.class, (Serializer)null, (Deserializer)forName.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), Element.class, (Serializer)null, (Deserializer)forName.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "dateTime"), Element.class, (Serializer)null, (Deserializer)forName.newInstance());
            final Class<?> forName2 = Class.forName("com.progress.wsa.xmr.StringSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "stringPrgs"), String.class, (Serializer)forName2.newInstance(), (Deserializer)forName2.newInstance());
            final Class<?> forName3 = Class.forName("com.progress.wsa.xmr.BooleanSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "booleanPrgs"), Boolean.class, (Serializer)forName3.newInstance(), (Deserializer)forName3.newInstance());
            final Class<?> forName4 = Class.forName("com.progress.wsa.xmr.IntSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "intPrgs"), Integer.class, (Serializer)forName4.newInstance(), (Deserializer)forName4.newInstance());
            final Class<?> forName5 = Class.forName("com.progress.wsa.xmr.DecimalSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "decimalPrgs"), BigDecimal.class, (Serializer)forName5.newInstance(), (Deserializer)forName5.newInstance());
            final Class<?> forName6 = Class.forName("com.progress.wsa.xmr.DateSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "datePrgs"), GregorianCalendar.class, (Serializer)forName6.newInstance(), (Deserializer)forName6.newInstance());
            final Class<?> forName7 = Class.forName("com.progress.wsa.xmr.DateTimeSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "dateTimeS"), DateTimeHolder.class, (Serializer)forName7.newInstance(), (Deserializer)forName7.newInstance());
            final Class<?> forName8 = Class.forName("com.progress.wsa.xmr.DateTimeTZSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "dateTimeTZ"), DateTimeTZHolder.class, (Serializer)forName8.newInstance(), (Deserializer)forName8.newInstance());
            final Class<?> forName9 = Class.forName("com.progress.wsa.xmr.RecidSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "recid"), Long.class, (Serializer)forName9.newInstance(), (Deserializer)forName9.newInstance());
            final Class<?> forName10 = Class.forName("com.progress.wsa.xmr.HandleSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "handle"), Handle.class, (Serializer)forName10.newInstance(), (Deserializer)forName10.newInstance());
            final Class<?> forName11 = Class.forName("com.progress.wsa.xmr.COMHandleSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "comhandle"), COMHandle.class, (Serializer)forName11.newInstance(), (Deserializer)forName11.newInstance());
            final Class<?> forName12 = Class.forName("com.progress.wsa.xmr.RawSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "raw"), byte[].class, (Serializer)forName12.newInstance(), (Deserializer)forName12.newInstance());
            final Class<?> forName13 = Class.forName("com.progress.wsa.xmr.RowidSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "rowid"), Rowid.class, (Serializer)forName13.newInstance(), (Deserializer)forName13.newInstance());
            final Class<?> forName14 = Class.forName("com.progress.wsa.xmr.MemptrSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "memptr"), Memptr.class, (Serializer)forName14.newInstance(), (Deserializer)forName14.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "resultsetholder"), XmrResultSetHolder.class, (Serializer)Class.forName("com.progress.wsa.xmr.ResultSetHolderSerializer").newInstance(), (Deserializer)null);
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "wsanil"), WsaNil.class, (Serializer)Class.forName("com.progress.wsa.xmr.WsaNilSerializer").newInstance(), (Deserializer)null);
            final Class<?> forName15 = Class.forName("com.progress.wsa.xmr.XmlPassthroughSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "datasetasxml"), DatasetAsXml.class, (Serializer)forName15.newInstance(), (Deserializer)forName15.newInstance());
            final Class<?> forName16 = Class.forName("com.progress.wsa.xmr.DatasetHandleXmlSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "datasethandleasxml"), DatasetHandleAsXml.class, (Serializer)forName16.newInstance(), (Deserializer)forName16.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"), Element.class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.ArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "arrayParam"), ArrayParamHolder.class, (Serializer)Class.forName("com.progress.wsa.xmr.ArrayParamSerializer").newInstance(), (Deserializer)null);
            final Class<?> forName17 = Class.forName("com.progress.wsa.xmr.DateTimeArraySerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "dateTimeSArray"), DateTimeArrayHolder.class, (Serializer)forName17.newInstance(), (Deserializer)forName17.newInstance());
            final Class<?> forName18 = Class.forName("com.progress.wsa.xmr.DateTimeTZArraySerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "dateTimeTZArray"), DateTimeTZArrayHolder.class, (Serializer)forName18.newInstance(), (Deserializer)forName18.newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "intPrgsArray"), Integer[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.IntArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "decimalPrgsArray"), BigDecimal[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.DecimalArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "stringPrgsArray"), String[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.StringArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "booleanPrgsArray"), Boolean[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.BooleanArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "datePrgsArray"), GregorianCalendar[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.DateArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "recidArray"), Long[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.RecidArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "rawArray"), byte[][].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.RawArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "rowidArray"), Rowid[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.RowidArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "memptrArray"), Memptr[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.MemptrArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "comhandleArray"), COMHandle[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.COMHandleArraySerializer").newInstance());
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://www.w3.org/2001/XMLSchema", "handleArray"), Handle[].class, (Serializer)null, (Deserializer)Class.forName("com.progress.wsa.xmr.HandleArraySerializer").newInstance());
            final Class<?> forName19 = Class.forName("com.progress.wsa.xmr.SoapEncParameterSerializer");
            ((XMLJavaMappingRegistry)this).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", RPCConstants.Q_ELEM_PARAMETER, Parameter.class, (Serializer)forName19.newInstance(), (Deserializer)forName19.newInstance());
        }
        catch (IllegalAccessException ex) {}
        catch (InstantiationException ex2) {}
        catch (ClassNotFoundException ex3) {}
        catch (NoClassDefFoundError noClassDefFoundError) {}
    }
}
