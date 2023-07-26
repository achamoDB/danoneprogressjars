// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGProc;
import javax.wsdl.Definition;
import com.ibm.wsdl.extensions.soap.SOAPBodyImpl;
import javax.wsdl.extensions.ExtensibilityElement;
import java.util.List;
import javax.xml.namespace.QName;
import com.ibm.wsdl.extensions.soap.SOAPHeaderImpl;
import java.util.Vector;
import javax.wsdl.BindingOutput;
import com.progress.open4gl.proxygen.PGAppObj;

public class WSDLBindingOutputObj
{
    public static BindingOutput build(final String s, final String str, final PGAppObj pgAppObj) {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.getGenInfo().getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        boolean b = true;
        final String string = str + "ID";
        new StringBuffer().append(pgAppObj.getAppObjectName()).append("ID").toString();
        Vector<String> vector = null;
        final Boolean b2 = new Boolean("true");
        final BindingOutput bindingOutput = definitionObj.createBindingOutput();
        final String bindingEncodingStyleAttr = wsdlGenInfo.getBindingEncodingStyleAttr();
        if (bindingEncodingStyleAttr != null) {
            vector = new Vector<String>();
            vector.add(bindingEncodingStyleAttr);
        }
        if (s == "release" || s == "run") {
            b = false;
        }
        if (b) {
            final SOAPHeaderImpl soapHeaderImpl = new SOAPHeaderImpl();
            soapHeaderImpl.setMessage(new QName(wsdlGenInfo.getTargetNamespace(), string));
            soapHeaderImpl.setPart(string);
            soapHeaderImpl.setUse(wsdlGenInfo.getBindingUseAttr());
            if (dwGenInfo.getEncoding() == 1) {
                soapHeaderImpl.setEncodingStyles((List)vector);
            }
            if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
                soapHeaderImpl.setNamespaceURI(pgAppObj.getSchemaNamespace());
            }
            bindingOutput.addExtensibilityElement((ExtensibilityElement)soapHeaderImpl);
        }
        final SOAPBodyImpl soapBodyImpl = new SOAPBodyImpl();
        soapBodyImpl.setUse(wsdlGenInfo.getBindingUseAttr());
        if (dwGenInfo.getEncoding() == 1) {
            soapBodyImpl.setEncodingStyles((List)vector);
        }
        if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
            soapBodyImpl.setNamespaceURI(pgAppObj.getSchemaNamespace());
        }
        bindingOutput.addExtensibilityElement((ExtensibilityElement)soapBodyImpl);
        return bindingOutput;
    }
    
    public static BindingOutput build(final String s, final String s2, final PGProc pgProc) {
        final WSDLGenInfo wsdlGenInfo = PGProc.getGenInfo().getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        Vector<String> encodingStyles = null;
        final BindingOutput bindingOutput = definitionObj.createBindingOutput();
        final String bindingEncodingStyleAttr = wsdlGenInfo.getBindingEncodingStyleAttr();
        if (bindingEncodingStyleAttr != null) {
            encodingStyles = new Vector<String>();
            encodingStyles.add(bindingEncodingStyleAttr);
        }
        final SOAPBodyImpl soapBodyImpl = new SOAPBodyImpl();
        soapBodyImpl.setUse(wsdlGenInfo.getBindingUseAttr());
        if (dwGenInfo.getEncoding() == 1) {
            soapBodyImpl.setEncodingStyles((List)encodingStyles);
        }
        if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
            soapBodyImpl.setNamespaceURI(pgProc.getSchemaNamespace());
        }
        bindingOutput.addExtensibilityElement((ExtensibilityElement)soapBodyImpl);
        return bindingOutput;
    }
}
