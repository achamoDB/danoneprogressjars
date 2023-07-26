// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import org.w3c.dom.Element;
import org.apache.xerces.dom.CoreDocumentImpl;
import com.progress.open4gl.proxygen.PGParam;
import org.w3c.dom.Node;
import com.progress.open4gl.proxygen.PGDataSetParam;
import com.progress.open4gl.proxygen.IPGDetail;
import javax.wsdl.Fault;
import javax.wsdl.Definition;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.Vector;
import javax.wsdl.Operation;

public class WSDLOperationObj
{
    public static Operation build(final String s, final String str, final WSDLGenInfo wsdlGenInfo) {
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final Operation operation = definitionObj.createOperation();
        operation.setUndefined(false);
        if (s == "connect") {
            operation.setName("Connect_" + str);
            if (dwGenInfo.getEncoding() != 3) {
                final Vector<String> parameterOrdering = new Vector<String>();
                parameterOrdering.add("userId");
                parameterOrdering.add("password");
                parameterOrdering.add("appServerInfo");
                operation.setParameterOrdering((List)parameterOrdering);
            }
        }
        else if (s == "create") {
            operation.setName("CreateAO_" + str);
        }
        else {
            operation.setName("Release_" + str);
        }
        operation.setInput(WSDLInputObj.build(s, str, wsdlGenInfo));
        operation.setOutput(WSDLOutputObj.build(s, str, wsdlGenInfo));
        final Fault fault = definitionObj.createFault();
        fault.setName(wsdlGenInfo.getCurrentObjectName() + "Fault");
        fault.setMessage(definitionObj.getMessage(new QName(wsdlGenInfo.getTargetNamespace(), "FaultDetailMessage")));
        operation.addFault(fault);
        return operation;
    }
    
    public static Operation build(final String s, final IPGDetail ipgDetail, final WSDLGenInfo wsdlGenInfo) {
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final String helpString = ipgDetail.getHelpString();
        final Operation operation = definitionObj.createOperation();
        operation.setUndefined(false);
        if (s == "run") {
            operation.setName(ipgDetail.getMethodName());
        }
        else {
            operation.setName("CreatePO_" + ipgDetail.getMethodName());
        }
        if (dwGenInfo.getEncoding() != 3) {
            final Vector<String> parameterOrdering = new Vector<String>();
            final PGParam[] parameters = ipgDetail.getParameters();
            for (int i = 0; i < parameters.length; ++i) {
                if (parameters[i].getParamType() != 36) {
                    parameterOrdering.add(parameters[i].getParamName());
                }
                else {
                    String e = ((PGDataSetParam)parameters[i]).getXmlNodeName();
                    if (e == null) {
                        e = parameters[i].getParamName();
                    }
                    parameterOrdering.add(e);
                }
            }
            operation.setParameterOrdering((List)parameterOrdering);
        }
        operation.setInput(WSDLInputObj.build(s, ipgDetail, wsdlGenInfo));
        operation.setOutput(WSDLOutputObj.build(s, ipgDetail, wsdlGenInfo));
        final Fault fault = definitionObj.createFault();
        fault.setName(wsdlGenInfo.getCurrentObjectName() + "Fault");
        fault.setMessage(definitionObj.getMessage(new QName(wsdlGenInfo.getTargetNamespace(), "FaultDetailMessage")));
        operation.addFault(fault);
        if (helpString != null && helpString.length() > 0) {
            final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
            final Element element = coreDocImpl.createElement("wsdl:documentation");
            element.appendChild(coreDocImpl.createTextNode(helpString));
            operation.setDocumentationElement(element);
        }
        return operation;
    }
}
