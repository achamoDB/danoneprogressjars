// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.util.Vector;
import com.progress.open4gl.Handle;
import org.w3c.dom.Element;
import org.apache.soap.util.Bean;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import org.apache.soap.util.xml.Deserializer;

public class HandleArraySerializer implements Deserializer
{
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        final Vector unMarshall = new ArrayParamSerializer().unMarshall(s, new QName("http://www.w3.org/2001/XMLSchema", "handle"), xmlJavaMappingRegistry, soapContext, (Element)node, new Handle(0L));
        return new Bean(Handle[].class, (Object)unMarshall.toArray(new Handle[unMarshall.size()]));
    }
}
