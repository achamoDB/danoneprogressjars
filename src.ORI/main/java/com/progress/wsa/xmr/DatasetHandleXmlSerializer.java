// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import com.progress.open4gl.DatasetHandleAsXml;
import java.io.IOException;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.Bean;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;

public class DatasetHandleXmlSerializer extends XmlPassthroughSerializer
{
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        final Bean unmarshall = super.unmarshall(s, qName, node, xmlJavaMappingRegistry, soapContext);
        unmarshall.type = DatasetHandleAsXml.class;
        return unmarshall;
    }
    
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        final String string = o.toString();
        if (!string.startsWith("<ProDataSet xmlns=\"\"")) {
            throw new IllegalArgumentException("XML representation of OUTPUT DATASET-HANDLE does not start as expected");
        }
        if (!string.endsWith("</ProDataSet>")) {
            throw new IllegalArgumentException("XML representation of OUTPUT DATASET-HANDLE does not end as expected");
        }
        writer.write("<" + o2 + string.substring("<ProDataSet xmlns=\"\"".length(), string.lastIndexOf("</ProDataSet>")) + "</" + o2 + ">");
    }
}
