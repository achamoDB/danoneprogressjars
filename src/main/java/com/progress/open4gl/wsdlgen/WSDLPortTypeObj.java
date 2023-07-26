// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGMethod;
import com.progress.open4gl.proxygen.PGProcDetail;
import javax.wsdl.Definition;
import com.progress.open4gl.proxygen.PGProc;
import com.progress.open4gl.proxygen.PGGenInfo;
import com.progress.open4gl.proxygen.IPGDetail;
import javax.xml.namespace.QName;
import javax.wsdl.PortType;
import com.progress.open4gl.proxygen.PGAppObj;

public class WSDLPortTypeObj
{
    public static PortType build(final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final PortType portType = wsdlGenInfo.getDefinitionObj().createPortType();
        String localPart;
        if (dwGenInfo.usePortTypeBindingSuffix()) {
            localPart = pgAppObj.getAppObjectName() + dwGenInfo.getPortTypeBindingSuffix();
        }
        else {
            localPart = pgAppObj.getAppObjectName() + "Obj";
        }
        final QName qName = new QName(wsdlGenInfo.getTargetNamespace(), localPart);
        portType.setUndefined(false);
        portType.setQName(qName);
        pgAppObj.setPortTypeObj(portType);
        wsdlGenInfo.setCurrentSchemaNamespace(pgAppObj.getSchemaNamespace());
        wsdlGenInfo.setMessagePrefix(null);
        wsdlGenInfo.setCurrentObjectName(pgAppObj.getAppObjectName());
        if (!genInfo.getConnectionFree() && !pgAppObj.isSubAppObject()) {
            portType.addOperation(WSDLOperationObj.build("connect", pgAppObj.getAppObjectName(), wsdlGenInfo));
            portType.addOperation(WSDLOperationObj.build("release", pgAppObj.getAppObjectName(), wsdlGenInfo));
        }
        if (pgAppObj.getSubObjects() != null) {
            for (int i = 0; i < pgAppObj.getSubObjects().length; ++i) {
                portType.addOperation(WSDLOperationObj.build("create", pgAppObj.getSubObjects(i).getAppObjectName(), wsdlGenInfo));
            }
        }
        if (pgAppObj.isSubAppObject()) {
            portType.addOperation(WSDLOperationObj.build("release", pgAppObj.getAppObjectName(), wsdlGenInfo));
        }
        for (int j = 0; j < pgAppObj.getPersistentProcedures().length; ++j) {
            portType.addOperation(WSDLOperationObj.build("create", pgAppObj.getPersistentProcedures(j).getProcDetail(), wsdlGenInfo));
        }
        wsdlGenInfo.setMessagePrefix(pgAppObj.getAppObjectName());
        for (int k = 0; k < pgAppObj.getProcedures().length; ++k) {
            portType.addOperation(WSDLOperationObj.build("run", pgAppObj.getProcedures(k).getProcDetail(), wsdlGenInfo));
        }
        return portType;
    }
    
    public static PortType build(final PGAppObj pgAppObj, final PGProc pgProc) {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.getGenInfo().getWSDLGenInfo();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final PGProcDetail procDetail = pgProc.getProcDetail();
        final PortType portType = definitionObj.createPortType();
        wsdlGenInfo.setCurrentSchemaNamespace(pgProc.getSchemaNamespace());
        wsdlGenInfo.setCurrentObjectName(procDetail.getMethodName());
        String localPart;
        if (dwGenInfo.usePortTypeBindingSuffix()) {
            localPart = procDetail.getMethodName() + dwGenInfo.getPortTypeBindingSuffix();
        }
        else {
            localPart = procDetail.getMethodName() + "Obj";
        }
        final QName qName = new QName(wsdlGenInfo.getTargetNamespace(), localPart);
        portType.setUndefined(false);
        portType.setQName(qName);
        pgProc.setPortTypeObj(portType);
        wsdlGenInfo.setMessagePrefix(null);
        portType.addOperation(WSDLOperationObj.build("release", procDetail.getMethodName(), wsdlGenInfo));
        wsdlGenInfo.setMessagePrefix(procDetail.getMethodName());
        wsdlGenInfo.setUsePrefixForTempTables(false);
        final PGMethod[] internalProcs = pgProc.getProcDetail().getInternalProcs();
        for (int i = 0; i < internalProcs.length; ++i) {
            final PGMethod pgMethod = internalProcs[i];
            if (!pgMethod.isExcluded()) {
                portType.addOperation(WSDLOperationObj.build("run", pgMethod.getMethodDetail(), wsdlGenInfo));
            }
        }
        wsdlGenInfo.setUsePrefixForTempTables(true);
        return portType;
    }
}
