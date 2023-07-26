// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGParam;
import javax.wsdl.Definition;
import com.progress.open4gl.proxygen.IPGDetail;
import javax.xml.namespace.QName;
import javax.wsdl.Message;

public class WSDLMessageObj
{
    public static Message buildGeneric(final String str, final String str2, final WSDLGenInfo wsdlGenInfo) {
        final Message message = wsdlGenInfo.getDefinitionObj().createMessage();
        String string;
        if (str2 != null) {
            string = str + str2;
        }
        else {
            string = str;
        }
        try {
            message.setQName(new QName(wsdlGenInfo.getTargetNamespace(), string));
            message.setUndefined(false);
            message.addPart(WSDLPartObj.buildGeneric(str, wsdlGenInfo));
        }
        catch (ClassCastException ex) {}
        return message;
    }
    
    public static Message buildInput(final String s, final String str, final WSDLGenInfo wsdlGenInfo) {
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Message message = wsdlGenInfo.getDefinitionObj().createMessage();
        final String messagePrefix = wsdlGenInfo.getMessagePrefix();
        try {
            String string;
            if (messagePrefix == null) {
                string = str;
            }
            else {
                string = messagePrefix + "_" + str;
            }
            String localPart;
            if (s == "connect") {
                localPart = string + "Connect";
            }
            else if (s == "release") {
                localPart = string + "Release";
            }
            else {
                localPart = string;
            }
            message.setQName(new QName(wsdlGenInfo.getTargetNamespace(), localPart));
            message.setUndefined(false);
            if (s == "connect") {
                if (dwGenInfo.getEncoding() == 3) {
                    message.addPart(WSDLPartObj.buildDocLiteral("Connect_" + string, wsdlGenInfo));
                }
                else {
                    wsdlGenInfo.setTypePrefix(wsdlGenInfo.getCurrentObjectName());
                    message.addPart(WSDLPartObj.buildRPC("userId", 1, 1, wsdlGenInfo));
                    message.addPart(WSDLPartObj.buildRPC("password", 1, 1, wsdlGenInfo));
                    message.addPart(WSDLPartObj.buildRPC("appServerInfo", 1, 1, wsdlGenInfo));
                }
            }
            else if (dwGenInfo.getEncoding() == 3) {
                String s2;
                if (s == "release") {
                    s2 = "Release_" + string;
                }
                else {
                    s2 = "CreateAO_" + string;
                }
                message.addPart(WSDLPartObj.buildDocLiteral(s2, wsdlGenInfo));
            }
        }
        catch (ClassCastException ex) {}
        return message;
    }
    
    public static Message buildInput(final String s, final IPGDetail ipgDetail, final WSDLGenInfo wsdlGenInfo) {
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final PGParam[] parameters = ipgDetail.getParameters();
        final Message message = definitionObj.createMessage();
        final String messagePrefix = wsdlGenInfo.getMessagePrefix();
        try {
            String localPart;
            if (messagePrefix == null) {
                localPart = ipgDetail.getMethodName();
            }
            else {
                localPart = messagePrefix + "_" + ipgDetail.getMethodName();
            }
            message.setQName(new QName(wsdlGenInfo.getTargetNamespace(), localPart));
            message.setUndefined(false);
            if (dwGenInfo.getEncoding() == 3) {
                String s2;
                if (s == "create") {
                    s2 = "CreatePO_" + ipgDetail.getMethodName();
                }
                else {
                    s2 = ipgDetail.getMethodName();
                }
                message.addPart(WSDLPartObj.buildDocLiteral(s2, wsdlGenInfo));
            }
            else {
                wsdlGenInfo.setTypePrefix(ipgDetail.getMethodName());
                for (int i = 0; i < parameters.length; ++i) {
                    final PGParam pgParam = parameters[i];
                    final int paramMode = pgParam.getParamMode();
                    if (paramMode == 1 || paramMode == 3) {
                        message.addPart(WSDLPartObj.buildRPC(pgParam, wsdlGenInfo));
                    }
                }
            }
        }
        catch (ClassCastException ex) {}
        return message;
    }
    
    public static Message buildOutput(final String s, final String str, final WSDLGenInfo wsdlGenInfo) {
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final String messagePrefix = wsdlGenInfo.getMessagePrefix();
        final Message message = definitionObj.createMessage();
        try {
            String string;
            if (messagePrefix == null) {
                string = str;
            }
            else {
                string = messagePrefix + "_" + str;
            }
            String localPart;
            if (s == "connect") {
                localPart = string + "ConnectResponse";
            }
            else if (s == "release") {
                localPart = string + "ReleaseResponse";
            }
            else {
                localPart = string + "Response";
            }
            message.setQName(new QName(wsdlGenInfo.getTargetNamespace(), localPart));
            message.setUndefined(false);
            if (dwGenInfo.getEncoding() == 3) {
                String str2;
                if (s == "connect") {
                    str2 = "Connect_" + string;
                }
                else if (s == "release") {
                    str2 = "Release_" + string;
                }
                else {
                    str2 = "CreateAO_" + string;
                }
                message.addPart(WSDLPartObj.buildDocLiteral(str2 + "Response", wsdlGenInfo));
            }
            else if (dwGenInfo.hasConnectReturnString() && s == "connect") {
                message.addPart(WSDLPartObj.buildRPC("result", 1, 2, wsdlGenInfo));
            }
        }
        catch (ClassCastException ex) {}
        return message;
    }
    
    public static Message buildOutput(final String s, final IPGDetail ipgDetail, final WSDLGenInfo wsdlGenInfo) {
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Message message = definitionObj.createMessage();
        final String messagePrefix = wsdlGenInfo.getMessagePrefix();
        try {
            String localPart;
            if (messagePrefix == null) {
                localPart = ipgDetail.getMethodName() + "Response";
            }
            else {
                localPart = messagePrefix + "_" + ipgDetail.getMethodName() + "Response";
            }
            message.setQName(new QName(wsdlGenInfo.getTargetNamespace(), localPart));
            message.setUndefined(false);
            if (dwGenInfo.getEncoding() == 3) {
                String str;
                if (s == "create") {
                    str = "CreatePO_" + ipgDetail.getMethodName();
                }
                else {
                    str = ipgDetail.getMethodName();
                }
                message.addPart(WSDLPartObj.buildDocLiteral(str + "Response", wsdlGenInfo));
            }
            else {
                wsdlGenInfo.setTypePrefix(ipgDetail.getMethodName());
                final PGParam returnValue = ipgDetail.getReturnValue();
                if (returnValue != null && (ipgDetail.getProcType() == 2 || ipgDetail.usesReturnValue())) {
                    message.addPart(WSDLPartObj.buildRPC(returnValue, wsdlGenInfo));
                }
                final PGParam[] parameters = ipgDetail.getParameters();
                for (int i = 0; i < parameters.length; ++i) {
                    final PGParam pgParam = parameters[i];
                    final int paramMode = pgParam.getParamMode();
                    if (paramMode == 2 || paramMode == 3) {
                        message.addPart(WSDLPartObj.buildRPC(pgParam, wsdlGenInfo));
                    }
                }
            }
        }
        catch (ClassCastException ex) {}
        return message;
    }
}
