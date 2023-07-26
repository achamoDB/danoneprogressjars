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
import com.progress.common.ehnlog.AppLogger;
import com.progress.wsa.admin.WsaParser;
import com.progress.wsa.open4gl.XMLSerializableRoot;
import com.progress.wsa.admin.WSAD;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.Serializer;

public class WSADSerializer implements Serializer, Deserializer
{
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        if (0 != s.compareTo("http://schemas.xmlsoap.org/soap/encoding/")) {
            throw new IllegalArgumentException("WSAD only supports SOAP encoding");
        }
        nsStack.pushScope();
        if (null == o || null == ((WSAD)o).getPGAppObj()) {
            writer.write("<" + o2.toString());
            final String prefixFromURI = nsStack.getPrefixFromURI("http://www.w3.org/2001/XMLSchema-instance", writer);
            final String prefixFromURI2 = nsStack.getPrefixFromURI("urn:schemas-progress-com:WSAD:0009", writer);
            writer.write(" " + prefixFromURI + ":" + "type" + "=\"");
            writer.write(prefixFromURI2 + ":" + ((WSAD)o).getXMLType() + "\"");
            writer.write(" " + prefixFromURI + ":nil=\"true\"/>");
        }
        else {
            writer.write("<" + o2.toString());
            final String prefixFromURI3 = nsStack.getPrefixFromURI("http://www.w3.org/2001/XMLSchema-instance", writer);
            final String prefixFromURI4 = nsStack.getPrefixFromURI("urn:schemas-progress-com:WSAD:0009", writer);
            writer.write(" " + prefixFromURI3 + ":" + "type" + "=\"");
            writer.write(prefixFromURI4 + ":" + ((WSAD)o).getXMLType() + "\">");
            final XMLSerializableRoot xmlSerializableRoot = (XMLSerializableRoot)o;
            xmlSerializableRoot.setPrefix(prefixFromURI4);
            xmlSerializableRoot.setTargetNamespace("urn:schemas-progress-com:WSAD:0009");
            new WsaParser(null, null).serializeObject(xmlSerializableRoot, writer);
            writer.write("</" + o2.toString() + ">");
        }
        nsStack.popScope();
    }
    
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        final String attributeNS = DOMUtils.getAttributeNS((Element)node, "http://www.w3.org/2001/XMLSchema-instance", "nil");
        if (null != attributeNS && (attributeNS.equals("true") || attributeNS.equals("1"))) {
            return new Bean(WSAD.class, (Object)null);
        }
        final WSAD wsad = new WSAD();
        wsad.readXML(node);
        return new Bean(WSAD.class, (Object)wsad);
    }
}
