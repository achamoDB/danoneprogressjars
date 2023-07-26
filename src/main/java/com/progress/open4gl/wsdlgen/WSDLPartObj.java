// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGDataSetParam;
import com.progress.open4gl.proxygen.PGParam;
import javax.wsdl.Definition;
import javax.xml.namespace.QName;
import javax.wsdl.Part;

public class WSDLPartObj
{
    public static Part buildGeneric(final String localPart, final WSDLGenInfo wsdlGenInfo) {
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final String currentSchemaNamespace = wsdlGenInfo.getCurrentSchemaNamespace();
        final Part part = definitionObj.createPart();
        part.setName(localPart);
        if (dwGenInfo.getEncoding() == 1 || dwGenInfo.getEncoding() == 2) {
            part.setTypeName(new QName(currentSchemaNamespace, localPart));
        }
        else if (dwGenInfo.getEncoding() == 3) {
            part.setElementName(new QName(currentSchemaNamespace, localPart));
        }
        return part;
    }
    
    public static Part buildDocLiteral(final String localPart, final WSDLGenInfo wsdlGenInfo) {
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final String currentSchemaNamespace = wsdlGenInfo.getCurrentSchemaNamespace();
        final Part part = definitionObj.createPart();
        part.setName("parameters");
        part.setElementName(new QName(currentSchemaNamespace, localPart));
        return part;
    }
    
    public static Part buildRPC(final PGParam pgParam, final WSDLGenInfo wsdlGenInfo) {
        final Part part = wsdlGenInfo.getDefinitionObj().createPart();
        if (pgParam.getParamType() != 36) {
            part.setName(pgParam.getParamName());
        }
        else {
            final String xmlNodeName = ((PGDataSetParam)pgParam).getXmlNodeName();
            if (xmlNodeName != null) {
                part.setName(xmlNodeName);
            }
            else {
                part.setName(pgParam.getParamName());
            }
        }
        setRPCPartType(part, pgParam, wsdlGenInfo);
        return part;
    }
    
    public static Part buildRPC(final String s, final int paramType, final int paramMode, final WSDLGenInfo wsdlGenInfo) {
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final PGParam pgParam = new PGParam();
        pgParam.setParamName(s);
        pgParam.setParamType(paramType);
        pgParam.setParamMode(paramMode);
        final Part part = definitionObj.createPart();
        part.setName(s);
        setRPCPartType(part, pgParam, wsdlGenInfo);
        return part;
    }
    
    public static void setRPCPartType(final Part part, final PGParam pgParam, final WSDLGenInfo wsdlGenInfo) {
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        if (dwGenInfo.getEncoding() == 1) {
            proToRpcEncoded(part, pgParam, wsdlGenInfo);
        }
        else if (dwGenInfo.getEncoding() == 2) {
            proToRpcLiteral(part, pgParam, wsdlGenInfo);
        }
    }
    
    public static void proToRpcEncoded(final Part part, final PGParam pgParam, final WSDLGenInfo wsdlGenInfo) {
        final int paramType = pgParam.getParamType();
        String namespaceURI;
        String localPart;
        if (paramType == 17) {
            namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
            localPart = "TableHandleParam";
        }
        else if (paramType == 37) {
            namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
            if (pgParam.getWriteBeforeImage()) {
                localPart = "DataSetHandleChangesParam";
            }
            else {
                localPart = "DataSetHandleParam";
            }
        }
        else if (paramType == 15) {
            namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
            if (wsdlGenInfo.usePrefixForTempTables()) {
                localPart = "ArrayOf" + wsdlGenInfo.getTypePrefix() + "_" + pgParam.getParamName() + "Row";
            }
            else {
                localPart = "ArrayOf" + pgParam.getParamName() + "Row";
            }
        }
        else if (paramType == 36) {
            final PGDataSetParam pgDataSetParam = (PGDataSetParam)pgParam;
            if (pgParam.getWriteBeforeImage()) {
                namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
                localPart = pgDataSetParam.getClassName() + "Changes";
            }
            else {
                namespaceURI = pgDataSetParam.getXmlNamespace();
                if (namespaceURI == null) {
                    namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
                }
                localPart = pgDataSetParam.getClassName() + "Param";
            }
        }
        else if (pgParam.isExtentField()) {
            namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
            localPart = WSDLDataTypes.proToXMLArrayType(paramType);
        }
        else {
            namespaceURI = wsdlGenInfo.getXSDSchemaNamespace();
            localPart = WSDLDataTypes.proToXMLType(paramType);
        }
        part.setTypeName(new QName(namespaceURI, localPart));
    }
    
    public static void proToRpcLiteral(final Part part, final PGParam pgParam, final WSDLGenInfo wsdlGenInfo) {
        final int paramType = pgParam.getParamType();
        String namespaceURI;
        String localPart;
        if (paramType == 17) {
            namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
            localPart = "TableHandleParam";
        }
        else if (paramType == 37) {
            namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
            if (pgParam.getWriteBeforeImage()) {
                localPart = "DataSetHandleChangesParam";
            }
            else {
                localPart = "DataSetHandleParam";
            }
        }
        else if (paramType == 15) {
            namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
            if (wsdlGenInfo.usePrefixForTempTables()) {
                localPart = wsdlGenInfo.getTypePrefix() + "_" + pgParam.getParamName() + "Param";
            }
            else {
                localPart = pgParam.getParamName() + "Param";
            }
        }
        else if (paramType == 36) {
            final PGDataSetParam pgDataSetParam = (PGDataSetParam)pgParam;
            if (pgParam.getWriteBeforeImage()) {
                namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
                localPart = pgDataSetParam.getClassName() + "Changes";
            }
            else {
                namespaceURI = pgDataSetParam.getXmlNamespace();
                if (namespaceURI == null) {
                    namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
                }
                localPart = pgDataSetParam.getClassName() + "Param";
            }
        }
        else if (pgParam.isExtentField()) {
            namespaceURI = wsdlGenInfo.getCurrentSchemaNamespace();
            localPart = WSDLDataTypes.proToXMLArrayType(paramType);
        }
        else {
            namespaceURI = wsdlGenInfo.getXSDSchemaNamespace();
            localPart = WSDLDataTypes.proToXMLType(paramType);
        }
        part.setTypeName(new QName(namespaceURI, localPart));
    }
}
