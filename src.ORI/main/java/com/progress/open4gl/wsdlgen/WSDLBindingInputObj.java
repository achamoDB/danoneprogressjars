// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGProc;
import javax.wsdl.Definition;
import com.progress.open4gl.proxygen.PGGenInfo;
import com.ibm.wsdl.extensions.soap.SOAPBodyImpl;
import javax.wsdl.extensions.ExtensibilityElement;
import java.util.List;
import javax.xml.namespace.QName;
import com.ibm.wsdl.extensions.soap.SOAPHeaderImpl;
import java.util.Vector;
import javax.wsdl.BindingInput;
import com.progress.open4gl.proxygen.PGAppObj;

public class WSDLBindingInputObj
{
    public static BindingInput build(final String s, final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        wsdlGenInfo.getCurrentSchemaNamespace();
        final String string = pgAppObj.getAppObjectName() + "ID";
        boolean b = true;
        Vector<String> vector = null;
        final Boolean required = new Boolean("true");
        final BindingInput bindingInput = definitionObj.createBindingInput();
        final String bindingEncodingStyleAttr = wsdlGenInfo.getBindingEncodingStyleAttr();
        if (bindingEncodingStyleAttr != null) {
            vector = new Vector<String>();
            vector.add(bindingEncodingStyleAttr);
        }
        try {
            if (s == "connect") {
                b = false;
            }
            else if ((s == "createPO" || s == "createAO" || s == "run") && !pgAppObj.isSubAppObject() && genInfo.getConnectionFree()) {
                b = false;
            }
            if (b) {
                final SOAPHeaderImpl soapHeaderImpl = new SOAPHeaderImpl();
                soapHeaderImpl.setMessage(new QName(wsdlGenInfo.getTargetNamespace(), string));
                soapHeaderImpl.setPart(string);
                soapHeaderImpl.setRequired(required);
                soapHeaderImpl.setUse(wsdlGenInfo.getBindingUseAttr());
                if (dwGenInfo.getEncoding() == 1) {
                    soapHeaderImpl.setEncodingStyles((List)vector);
                }
                if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
                    soapHeaderImpl.setNamespaceURI(pgAppObj.getSchemaNamespace());
                }
                bindingInput.addExtensibilityElement((ExtensibilityElement)soapHeaderImpl);
            }
            final SOAPBodyImpl soapBodyImpl = new SOAPBodyImpl();
            soapBodyImpl.setUse(wsdlGenInfo.getBindingUseAttr());
            if (dwGenInfo.getEncoding() == 1) {
                soapBodyImpl.setEncodingStyles((List)vector);
            }
            if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
                soapBodyImpl.setNamespaceURI(pgAppObj.getSchemaNamespace());
            }
            bindingInput.addExtensibilityElement((ExtensibilityElement)soapBodyImpl);
        }
        catch (Exception ex) {}
        return bindingInput;
    }
    
    public static BindingInput build(final String s, final PGProc pgProc) {
        final WSDLGenInfo wsdlGenInfo = PGProc.getGenInfo().getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        wsdlGenInfo.getCurrentSchemaNamespace();
        final String string = pgProc.getProcDetail().getMethodName() + "ID";
        Vector<String> vector = null;
        final Boolean required = new Boolean("true");
        final BindingInput bindingInput = definitionObj.createBindingInput();
        final String bindingEncodingStyleAttr = wsdlGenInfo.getBindingEncodingStyleAttr();
        if (bindingEncodingStyleAttr != null) {
            vector = new Vector<String>();
            vector.add(bindingEncodingStyleAttr);
        }
        try {
            final SOAPHeaderImpl soapHeaderImpl = new SOAPHeaderImpl();
            soapHeaderImpl.setMessage(new QName(wsdlGenInfo.getTargetNamespace(), string));
            soapHeaderImpl.setPart(string);
            soapHeaderImpl.setRequired(required);
            soapHeaderImpl.setUse(wsdlGenInfo.getBindingUseAttr());
            if (dwGenInfo.getEncoding() == 1) {
                soapHeaderImpl.setEncodingStyles((List)vector);
            }
            if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
                soapHeaderImpl.setNamespaceURI(pgProc.getSchemaNamespace());
            }
            bindingInput.addExtensibilityElement((ExtensibilityElement)soapHeaderImpl);
            final SOAPBodyImpl soapBodyImpl = new SOAPBodyImpl();
            soapBodyImpl.setUse(wsdlGenInfo.getBindingUseAttr());
            if (dwGenInfo.getEncoding() == 1) {
                soapBodyImpl.setEncodingStyles((List)vector);
            }
            if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
                soapBodyImpl.setNamespaceURI(pgProc.getSchemaNamespace());
            }
            bindingInput.addExtensibilityElement((ExtensibilityElement)soapBodyImpl);
        }
        catch (Exception ex) {}
        return bindingInput;
    }
}
