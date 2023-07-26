// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGProcDetail;
import com.progress.open4gl.proxygen.PGProc;
import javax.wsdl.PortType;
import javax.wsdl.Definition;
import com.progress.open4gl.proxygen.PGGenInfo;
import javax.wsdl.extensions.ExtensibilityElement;
import com.ibm.wsdl.extensions.soap.SOAPOperationImpl;
import javax.wsdl.BindingOperation;
import com.progress.open4gl.proxygen.PGAppObj;

public class WSDLBindingOpObj
{
    public static BindingOperation build(final String s, final String s2, final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = genInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final PortType currentPortTypeObj = wsdlGenInfo.getCurrentPortTypeObj();
        final BindingOperation bindingOperation = definitionObj.createBindingOperation();
        String name;
        if (s == "connect") {
            name = "Connect_" + s2;
        }
        else if (s == "release") {
            name = "Release_" + s2;
        }
        else if (s == "createPO") {
            name = "CreatePO_" + s2;
        }
        else if (s == "createAO") {
            name = "CreateAO_" + s2;
        }
        else {
            name = s2;
        }
        bindingOperation.setName(name);
        bindingOperation.setOperation(currentPortTypeObj.getOperation(name, (String)null, (String)null));
        final SOAPOperationImpl soapOperationImpl = new SOAPOperationImpl();
        if (dwGenInfo.useUserDefinedSoapAction()) {
            if (dwGenInfo.appendToSoapAction()) {
                soapOperationImpl.setSoapActionURI(dwGenInfo.getSoapAction() + "/" + pgAppObj.getAppObjectName());
            }
            else {
                soapOperationImpl.setSoapActionURI(dwGenInfo.getSoapAction());
            }
        }
        else {
            soapOperationImpl.setSoapActionURI("");
        }
        soapOperationImpl.setStyle(wsdlGenInfo.getBindingStyleAttr());
        bindingOperation.addExtensibilityElement((ExtensibilityElement)soapOperationImpl);
        bindingOperation.setBindingInput(WSDLBindingInputObj.build(s, pgAppObj));
        bindingOperation.setBindingOutput(WSDLBindingOutputObj.build(s, s2, pgAppObj));
        bindingOperation.addBindingFault(WSDLBindingFaultObj.build(wsdlGenInfo));
        return bindingOperation;
    }
    
    public static BindingOperation build(final String s, final String str, final PGProc pgProc) {
        final PGGenInfo genInfo = PGProc.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = genInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final PGProcDetail procDetail = pgProc.getProcDetail();
        final PortType currentPortTypeObj = wsdlGenInfo.getCurrentPortTypeObj();
        final BindingOperation bindingOperation = definitionObj.createBindingOperation();
        String string;
        if (s == "release") {
            string = "Release_" + str;
        }
        else {
            string = str;
        }
        bindingOperation.setName(string);
        bindingOperation.setOperation(currentPortTypeObj.getOperation(string, (String)null, (String)null));
        final SOAPOperationImpl soapOperationImpl = new SOAPOperationImpl();
        if (dwGenInfo.useUserDefinedSoapAction()) {
            if (dwGenInfo.appendToSoapAction()) {
                soapOperationImpl.setSoapActionURI(dwGenInfo.getSoapAction() + "/" + procDetail.getMethodName());
            }
            else {
                soapOperationImpl.setSoapActionURI(dwGenInfo.getSoapAction());
            }
        }
        else {
            soapOperationImpl.setSoapActionURI("");
        }
        soapOperationImpl.setStyle(wsdlGenInfo.getBindingStyleAttr());
        bindingOperation.addExtensibilityElement((ExtensibilityElement)soapOperationImpl);
        bindingOperation.setBindingInput(WSDLBindingInputObj.build(s, pgProc));
        bindingOperation.setBindingOutput(WSDLBindingOutputObj.build(s, str, pgProc));
        bindingOperation.addBindingFault(WSDLBindingFaultObj.build(wsdlGenInfo));
        return bindingOperation;
    }
}
