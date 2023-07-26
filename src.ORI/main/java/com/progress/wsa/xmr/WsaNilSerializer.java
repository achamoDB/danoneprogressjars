// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.io.IOException;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Serializer;

public class WsaNilSerializer implements Serializer
{
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        nsStack.pushScope();
        writer.write("<" + o2.toString() + " " + "xsi" + ":nil=\"true\"/>");
        nsStack.popScope();
    }
}
