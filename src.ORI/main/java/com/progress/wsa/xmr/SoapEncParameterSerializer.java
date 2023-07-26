// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import org.apache.soap.rpc.Parameter;
import org.apache.soap.util.xml.DOMUtils;
import org.apache.soap.encoding.soapenc.SoapEncUtils;
import org.w3c.dom.Element;
import org.apache.soap.util.Bean;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import org.apache.soap.encoding.soapenc.ParameterSerializer;

public class SoapEncParameterSerializer extends ParameterSerializer
{
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        String attributeNS = s;
        Element element = (Element)node;
        QName qName2 = SoapEncUtils.getTypeQName(element);
        final String attribute = DOMUtils.getAttribute(element, "href");
        String s2 = element.getTagName();
        Bean unmarshall2;
        if (attribute != null && attribute.length() > 0 && attribute.charAt(0) == '#') {
            if (null == qName2 || null == attributeNS) {
                final String substring = attribute.substring(1);
                final Element elementByID = DOMUtils.getElementByID(node.getOwnerDocument().getDocumentElement(), substring);
                if (elementByID == null) {
                    throw new IllegalArgumentException("No such ID '" + substring + "'.");
                }
                element = elementByID;
                if (null == qName2) {
                    qName2 = SoapEncUtils.getTypeQName(elementByID);
                }
                if (null == attributeNS) {
                    attributeNS = elementByID.getAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle");
                }
            }
            if (null == qName2) {
                final String namespaceURI = element.getNamespaceURI();
                s2 = element.getTagName();
                if (namespaceURI != null) {
                    qName2 = new QName(namespaceURI, s2);
                }
                else {
                    qName2 = new QName("", s2);
                }
            }
            final Bean unmarshall = xmlJavaMappingRegistry.unmarshall(attributeNS, qName2, (Node)element, soapContext);
            unmarshall2 = new Bean(Parameter.class, (Object)new Parameter(s2, unmarshall.type, unmarshall.value, (String)null));
        }
        else {
            unmarshall2 = super.unmarshall(s, qName, node, xmlJavaMappingRegistry, soapContext);
        }
        return unmarshall2;
    }
}
