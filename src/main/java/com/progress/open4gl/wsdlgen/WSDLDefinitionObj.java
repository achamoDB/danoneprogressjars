// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.ibm.wsdl.xml.WSDLWriterImpl;
import java.io.Writer;
import javax.wsdl.Types;
import com.progress.open4gl.proxygen.PGProcDetail;
import com.progress.open4gl.proxygen.PGProc;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.w3c.dom.Node;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensionRegistry;
import com.progress.open4gl.proxygen.PGGenInfo;
import javax.wsdl.Service;
import javax.wsdl.extensions.ExtensionDeserializer;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.wsdl.factory.WSDLFactory;
import com.ibm.wsdl.factory.WSDLFactoryImpl;
import javax.wsdl.Definition;
import com.progress.open4gl.proxygen.PGAppObj;

public class WSDLDefinitionObj
{
    public static Definition build(final PGAppObj pgAppObj) throws WSDLException {
        Definition definition = null;
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = genInfo.getDWGenInfo();
        try {
            definition = ((WSDLFactoryImpl)WSDLFactory.newInstance()).newDefinition();
            wsdlGenInfo.setDefinitionObj(definition);
            final ExtensionRegistry extensionRegistry = definition.getExtensionRegistry();
            final ProTypesSerializer proTypesSerializer = new ProTypesSerializer();
            extensionRegistry.registerSerializer(Types.class, ProTypesExt.Q_ELEM_TYPES_SCHEMA, (ExtensionSerializer)proTypesSerializer);
            extensionRegistry.registerDeserializer(Types.class, ProTypesExt.Q_ELEM_TYPES_SCHEMA, (ExtensionDeserializer)proTypesSerializer);
            extensionRegistry.mapExtensionTypes(Types.class, ProTypesExt.Q_ELEM_TYPES_SCHEMA, ProTypesExt.class);
            addDocumentationElement(pgAppObj);
            addNamespaces(pgAppObj);
            addTypesElement(pgAppObj);
            addGenericMessages(pgAppObj);
            addPortTypes(pgAppObj);
            addBindings(pgAppObj);
            final String soapEndPointURL = dwGenInfo.getSoapEndPointURL();
            if (soapEndPointURL != null && soapEndPointURL.length() > 0) {
                addService(null, pgAppObj);
            }
        }
        finally {}
        return definition;
    }
    
    private static void addDocumentationElement(final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        final Element element = coreDocImpl.createElement("wsdl:documentation");
        String string = "";
        final String author = genInfo.getAuthor();
        if (author != null && !author.equals("")) {
            string = "Author=" + author + ", ";
        }
        final String string2 = string + "EncodingType=";
        String str;
        if (dwGenInfo.getEncoding() == 1) {
            str = string2 + "RPC_ENCODED, ";
        }
        else if (dwGenInfo.getEncoding() == 3) {
            str = string2 + "DOC_LITERAL, ";
        }
        else {
            str = string2 + "RPC_LITERAL, ";
        }
        element.appendChild(coreDocImpl.createTextNode(str + wsdlGenInfo.getProductName() + "=" + wsdlGenInfo.getProductVersion()));
        definitionObj.setDocumentationElement(element);
    }
    
    private static void addNamespaces(final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        wsdlGenInfo.getDWGenInfo();
        final String targetNamespace = wsdlGenInfo.getTargetNamespace();
        String s;
        if (targetNamespace.startsWith("urn:")) {
            s = ":";
        }
        else {
            s = "/";
        }
        try {
            definitionObj.setQName(new QName(targetNamespace, pgAppObj.getAppObjectName()));
            definitionObj.setTargetNamespace(targetNamespace);
            definitionObj.addNamespace("tns", targetNamespace);
            definitionObj.addNamespace("xsd", wsdlGenInfo.getXSDSchemaNamespace());
            definitionObj.addNamespace("soap", wsdlGenInfo.getSoapNamespace());
            definitionObj.addNamespace("soapenc", wsdlGenInfo.getSoapEncNamespace());
            definitionObj.addNamespace("wsdl", wsdlGenInfo.getWSDLNamespace());
            definitionObj.addNamespace("prodata", wsdlGenInfo.getProdataNamespace());
            final String uniqueSchemaPrefix = wsdlGenInfo.getUniqueSchemaPrefix();
            wsdlGenInfo.updateUniqueSchemaPrefix();
            definitionObj.addNamespace(uniqueSchemaPrefix, wsdlGenInfo.getFaultNamespace());
            wsdlGenInfo.setFaultDetailSchemaPrefix(uniqueSchemaPrefix);
            final String uniqueSchemaPrefix2 = wsdlGenInfo.getUniqueSchemaPrefix();
            wsdlGenInfo.updateUniqueSchemaPrefix();
            final String string = targetNamespace + s + pgAppObj.getAppObjectName();
            definitionObj.addNamespace(uniqueSchemaPrefix2, string);
            pgAppObj.setSchemaPrefix(uniqueSchemaPrefix2);
            pgAppObj.setSchemaNamespace(string);
            if (pgAppObj.getSubObjects() != null) {
                for (int i = 0; i < pgAppObj.getSubObjects().length; ++i) {
                    final PGAppObj subObjects = pgAppObj.getSubObjects(i);
                    final String uniqueSchemaPrefix3 = wsdlGenInfo.getUniqueSchemaPrefix();
                    wsdlGenInfo.updateUniqueSchemaPrefix();
                    final String string2 = targetNamespace + s + subObjects.getAppObjectName();
                    definitionObj.addNamespace(uniqueSchemaPrefix3, string2);
                    subObjects.setSchemaPrefix(uniqueSchemaPrefix3);
                    subObjects.setSchemaNamespace(string2);
                    addProcObjectNamespaces(subObjects, genInfo, s);
                }
            }
            addProcObjectNamespaces(pgAppObj, genInfo, s);
        }
        catch (ClassCastException ex) {}
    }
    
    private static void addProcObjectNamespaces(final PGAppObj pgAppObj, final PGGenInfo pgGenInfo, final String str) {
        final WSDLGenInfo wsdlGenInfo = pgGenInfo.getWSDLGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final String targetNamespace = wsdlGenInfo.getTargetNamespace();
        try {
            for (int i = 0; i < pgAppObj.getPersistentProcedures().length; ++i) {
                final PGProc persistentProcedures = pgAppObj.getPersistentProcedures(i);
                final PGProcDetail procDetail = persistentProcedures.getProcDetail();
                final String uniqueSchemaPrefix = wsdlGenInfo.getUniqueSchemaPrefix();
                wsdlGenInfo.updateUniqueSchemaPrefix();
                final String string = targetNamespace + str + procDetail.getMethodName();
                definitionObj.addNamespace(uniqueSchemaPrefix, string);
                persistentProcedures.setSchemaPrefix(uniqueSchemaPrefix);
                persistentProcedures.setSchemaNamespace(string);
            }
        }
        catch (Exception ex) {}
    }
    
    private static void addTypesElement(final PGAppObj pgAppObj) {
        final Definition definitionObj = PGAppObj.getGenInfo().getWSDLGenInfo().getDefinitionObj();
        final DWGenInfo dwGenInfo = PGAppObj.getGenInfo().getDWGenInfo();
        try {
            final Types types = definitionObj.createTypes();
            if (dwGenInfo.getEncoding() == 1) {
                RPCEncodedType.buildTypesElement(pgAppObj, types);
            }
            else if (dwGenInfo.getEncoding() == 3) {
                DOCLiteralType.buildTypesElement(pgAppObj, types);
            }
            else if (dwGenInfo.getEncoding() == 2) {
                RPCLiteralType.buildTypesElement(pgAppObj, types);
            }
            definitionObj.setTypes(types);
        }
        catch (ClassCastException ex) {}
    }
    
    private static void addGenericMessages(final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        genInfo.getDWGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        try {
            wsdlGenInfo.setCurrentSchemaNamespace(wsdlGenInfo.getFaultNamespace());
            definitionObj.addMessage(WSDLMessageObj.buildGeneric("FaultDetail", "Message", wsdlGenInfo));
            if (!genInfo.getConnectionFree()) {
                wsdlGenInfo.setCurrentSchemaNamespace(pgAppObj.getSchemaNamespace());
                definitionObj.addMessage(WSDLMessageObj.buildGeneric(pgAppObj.getAppObjectName() + "ID", null, wsdlGenInfo));
            }
            if (pgAppObj.getSubObjects() != null) {
                for (int i = 0; i < pgAppObj.getSubObjects().length; ++i) {
                    final PGAppObj subObjects = pgAppObj.getSubObjects(i);
                    wsdlGenInfo.setCurrentSchemaNamespace(subObjects.getSchemaNamespace());
                    definitionObj.addMessage(WSDLMessageObj.buildGeneric(subObjects.getAppObjectName() + "ID", null, wsdlGenInfo));
                    addProcObjectGenericMessages(subObjects, genInfo);
                }
            }
            addProcObjectGenericMessages(pgAppObj, genInfo);
        }
        catch (ClassCastException ex) {}
    }
    
    private static void addProcObjectGenericMessages(final PGAppObj pgAppObj, final PGGenInfo pgGenInfo) {
        final WSDLGenInfo wsdlGenInfo = pgGenInfo.getWSDLGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        try {
            for (int i = 0; i < pgAppObj.getPersistentProcedures().length; ++i) {
                final PGProc persistentProcedures = pgAppObj.getPersistentProcedures(i);
                final PGProcDetail procDetail = persistentProcedures.getProcDetail();
                wsdlGenInfo.setCurrentSchemaNamespace(persistentProcedures.getSchemaNamespace());
                definitionObj.addMessage(WSDLMessageObj.buildGeneric(procDetail.getMethodName() + "ID", null, wsdlGenInfo));
            }
        }
        catch (Exception ex) {}
    }
    
    private static void addPortTypes(final PGAppObj pgAppObj) {
        final Definition definitionObj = PGAppObj.getGenInfo().getWSDLGenInfo().getDefinitionObj();
        try {
            definitionObj.addPortType(WSDLPortTypeObj.build(pgAppObj));
            if (pgAppObj.getSubObjects() != null) {
                for (int i = 0; i < pgAppObj.getSubObjects().length; ++i) {
                    addPortTypes(pgAppObj.getSubObjects(i));
                }
            }
            for (int j = 0; j < pgAppObj.getPersistentProcedures().length; ++j) {
                definitionObj.addPortType(WSDLPortTypeObj.build(pgAppObj, pgAppObj.getPersistentProcedures(j)));
            }
        }
        catch (ClassCastException ex) {}
    }
    
    private static void addBindings(final PGAppObj pgAppObj) {
        final Definition definitionObj = PGAppObj.getGenInfo().getWSDLGenInfo().getDefinitionObj();
        try {
            definitionObj.addBinding(WSDLBindingObj.build(pgAppObj));
            if (pgAppObj.getSubObjects() != null) {
                for (int i = 0; i < pgAppObj.getSubObjects().length; ++i) {
                    addBindings(pgAppObj.getSubObjects(i));
                }
            }
            for (int j = 0; j < pgAppObj.getPersistentProcedures().length; ++j) {
                definitionObj.addBinding(WSDLBindingObj.build(pgAppObj.getPersistentProcedures(j)));
            }
        }
        catch (ClassCastException ex) {}
    }
    
    private static void addService(Service service, final PGAppObj pgAppObj) {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.getGenInfo().getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        try {
            if (!pgAppObj.isSubAppObject()) {
                service = definitionObj.createService();
                String localPart;
                if (dwGenInfo.useServiceSuffix()) {
                    localPart = pgAppObj.getAppObjectName() + dwGenInfo.getServiceSuffix();
                }
                else {
                    localPart = pgAppObj.getAppObjectName() + "Service";
                }
                service.setQName(new QName(wsdlGenInfo.getTargetNamespace(), localPart));
            }
            service.addPort(WSDLPortObj.build(pgAppObj));
            if (pgAppObj.getSubObjects() != null) {
                for (int i = 0; i < pgAppObj.getSubObjects().length; ++i) {
                    addService(service, pgAppObj.getSubObjects(i));
                }
            }
            for (int j = 0; j < pgAppObj.getPersistentProcedures().length; ++j) {
                service.addPort(WSDLPortObj.build(pgAppObj.getPersistentProcedures(j)));
            }
            if (!pgAppObj.isSubAppObject()) {
                definitionObj.addService(service);
            }
        }
        catch (ClassCastException ex) {}
    }
    
    public static void out(final Definition definition, final Writer writer) {
        final WSDLWriterImpl wsdlWriterImpl = new WSDLWriterImpl();
        try {
            wsdlWriterImpl.writeWSDL(definition, writer);
        }
        catch (WSDLException ex) {}
    }
}
