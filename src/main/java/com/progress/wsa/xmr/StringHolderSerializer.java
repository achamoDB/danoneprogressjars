// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import org.apache.soap.util.Bean;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import java.io.IOException;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.Serializer;

public class StringHolderSerializer extends StringSerializer implements Serializer, Deserializer
{
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        throw new IllegalArgumentException("Invalid call to StringHolderSerializer's marshall method.");
    }
    
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        throw new IllegalArgumentException("Invalid call to StringHolderSerializer's unmarshall method.");
    }
}
