// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import javax.wsdl.Definition;
import javax.wsdl.extensions.ExtensibilityElement;
import java.util.List;
import java.util.Vector;
import com.ibm.wsdl.extensions.soap.SOAPFaultImpl;
import javax.wsdl.BindingFault;

public class WSDLBindingFaultObj
{
    public static BindingFault build(final WSDLGenInfo wsdlGenInfo) {
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final BindingFault bindingFault = definitionObj.createBindingFault();
        bindingFault.setName(wsdlGenInfo.getCurrentObjectName() + "Fault");
        final SOAPFaultImpl soapFaultImpl = new SOAPFaultImpl();
        soapFaultImpl.setName(wsdlGenInfo.getCurrentObjectName() + "Fault");
        soapFaultImpl.setUse(wsdlGenInfo.getBindingUseAttr());
        if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
            soapFaultImpl.setNamespaceURI(dwGenInfo.getSoapEndPointURL());
        }
        if (dwGenInfo.getEncoding() == 1) {
            final String bindingEncodingStyleAttr = wsdlGenInfo.getBindingEncodingStyleAttr();
            if (bindingEncodingStyleAttr != null) {
                final Vector<String> encodingStyles = new Vector<String>();
                encodingStyles.add(bindingEncodingStyleAttr);
                soapFaultImpl.setEncodingStyles((List)encodingStyles);
            }
        }
        bindingFault.addExtensibilityElement((ExtensibilityElement)soapFaultImpl);
        return bindingFault;
    }
}
