// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import org.apache.soap.util.xml.Deserializer;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.DOMUtils;
import java.util.Vector;
import org.w3c.dom.Element;
import org.apache.soap.util.xml.QName;
import java.io.IOException;
import com.progress.wsa.admin.PscDeploymentDescriptor;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Serializer;

public class ArrayParamSerializer implements Serializer
{
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        final ArrayParamHolder arrayParamHolder = (ArrayParamHolder)o;
        final Object[] array = arrayParamHolder.getArray();
        final int defaultEncodingType = ((PscDeploymentDescriptor)soapContext.getProperty("psc.wsa.deployment_desc")).defaultEncodingType();
        String s2 = s;
        Object o3 = o2;
        int n = 0;
        final Class elementClassType = arrayParamHolder.getElementClassType();
        final Serializer querySerializer = xmlJavaMappingRegistry.querySerializer(elementClassType, s);
        if (defaultEncodingType == 2) {
            writer.write("<" + o2.toString());
            o3 = "item";
            n = 1;
        }
        if (defaultEncodingType == 1) {
            writer.write("<" + o2.toString() + " soapenc" + ":" + "arrayType" + "=\"" + "xsd" + ":" + ((SOAP4glXMR)xmlJavaMappingRegistry).queryElementType(elementClassType, s).getLocalPart());
            if (array != null) {
                writer.write("[" + array.length + "]");
            }
            else {
                writer.write("[]");
            }
            writer.write("\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"");
            s2 = null;
            o3 = "item";
            n = 1;
        }
        if (null != array) {
            if (n != 0) {
                writer.write(">");
            }
            for (int i = 0; i < array.length; ++i) {
                querySerializer.marshall(s2, clazz, array[i], o3, writer, nsStack, xmlJavaMappingRegistry, soapContext);
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
    
    public Vector unMarshall(final String s, final QName qName, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext, Element element, Object value) throws IllegalArgumentException {
        final Vector<Object> vector = new Vector<Object>();
        Element element2 = element;
        final Element element3 = element;
        final Deserializer queryDeserializer = xmlJavaMappingRegistry.queryDeserializer(qName, s);
        if (DOMUtils.countKids(element, element.getNodeType()) >= 1) {
            element = (element2 = DOMUtils.getFirstChildElement(element));
        }
        while (element2 != null) {
            final String attribute = element2.getAttribute("href");
            Element elementByID;
            if (attribute != null && !attribute.equals("") && attribute.charAt(0) == '#') {
                final String substring = attribute.substring(1);
                elementByID = DOMUtils.getElementByID(element3.getOwnerDocument().getDocumentElement(), substring);
                if (elementByID == null) {
                    throw new IllegalArgumentException("No such ID '" + substring + "' found in ArrayParamSerializer deserializer");
                }
            }
            else {
                elementByID = element2;
            }
            value = queryDeserializer.unmarshall(s, qName, (Node)elementByID, xmlJavaMappingRegistry, soapContext).value;
            vector.add(value);
            element2 = DOMUtils.getNextSiblingElement(element2);
            if (element2 != null && element2.getLocalName().compareTo(element.getLocalName()) != 0) {
                element2 = null;
            }
        }
        return vector;
    }
}
