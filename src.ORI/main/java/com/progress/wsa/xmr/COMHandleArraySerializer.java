// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import org.apache.soap.util.xml.DOMUtils;
import java.util.Vector;
import org.w3c.dom.Element;
import org.apache.soap.util.Bean;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import java.io.IOException;
import com.progress.open4gl.COMHandle;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.Serializer;

public class COMHandleArraySerializer implements Serializer, Deserializer
{
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        final COMHandle[] array = (COMHandle[])o;
        final Serializer querySerializer = xmlJavaMappingRegistry.querySerializer(COMHandle.class, s);
        for (int i = 0; i < array.length; ++i) {
            querySerializer.marshall(s, clazz, (Object)array[i], o2, writer, nsStack, xmlJavaMappingRegistry, soapContext);
        }
    }
    
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        final Element element = (Element)node;
        final QName qName2 = new QName("http://www.w3.org/2001/XMLSchema", "comhandle");
        final Deserializer queryDeserializer = xmlJavaMappingRegistry.queryDeserializer(qName2, s);
        int i = 1;
        final Vector vector = new Vector<COMHandle>();
        Element nextSiblingElement = element;
        while (i != 0) {
            vector.add((COMHandle)queryDeserializer.unmarshall(s, qName2, (Node)nextSiblingElement, xmlJavaMappingRegistry, soapContext).value);
            nextSiblingElement = DOMUtils.getNextSiblingElement(nextSiblingElement);
            if (nextSiblingElement.getLocalName() != element.getLocalName()) {
                i = 0;
            }
        }
        return new Bean(COMHandle[].class, (Object)vector.toArray(new COMHandle[vector.size()]));
    }
}
