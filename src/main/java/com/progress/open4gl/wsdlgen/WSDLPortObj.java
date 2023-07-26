// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGProcDetail;
import javax.wsdl.Definition;
import com.progress.open4gl.proxygen.PGProc;
import org.w3c.dom.Element;
import org.apache.xerces.dom.CoreDocumentImpl;
import com.progress.open4gl.proxygen.PGGenInfo;
import javax.wsdl.extensions.ExtensibilityElement;
import com.ibm.wsdl.extensions.soap.SOAPAddressImpl;
import org.w3c.dom.Node;
import javax.wsdl.Port;
import com.progress.open4gl.proxygen.PGAppObj;

public class WSDLPortObj
{
    public static Port build(final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        final DWGenInfo dwGenInfo = genInfo.getDWGenInfo();
        final Port port = wsdlGenInfo.getDefinitionObj().createPort();
        if (dwGenInfo.usePortTypeBindingSuffix()) {
            port.setName(pgAppObj.getAppObjectName() + dwGenInfo.getPortTypeBindingSuffix());
        }
        else {
            port.setName(pgAppObj.getAppObjectName() + "Obj");
        }
        port.setBinding(pgAppObj.getBindingObj());
        final Element element = coreDocImpl.createElement("wsdl:documentation");
        element.appendChild(coreDocImpl.createTextNode(pgAppObj.getHelpString()));
        port.setDocumentationElement(element);
        final SOAPAddressImpl soapAddressImpl = new SOAPAddressImpl();
        soapAddressImpl.setLocationURI(dwGenInfo.getSoapEndPointURL());
        port.addExtensibilityElement((ExtensibilityElement)soapAddressImpl);
        return port;
    }
    
    public static Port build(final PGProc pgProc) {
        final PGGenInfo genInfo = PGProc.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        final DWGenInfo dwGenInfo = genInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final PGProcDetail procDetail = pgProc.getProcDetail();
        final Port port = definitionObj.createPort();
        if (dwGenInfo.usePortTypeBindingSuffix()) {
            port.setName(procDetail.getMethodName() + dwGenInfo.getPortTypeBindingSuffix());
        }
        else {
            port.setName(procDetail.getMethodName() + "Obj");
        }
        port.setBinding(pgProc.getBindingObj());
        final Element element = coreDocImpl.createElement("wsdl:documentation");
        element.appendChild(coreDocImpl.createTextNode(procDetail.getHelpString()));
        port.setDocumentationElement(element);
        final SOAPAddressImpl soapAddressImpl = new SOAPAddressImpl();
        soapAddressImpl.setLocationURI(dwGenInfo.getSoapEndPointURL());
        port.addExtensibilityElement((ExtensibilityElement)soapAddressImpl);
        return port;
    }
}
