// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import javax.wsdl.WSDLException;
import org.w3c.dom.Element;
import java.io.Writer;
import org.w3c.dom.Node;
import com.ibm.wsdl.util.xml.DOM2Writer;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.Definition;
import java.io.PrintWriter;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;
import javax.wsdl.extensions.ExtensionDeserializer;
import javax.wsdl.extensions.ExtensionSerializer;

public class ProTypesSerializer implements ExtensionSerializer, ExtensionDeserializer
{
    public void marshall(final Class clazz, final QName qName, final ExtensibilityElement extensibilityElement, final PrintWriter printWriter, final Definition definition, final ExtensionRegistry extensionRegistry) throws WSDLException {
        final Element schemaDefinition = ((ProTypesExt)extensibilityElement).getSchemaDefinition();
        if (schemaDefinition != null) {
            DOM2Writer.serializeAsXML((Node)schemaDefinition, (Writer)printWriter);
            printWriter.println();
        }
    }
    
    public ExtensibilityElement unmarshall(final Class clazz, final QName qName, final Element element, final Definition definition, final ExtensionRegistry extensionRegistry) throws WSDLException {
        return null;
    }
}
