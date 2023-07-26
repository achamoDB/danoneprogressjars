// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import org.apache.xml.serialize.BaseMarkupSerializer;
import com.progress.open4gl.DatasetAsXml;
import org.apache.soap.util.xml.DOMUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import org.apache.soap.util.xml.NSStack;
import java.io.IOException;
import java.io.Writer;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;
import org.apache.xml.serialize.OutputFormat;
import java.io.StringWriter;
import org.apache.soap.util.Bean;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import org.apache.soap.util.xml.Serializer;
import org.apache.soap.util.xml.Deserializer;

public class XmlPassthroughSerializer implements Deserializer, Serializer
{
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        final StringWriter stringWriter = new StringWriter();
        final OutputFormat outputFormat = new OutputFormat();
        if (node.getNodeType() == 1) {
            final Element serializeableDomTree = this.createSerializeableDomTree(s, (Element)node);
            String string;
            try {
                outputFormat.setOmitXMLDeclaration(true);
                ((BaseMarkupSerializer)new XMLSerializer((Writer)stringWriter, outputFormat)).serialize(serializeableDomTree);
                stringWriter.close();
                string = stringWriter.toString();
            }
            catch (IOException cause) {
                throw new IllegalArgumentException("Failed to serialize DOM node argument.", cause);
            }
            return new Bean(DatasetAsXml.class, (Object)string);
        }
        throw new IllegalArgumentException("Source DOM node is not a DOM Element node");
    }
    
    public void marshall(final String s, final Class clazz, final Object o, final Object obj, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IOException {
        final String string = o.toString();
        if (string.startsWith("<" + obj)) {
            writer.write(string);
        }
        else {
            final String substring = string.substring(1, string.indexOf(" "));
            writer.write("<" + obj + string.substring(("<" + substring).length(), string.lastIndexOf("</" + substring + ">")) + "</" + obj + ">");
        }
    }
    
    protected Element createSerializeableDomTree(final String s, final Element element) {
        Element resolveHrefs = element;
        Node node = element.getParentNode();
        resolveHrefs.removeAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type");
        while (node.getNodeType() == 1) {
            final NamedNodeMap attributes = node.getAttributes();
            for (int length = attributes.getLength(), i = 0; i < length; ++i) {
                final Attr attr = (Attr)attributes.item(i);
                if (attr.getName().equals("xmlns")) {
                    if (!resolveHrefs.hasAttribute(attr.getName())) {
                        resolveHrefs.setAttributeNode((Attr)attr.cloneNode(true));
                    }
                }
                else if (attr.getPrefix().equals("xmlns") && !resolveHrefs.hasAttributeNS(attr.getNamespaceURI(), attr.getLocalName())) {
                    resolveHrefs.setAttributeNodeNS((Attr)attr.cloneNode(true));
                }
            }
            node = node.getParentNode();
        }
        if (s != null && s.equals("http://schemas.xmlsoap.org/soap/encoding/")) {
            resolveHrefs = this.resolveHrefs(resolveHrefs);
        }
        return resolveHrefs;
    }
    
    protected Element resolveHrefs(final Element element) throws IllegalArgumentException {
        Element nextElement = element;
        final Element documentElement = element.getOwnerDocument().getDocumentElement();
        while (nextElement != null) {
            final String href = this.getHref(nextElement);
            if (href != null) {
                final Element elementByID = DOMUtils.getElementByID(documentElement, href);
                if (elementByID == null) {
                    throw new IllegalArgumentException("No XML element found with ID '" + href + "'.");
                }
                nextElement.removeAttribute("href");
                final NamedNodeMap attributes = elementByID.getAttributes();
                for (int i = 0; i < attributes.getLength(); ++i) {
                    final Attr attributeNode = (Attr)attributes.item(i);
                    final String namespaceURI = attributeNode.getNamespaceURI();
                    final String localName = attributeNode.getLocalName();
                    if (!attributeNode.getName().equals("id") && (!namespaceURI.equals("http://schemas.xmlsoap.org/soap/encoding/") || !localName.equals("root")) && (!namespaceURI.equals("http://schemas.xmlsoap.org/soap/envelope/") || !localName.equals("encodingStyle"))) {
                        elementByID.removeAttributeNode(attributeNode);
                        nextElement.setAttributeNode(attributeNode);
                    }
                }
                Node firstChild = elementByID.getFirstChild();
                if (firstChild != null) {
                    Node nextSibling;
                    do {
                        nextSibling = firstChild.getNextSibling();
                        elementByID.removeChild(firstChild);
                        nextElement.appendChild(firstChild);
                    } while ((firstChild = nextSibling) != null);
                }
            }
            nextElement = this.getNextElement(element, nextElement);
        }
        return element;
    }
    
    private Element getNextElement(final Element element, final Element element2) {
        Element element3 = DOMUtils.getFirstChildElement(element2);
        if (null == element3) {
            element3 = DOMUtils.getNextSiblingElement(element2);
        }
        if (null == element3 && element2 != element) {
            for (Element element4 = (Element)element2.getParentNode(); element4 != element && null == element3; element3 = DOMUtils.getNextSiblingElement(element4), element4 = (Element)element4.getParentNode()) {}
        }
        return element3;
    }
    
    protected String getHref(final Element element) throws IllegalArgumentException {
        String str = DOMUtils.getAttribute(element, "href");
        if (str != null) {
            if (str.length() <= 1 || str.charAt(0) != '#') {
                throw new IllegalArgumentException("Attempting to resolve SOAP encoded reference failed: ID '" + str + "' is not a relative reference. It must " + "start with a '#'.");
            }
            str = str.substring(1);
        }
        return str;
    }
}
