// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.util.Vector;
import org.w3c.dom.Element;
import org.apache.soap.util.Bean;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import java.io.IOException;
import java.util.GregorianCalendar;
import com.progress.wsa.admin.PscDeploymentDescriptor;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.Serializer;

public class DateTimeTZArraySerializer implements Serializer, Deserializer
{
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        final GregorianCalendar[] calArray = ((DateTimeTZArrayHolder)o).getCalArray();
        final int defaultEncodingType = ((PscDeploymentDescriptor)soapContext.getProperty("psc.wsa.deployment_desc")).defaultEncodingType();
        Object o3 = o2;
        int n = 0;
        final Serializer querySerializer = xmlJavaMappingRegistry.querySerializer(DateTimeTZHolder.class, s);
        if (defaultEncodingType == 2) {
            writer.write("<" + o2.toString());
            o3 = "item";
            n = 1;
        }
        if (defaultEncodingType == 1) {
            writer.write("<" + o2.toString() + " soapenc" + ":" + "arrayType" + "=\"" + "xsd" + ":dateTime");
            if (calArray != null) {
                writer.write("[" + calArray.length + "]");
            }
            else {
                writer.write("[]");
            }
            writer.write("\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"");
            o3 = "item";
            n = 1;
        }
        if (null != calArray) {
            if (n != 0) {
                writer.write(">");
            }
            for (int i = 0; i < calArray.length; ++i) {
                querySerializer.marshall(s, clazz, (Object)new DateTimeTZHolder(calArray[i]), o3, writer, nsStack, xmlJavaMappingRegistry, soapContext);
            }
        }
        else {
            writer.write(" xsi:nil=\"true\"/>");
            n = 0;
        }
        if (n != 0) {
            writer.write("</" + o2.toString() + ">");
        }
    }
    
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        final Vector unMarshall = new ArrayParamSerializer().unMarshall(s, new QName("http://www.w3.org/2001/XMLSchema", "dateTimeTZ"), xmlJavaMappingRegistry, soapContext, (Element)node, new GregorianCalendar());
        return new Bean(GregorianCalendar[].class, (Object)unMarshall.toArray(new GregorianCalendar[unMarshall.size()]));
    }
}
