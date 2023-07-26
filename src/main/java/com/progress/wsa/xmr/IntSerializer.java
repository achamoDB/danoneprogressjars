// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import org.apache.soap.util.xml.DOMUtils;
import org.w3c.dom.Element;
import org.apache.soap.util.Bean;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import java.io.IOException;
import org.apache.soap.Utils;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.Serializer;

public class IntSerializer implements Serializer, Deserializer
{
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        nsStack.pushScope();
        writer.write("<" + o2.toString());
        if (null != s && 0 == s.compareTo("http://schemas.xmlsoap.org/soap/encoding/")) {
            writer.write(" xsi:type=\"xsd:int\"");
        }
        if (null != o) {
            writer.write(">" + Utils.cleanString(o.toString()) + "</" + o2.toString() + ">");
        }
        else {
            writer.write(" xsi:nil=\"true\"/>");
        }
        nsStack.popScope();
    }
    
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        final Element element = (Element)node;
        final String attributeNS = DOMUtils.getAttributeNS(element, "http://www.w3.org/2001/XMLSchema-instance", "nil");
        if (null != attributeNS && (attributeNS.equals("true") || attributeNS.equals("1"))) {
            return new Bean(Integer.class, (Object)null);
        }
        final String childCharacterData = DOMUtils.getChildCharacterData(element);
        try {
            return new Bean(Integer.class, (Object)Integer.decode(childCharacterData));
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid integer value '" + childCharacterData + "' sent to deserializer");
        }
    }
}
