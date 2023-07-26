// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGMethodDetail;
import com.progress.open4gl.proxygen.PGMethod;
import com.progress.open4gl.proxygen.PGProcDetail;
import com.progress.open4gl.proxygen.PGProc;
import javax.wsdl.PortType;
import javax.wsdl.Definition;
import com.progress.open4gl.proxygen.PGGenInfo;
import javax.wsdl.extensions.ExtensibilityElement;
import com.ibm.wsdl.extensions.soap.SOAPBindingImpl;
import javax.xml.namespace.QName;
import javax.wsdl.Binding;
import com.progress.open4gl.proxygen.PGAppObj;

public class WSDLBindingObj
{
    public static Binding build(final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final PortType portTypeObj = pgAppObj.getPortTypeObj();
        final Binding binding = definitionObj.createBinding();
        binding.setPortType(portTypeObj);
        try {
            String localPart;
            if (dwGenInfo.usePortTypeBindingSuffix()) {
                localPart = pgAppObj.getAppObjectName() + dwGenInfo.getPortTypeBindingSuffix();
            }
            else {
                localPart = pgAppObj.getAppObjectName() + "Obj";
            }
            binding.setQName(new QName(wsdlGenInfo.getTargetNamespace(), localPart));
            binding.setUndefined(false);
            pgAppObj.setBindingObj(binding);
            wsdlGenInfo.setCurrentSchemaNamespace(pgAppObj.getSchemaNamespace());
            wsdlGenInfo.setCurrentObjectName(pgAppObj.getAppObjectName());
            wsdlGenInfo.setCurrentPortTypeObj(portTypeObj);
            final SOAPBindingImpl soapBindingImpl = new SOAPBindingImpl();
            soapBindingImpl.setStyle(wsdlGenInfo.getBindingStyleAttr());
            soapBindingImpl.setTransportURI(wsdlGenInfo.getBindingTransportAttr());
            binding.addExtensibilityElement((ExtensibilityElement)soapBindingImpl);
            if (!genInfo.getConnectionFree() && !pgAppObj.isSubAppObject()) {
                binding.addBindingOperation(WSDLBindingOpObj.build("connect", pgAppObj.getAppObjectName(), pgAppObj));
                binding.addBindingOperation(WSDLBindingOpObj.build("release", pgAppObj.getAppObjectName(), pgAppObj));
            }
            if (!pgAppObj.isSubAppObject()) {
                for (int i = 0; i < pgAppObj.getSubObjects().length; ++i) {
                    binding.addBindingOperation(WSDLBindingOpObj.build("createAO", pgAppObj.getSubObjects(i).getAppObjectName(), pgAppObj));
                }
            }
            if (pgAppObj.isSubAppObject()) {
                binding.addBindingOperation(WSDLBindingOpObj.build("release", pgAppObj.getAppObjectName(), pgAppObj));
            }
            for (int j = 0; j < pgAppObj.getPersistentProcedures().length; ++j) {
                binding.addBindingOperation(WSDLBindingOpObj.build("createPO", pgAppObj.getPersistentProcedures(j).getProcDetail().getMethodName(), pgAppObj));
            }
            for (int k = 0; k < pgAppObj.getProcedures().length; ++k) {
                binding.addBindingOperation(WSDLBindingOpObj.build("run", pgAppObj.getProcedures(k).getProcDetail().getMethodName(), pgAppObj));
            }
        }
        catch (Exception ex) {}
        return binding;
    }
    
    public static Binding build(final PGProc pgProc) {
        final WSDLGenInfo wsdlGenInfo = PGProc.getGenInfo().getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final PortType portTypeObj = pgProc.getPortTypeObj();
        final PGProcDetail procDetail = pgProc.getProcDetail();
        final Binding binding = definitionObj.createBinding();
        binding.setPortType(portTypeObj);
        try {
            String localPart;
            if (dwGenInfo.usePortTypeBindingSuffix()) {
                localPart = procDetail.getMethodName() + dwGenInfo.getPortTypeBindingSuffix();
            }
            else {
                localPart = procDetail.getMethodName() + "Obj";
            }
            binding.setQName(new QName(wsdlGenInfo.getTargetNamespace(), localPart));
            binding.setUndefined(false);
            pgProc.setBindingObj(binding);
            wsdlGenInfo.setCurrentSchemaNamespace(pgProc.getSchemaNamespace());
            wsdlGenInfo.setCurrentObjectName(procDetail.getMethodName());
            wsdlGenInfo.setCurrentPortTypeObj(portTypeObj);
            final SOAPBindingImpl soapBindingImpl = new SOAPBindingImpl();
            soapBindingImpl.setStyle(wsdlGenInfo.getBindingStyleAttr());
            soapBindingImpl.setTransportURI(wsdlGenInfo.getBindingTransportAttr());
            binding.addExtensibilityElement((ExtensibilityElement)soapBindingImpl);
            binding.addBindingOperation(WSDLBindingOpObj.build("release", procDetail.getMethodName(), pgProc));
            final PGMethod[] internalProcs = pgProc.getProcDetail().getInternalProcs();
            for (int i = 0; i < internalProcs.length; ++i) {
                final PGMethod pgMethod = internalProcs[i];
                final PGMethodDetail methodDetail = pgMethod.getMethodDetail();
                if (!pgMethod.isExcluded()) {
                    binding.addBindingOperation(WSDLBindingOpObj.build("run", methodDetail.getMethodName(), pgProc));
                }
            }
        }
        catch (Exception ex) {}
        return binding;
    }
}
