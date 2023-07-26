// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGMetaData;
import com.progress.open4gl.proxygen.PGDataSetParam;
import com.progress.open4gl.proxygen.PGParam;
import com.progress.open4gl.proxygen.PGMethodDetail;
import com.progress.open4gl.proxygen.PGMethod;
import com.progress.open4gl.proxygen.PGProcDetail;
import com.progress.open4gl.proxygen.PGProc;
import com.progress.open4gl.proxygen.PGGenInfo;
import com.progress.open4gl.proxygen.IPGDetail;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.w3c.dom.Node;
import java.util.Vector;
import org.w3c.dom.Element;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.Types;
import com.progress.open4gl.proxygen.PGAppObj;

public class DOCLiteralType
{
    public static void buildTypesElement(final PGAppObj pgAppObj, final Types types) {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.getGenInfo().getWSDLGenInfo();
        try {
            if (!pgAppObj.isSubAppObject()) {
                final Element buildFaultDetailSchemaElement = buildFaultDetailSchemaElement(pgAppObj);
                final ProTypesExt proTypesExt = new ProTypesExt();
                proTypesExt.setSchemaDefinition(buildFaultDetailSchemaElement);
                types.addExtensibilityElement((ExtensibilityElement)proTypesExt);
            }
            final Element buildSchemaElement = buildSchemaElement(pgAppObj);
            final ProTypesExt proTypesExt2 = new ProTypesExt();
            proTypesExt2.setSchemaDefinition(buildSchemaElement);
            types.addExtensibilityElement((ExtensibilityElement)proTypesExt2);
            if (pgAppObj.getSubObjects() != null) {
                for (int i = 0; i < pgAppObj.getSubObjects().length; ++i) {
                    buildTypesElement(pgAppObj.getSubObjects(i), types);
                }
            }
            for (int j = 0; j < pgAppObj.getPersistentProcedures().length; ++j) {
                final Element buildSchemaElement2 = buildSchemaElement(pgAppObj.getPersistentProcedures(j));
                final ProTypesExt proTypesExt3 = new ProTypesExt();
                proTypesExt3.setSchemaDefinition(buildSchemaElement2);
                types.addExtensibilityElement((ExtensibilityElement)proTypesExt3);
            }
            final Vector dataSetNspaceNames = wsdlGenInfo.getDataSetNspaceNames();
            final Vector dataSetNspacePrefixes = wsdlGenInfo.getDataSetNspacePrefixes();
            for (int k = 0; k < dataSetNspaceNames.size(); ++k) {
                final Element buildDataSetSchemaElement = WSDLDataSetTypes.buildDataSetSchemaElement(dataSetNspaceNames.elementAt(k), dataSetNspacePrefixes.elementAt(k), wsdlGenInfo);
                final ProTypesExt proTypesExt4 = new ProTypesExt();
                proTypesExt4.setSchemaDefinition(buildDataSetSchemaElement);
                types.addExtensibilityElement((ExtensibilityElement)proTypesExt4);
            }
        }
        catch (ClassCastException ex) {}
    }
    
    private static Element buildFaultDetailSchemaElement(final PGAppObj pgAppObj) {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.getGenInfo().getWSDLGenInfo();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("schema");
            element.setAttribute("targetNamespace", wsdlGenInfo.getFaultNamespace());
            element.setAttribute("xmlns", wsdlGenInfo.getXSDSchemaNamespace());
            element.setAttribute("elementFormDefault", "unqualified");
            element.appendChild(buildFaultDetailElement(wsdlGenInfo));
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildFaultDetailElement(final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("element");
            element.setAttribute("name", "FaultDetail");
            final Element element2 = coreDocImpl.createElement("complexType");
            final Element element3 = coreDocImpl.createElement("sequence");
            final Element element4 = coreDocImpl.createElement("element");
            element4.setAttribute("name", "errorMessage");
            element4.setAttribute("type", "xsd:string");
            element3.appendChild(element4);
            final Element element5 = coreDocImpl.createElement("element");
            element5.setAttribute("name", "requestID");
            element5.setAttribute("type", "xsd:string");
            element3.appendChild(element5);
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildEmptyElement(final String s, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("element");
            element.setAttribute("name", s);
            final Element element2 = coreDocImpl.createElement("complexType");
            element2.appendChild(coreDocImpl.createElement("sequence"));
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildReturnOnlyElement(final String s, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("element");
            element.setAttribute("name", s);
            final Element element2 = coreDocImpl.createElement("complexType");
            final Element element3 = coreDocImpl.createElement("sequence");
            final Element element4 = coreDocImpl.createElement("element");
            element4.setAttribute("name", "result");
            element4.setAttribute("type", "xsd:string");
            element4.setAttribute("nillable", "true");
            element3.appendChild(element4);
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildGenericTableHandleComplexType(final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", "TableHandleParam");
            final Element element2 = coreDocImpl.createElement("sequence");
            final Element element3 = coreDocImpl.createElement("any");
            element3.setAttribute("namespace", "##local");
            element2.appendChild(element3);
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildSchemaElement(final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final DWGenInfo dwGenInfo = genInfo.getDWGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            wsdlGenInfo.setCurrentSchemaPrefix(pgAppObj.getSchemaPrefix());
            wsdlGenInfo.setCurrentSchemaNamespace(pgAppObj.getSchemaNamespace());
            element = coreDocImpl.createElement("schema");
            element.setAttribute("targetNamespace", pgAppObj.getSchemaNamespace());
            element.setAttribute("xmlns", wsdlGenInfo.getXSDSchemaNamespace());
            element.setAttribute("elementFormDefault", "qualified");
            if ((!genInfo.getConnectionFree() && !pgAppObj.isSubAppObject()) || pgAppObj.isSubAppObject()) {
                element.appendChild(buildObjectIDElement(pgAppObj.getAppObjectName(), wsdlGenInfo));
            }
            if (!genInfo.getConnectionFree() && !pgAppObj.isSubAppObject()) {
                element.appendChild(buildConnectElement(pgAppObj.getAppObjectName(), wsdlGenInfo));
                Element element2;
                if (dwGenInfo.hasConnectReturnString()) {
                    element2 = buildReturnOnlyElement("Connect_" + pgAppObj.getAppObjectName() + "Response", wsdlGenInfo);
                }
                else {
                    element2 = buildEmptyElement("Connect_" + pgAppObj.getAppObjectName() + "Response", wsdlGenInfo);
                }
                element.appendChild(element2);
            }
            if ((!genInfo.getConnectionFree() && !pgAppObj.isSubAppObject()) || pgAppObj.isSubAppObject()) {
                element.appendChild(buildEmptyElement("Release_" + pgAppObj.getAppObjectName(), wsdlGenInfo));
                element.appendChild(buildEmptyElement("Release_" + pgAppObj.getAppObjectName() + "Response", wsdlGenInfo));
            }
            if (pgAppObj.hasParamType(17, false)) {
                element.appendChild(buildGenericTableHandleComplexType(wsdlGenInfo));
            }
            if (pgAppObj.hasDatasetHandleParam()) {
                element.appendChild(WSDLDataSetTypes.buildGenericDataSetHandleComplexType(wsdlGenInfo));
            }
            if (pgAppObj.hasDatasetHandleChangesParam()) {
                element.appendChild(WSDLDataSetTypes.buildGenericDataSetHandleChangesComplexType(wsdlGenInfo));
            }
            wsdlGenInfo.setDataSetsCreatedList(new Vector());
            wsdlGenInfo.setDataSetChangesCreatedList(new Vector());
            wsdlGenInfo.setNamespacesImportedList(new Vector());
            wsdlGenInfo.setUniqueIndexNames(new Vector());
            wsdlGenInfo.setKeyRefNames(new Vector());
            for (int i = 0; i < pgAppObj.getProcedures().length; ++i) {
                final PGProc procedures = pgAppObj.getProcedures(i);
                buildTempTableComplexTypesForProc(procedures.getProcDetail(), element, false, wsdlGenInfo);
                WSDLDataSetTypes.buildDataSetComplexTypesForProc(procedures.getProcDetail(), element, wsdlGenInfo);
            }
            for (int j = 0; j < pgAppObj.getPersistentProcedures().length; ++j) {
                final PGProc persistentProcedures = pgAppObj.getPersistentProcedures(j);
                if (!pgAppObj.containsProc(persistentProcedures)) {
                    buildTempTableComplexTypesForProc(persistentProcedures.getProcDetail(), element, false, wsdlGenInfo);
                    WSDLDataSetTypes.buildDataSetComplexTypesForProc(persistentProcedures.getProcDetail(), element, wsdlGenInfo);
                }
            }
            wsdlGenInfo.setDataSetsCreatedList(null);
            wsdlGenInfo.setDataSetChangesCreatedList(null);
            wsdlGenInfo.setNamespacesImportedList(null);
            wsdlGenInfo.setUniqueIndexNames(null);
            wsdlGenInfo.setKeyRefNames(null);
            for (int k = 0; k < pgAppObj.getProcedures().length; ++k) {
                final PGProcDetail procDetail = pgAppObj.getProcedures(k).getProcDetail();
                element.appendChild(buildMethodElement(procDetail.getMethodName(), procDetail, wsdlGenInfo));
                element.appendChild(buildMethodResponseElement(procDetail.getMethodName(), procDetail, wsdlGenInfo));
            }
            if (pgAppObj.getSubObjects() != null) {
                for (int l = 0; l < pgAppObj.getSubObjects().length; ++l) {
                    final PGAppObj subObjects = pgAppObj.getSubObjects(l);
                    element.appendChild(buildEmptyElement("CreateAO_" + subObjects.getAppObjectName(), wsdlGenInfo));
                    element.appendChild(buildEmptyElement("CreateAO_" + subObjects.getAppObjectName() + "Response", wsdlGenInfo));
                }
            }
            for (int n = 0; n < pgAppObj.getPersistentProcedures().length; ++n) {
                final PGProcDetail procDetail2 = pgAppObj.getPersistentProcedures(n).getProcDetail();
                element.appendChild(buildMethodElement("CreatePO_" + procDetail2.getMethodName(), procDetail2, wsdlGenInfo));
                element.appendChild(buildMethodResponseElement("CreatePO_" + procDetail2.getMethodName(), procDetail2, wsdlGenInfo));
            }
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildSchemaElement(final PGProc pgProc) {
        final WSDLGenInfo wsdlGenInfo = PGProc.getGenInfo().getWSDLGenInfo();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        final PGProcDetail procDetail = pgProc.getProcDetail();
        final PGMethod[] internalProcs = procDetail.getInternalProcs();
        Element element = null;
        try {
            wsdlGenInfo.setCurrentSchemaPrefix(pgProc.getSchemaPrefix());
            wsdlGenInfo.setCurrentSchemaNamespace(pgProc.getSchemaNamespace());
            element = coreDocImpl.createElement("schema");
            element.setAttribute("targetNamespace", pgProc.getSchemaNamespace());
            element.setAttribute("xmlns", wsdlGenInfo.getXSDSchemaNamespace());
            element.setAttribute("elementFormDefault", "qualified");
            element.appendChild(buildObjectIDElement(procDetail.getMethodName(), wsdlGenInfo));
            element.appendChild(buildEmptyElement("Release_" + procDetail.getMethodName(), wsdlGenInfo));
            element.appendChild(buildEmptyElement("Release_" + procDetail.getMethodName() + "Response", wsdlGenInfo));
            if (pgProc.hasParamType(17, true)) {
                element.appendChild(buildGenericTableHandleComplexType(wsdlGenInfo));
            }
            if (pgProc.hasDatasetHandleParam(true)) {
                element.appendChild(WSDLDataSetTypes.buildGenericDataSetHandleComplexType(wsdlGenInfo));
            }
            if (pgProc.hasDatasetHandleChangesParam(true)) {
                element.appendChild(WSDLDataSetTypes.buildGenericDataSetHandleChangesComplexType(wsdlGenInfo));
            }
            if (pgProc.hasParamType(15, true)) {
                wsdlGenInfo.setTempTableList(new Vector());
                for (int i = 0; i < internalProcs.length; ++i) {
                    final PGMethod pgMethod = internalProcs[i];
                    if (!pgMethod.isExcluded()) {
                        buildTempTableComplexTypesForProc(pgMethod.getMethodDetail(), element, true, wsdlGenInfo);
                    }
                }
                wsdlGenInfo.setTempTableList(null);
            }
            if (pgProc.hasParamType(36, true)) {
                wsdlGenInfo.setDataSetsCreatedList(new Vector());
                wsdlGenInfo.setDataSetChangesCreatedList(new Vector());
                wsdlGenInfo.setNamespacesImportedList(new Vector());
                wsdlGenInfo.setUniqueIndexNames(new Vector());
                wsdlGenInfo.setKeyRefNames(new Vector());
                for (int j = 0; j < internalProcs.length; ++j) {
                    final PGMethod pgMethod2 = internalProcs[j];
                    if (!pgMethod2.isExcluded()) {
                        WSDLDataSetTypes.buildDataSetComplexTypesForProc(pgMethod2.getMethodDetail(), element, wsdlGenInfo);
                    }
                }
                wsdlGenInfo.setDataSetsCreatedList(null);
                wsdlGenInfo.setDataSetChangesCreatedList(null);
                wsdlGenInfo.setNamespacesImportedList(null);
                wsdlGenInfo.setUniqueIndexNames(null);
                wsdlGenInfo.setKeyRefNames(null);
            }
            wsdlGenInfo.setUsePrefixForTempTables(false);
            for (int k = 0; k < internalProcs.length; ++k) {
                final PGMethod pgMethod3 = internalProcs[k];
                final PGMethodDetail methodDetail = pgMethod3.getMethodDetail();
                if (!pgMethod3.isExcluded()) {
                    element.appendChild(buildMethodElement(methodDetail.getMethodName(), methodDetail, wsdlGenInfo));
                    element.appendChild(buildMethodResponseElement(methodDetail.getMethodName(), methodDetail, wsdlGenInfo));
                }
            }
            wsdlGenInfo.setUsePrefixForTempTables(true);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static void buildTempTableComplexTypesForProc(final IPGDetail ipgDetail, final Element element, final boolean b, final WSDLGenInfo wsdlGenInfo) {
        final PGParam[] parameters = ipgDetail.getParameters();
        final String methodName = ipgDetail.getMethodName();
        Vector<String> tempTableList = null;
        if (b) {
            tempTableList = (Vector<String>)wsdlGenInfo.getTempTableList();
        }
        for (int i = 0; i < parameters.length; ++i) {
            final PGParam pgParam = parameters[i];
            if (pgParam.getParamType() == 15) {
                boolean b2 = true;
                if (b && tempTableList.contains(pgParam.getParamName())) {
                    b2 = false;
                }
                if (b2) {
                    element.appendChild(buildRowComplexType(methodName, pgParam, !b, wsdlGenInfo));
                    element.appendChild(buildTableComplexType(methodName, pgParam, !b, wsdlGenInfo));
                    if (b) {
                        tempTableList.add(pgParam.getParamName());
                    }
                }
            }
        }
    }
    
    private static Element buildConnectElement(final String str, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("element");
            element.setAttribute("name", "Connect_" + str);
            final Element element2 = coreDocImpl.createElement("complexType");
            final Element element3 = coreDocImpl.createElement("sequence");
            final Element element4 = coreDocImpl.createElement("element");
            element4.setAttribute("name", "userId");
            element4.setAttribute("type", "xsd:string");
            element4.setAttribute("nillable", "true");
            element3.appendChild(element4);
            final Element element5 = coreDocImpl.createElement("element");
            element5.setAttribute("name", "password");
            element5.setAttribute("type", "xsd:string");
            element5.setAttribute("nillable", "true");
            element3.appendChild(element5);
            final Element element6 = coreDocImpl.createElement("element");
            element6.setAttribute("name", "appServerInfo");
            element6.setAttribute("type", "xsd:string");
            element6.setAttribute("nillable", "true");
            element3.appendChild(element6);
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildObjectIDElement(final String str, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("element");
            element.setAttribute("name", str + "ID");
            final Element element2 = coreDocImpl.createElement("complexType");
            final Element element3 = coreDocImpl.createElement("sequence");
            final Element element4 = coreDocImpl.createElement("element");
            element4.setAttribute("name", "UUID");
            element4.setAttribute("type", "xsd:string");
            element3.appendChild(element4);
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildMethodElement(final String s, final IPGDetail ipgDetail, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        final PGParam[] parameters = ipgDetail.getParameters();
        int n = 0;
        try {
            element = coreDocImpl.createElement("element");
            element.setAttribute("name", s);
            final Element element2 = coreDocImpl.createElement("complexType");
            final Element element3 = coreDocImpl.createElement("sequence");
            for (int i = 0; i < parameters.length; ++i) {
                final PGParam pgParam = parameters[i];
                final int paramMode = pgParam.getParamMode();
                final int paramType = pgParam.getParamType();
                if (paramMode == 1 || paramMode == 3) {
                    String str = wsdlGenInfo.getCurrentSchemaPrefix() + ":";
                    String s2 = null;
                    switch (paramType) {
                        case 15: {
                            if (wsdlGenInfo.usePrefixForTempTables()) {
                                s2 = ipgDetail.getMethodName() + "_" + pgParam.getParamName() + "Param";
                                break;
                            }
                            s2 = pgParam.getParamName() + "Param";
                            break;
                        }
                        case 17: {
                            s2 = "TableHandleParam";
                            break;
                        }
                        case 36: {
                            final PGDataSetParam pgDataSetParam = (PGDataSetParam)pgParam;
                            s2 = pgDataSetParam.getXmlNodeName();
                            if (s2 == null) {
                                s2 = pgDataSetParam.getClassName();
                            }
                            if (pgParam.getWriteBeforeImage()) {
                                n = 1;
                                break;
                            }
                            str = pgDataSetParam.getXmlNamespacePrefix() + ":";
                            break;
                        }
                        case 37: {
                            if (pgParam.getWriteBeforeImage()) {
                                s2 = "DataSetHandleChangesParam";
                                break;
                            }
                            s2 = "DataSetHandleParam";
                            break;
                        }
                        default: {
                            str = "xsd:";
                            s2 = WSDLDataTypes.proToXMLType(pgParam.getParamType());
                            break;
                        }
                    }
                    final Element element4 = coreDocImpl.createElement("element");
                    if (paramType != 36) {
                        element4.setAttribute("name", pgParam.getParamName());
                        element4.setAttribute("type", str + s2);
                    }
                    else if (n == 1) {
                        final PGDataSetParam pgDataSetParam2 = (PGDataSetParam)pgParam;
                        element4.setAttribute("name", s2);
                        element4.setAttribute("type", str + (pgDataSetParam2.getClassName() + "Changes"));
                    }
                    else {
                        element4.setAttribute("ref", str + s2);
                    }
                    if (pgParam.isExtentField()) {
                        final int extent = pgParam.getExtent();
                        final String string = Integer.toString(extent);
                        element4.setAttribute("minOccurs", string);
                        if (extent == 0) {
                            element4.setAttribute("maxOccurs", "unbounded");
                        }
                        else {
                            element4.setAttribute("maxOccurs", string);
                        }
                    }
                    if (paramType != 36) {
                        element4.setAttribute("nillable", "true");
                    }
                    element3.appendChild(element4);
                }
            }
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildMethodResponseElement(final String str, final IPGDetail ipgDetail, final WSDLGenInfo wsdlGenInfo) {
        Element element = null;
        final PGParam[] parameters = ipgDetail.getParameters();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        int n = 0;
        try {
            element = coreDocImpl.createElement("element");
            element.setAttribute("name", str + "Response");
            final Element element2 = coreDocImpl.createElement("complexType");
            final Element element3 = coreDocImpl.createElement("sequence");
            PGParam pgParam = ipgDetail.getReturnValue();
            if (pgParam == null) {
                PGAppObj.createReturnValueObj(ipgDetail);
                pgParam = ipgDetail.getReturnValue();
            }
            if (pgParam != null) {
                final String proToXMLType = WSDLDataTypes.proToXMLType(pgParam.getParamType());
                final Element element4 = coreDocImpl.createElement("element");
                element4.setAttribute("name", pgParam.getParamName());
                element4.setAttribute("type", "xsd:" + proToXMLType);
                if (pgParam.isExtentField()) {
                    final int extent = pgParam.getExtent();
                    final String string = Integer.toString(extent);
                    element4.setAttribute("minOccurs", string);
                    if (extent == 0) {
                        element4.setAttribute("maxOccurs", "unbounded");
                    }
                    else {
                        element4.setAttribute("maxOccurs", string);
                    }
                }
                element4.setAttribute("nillable", "true");
                element3.appendChild(element4);
            }
            for (int i = 0; i < parameters.length; ++i) {
                final PGParam pgParam2 = parameters[i];
                final int paramMode = pgParam2.getParamMode();
                final int paramType = pgParam2.getParamType();
                if (paramMode == 2 || paramMode == 3) {
                    String str2 = wsdlGenInfo.getCurrentSchemaPrefix() + ":";
                    String s = null;
                    switch (paramType) {
                        case 15: {
                            if (wsdlGenInfo.usePrefixForTempTables()) {
                                s = ipgDetail.getMethodName() + "_" + pgParam2.getParamName() + "Param";
                                break;
                            }
                            s = pgParam2.getParamName() + "Param";
                            break;
                        }
                        case 17: {
                            s = "TableHandleParam";
                            break;
                        }
                        case 36: {
                            final PGDataSetParam pgDataSetParam = (PGDataSetParam)pgParam2;
                            s = pgDataSetParam.getXmlNodeName();
                            if (s == null) {
                                s = pgDataSetParam.getClassName();
                            }
                            if (pgParam2.getWriteBeforeImage()) {
                                n = 1;
                                break;
                            }
                            str2 = pgDataSetParam.getXmlNamespacePrefix() + ":";
                            break;
                        }
                        case 37: {
                            if (pgParam2.getWriteBeforeImage()) {
                                s = "DataSetHandleChangesParam";
                                break;
                            }
                            s = "DataSetHandleParam";
                            break;
                        }
                        default: {
                            str2 = "xsd:";
                            s = WSDLDataTypes.proToXMLType(pgParam2.getParamType());
                            break;
                        }
                    }
                    final Element element5 = coreDocImpl.createElement("element");
                    if (paramType != 36) {
                        element5.setAttribute("name", pgParam2.getParamName());
                        element5.setAttribute("type", str2 + s);
                    }
                    else if (n == 1) {
                        final PGDataSetParam pgDataSetParam2 = (PGDataSetParam)pgParam2;
                        element5.setAttribute("name", s);
                        element5.setAttribute("type", str2 + (pgDataSetParam2.getClassName() + "Changes"));
                    }
                    else {
                        element5.setAttribute("ref", str2 + s);
                    }
                    if (pgParam2.isExtentField()) {
                        final int extent2 = pgParam2.getExtent();
                        final String string2 = Integer.toString(extent2);
                        element5.setAttribute("minOccurs", string2);
                        if (extent2 == 0) {
                            element5.setAttribute("maxOccurs", "unbounded");
                        }
                        else {
                            element5.setAttribute("maxOccurs", string2);
                        }
                    }
                    if (paramType != 36) {
                        element5.setAttribute("nillable", "true");
                    }
                    element3.appendChild(element5);
                }
            }
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildRowComplexType(final String str, final PGParam pgParam, final boolean b, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        String s;
        if (b) {
            s = str + "_" + pgParam.getParamName() + "Row";
        }
        else {
            s = pgParam.getParamName() + "Row";
        }
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", s);
            final Element element2 = coreDocImpl.createElement("sequence");
            for (int i = 0; i < pgParam.getMetaData().length; ++i) {
                final PGMetaData metaData = pgParam.getMetaData(i);
                final Element element3 = coreDocImpl.createElement("element");
                element3.setAttribute("name", metaData.getFieldName());
                element3.setAttribute("type", "xsd:" + WSDLDataTypes.proToXMLType(metaData.getType()));
                element3.setAttribute("nillable", "true");
                final int extent = metaData.getExtent();
                if (extent > 1) {
                    element3.setAttribute("minOccurs", Integer.toString(extent));
                    element3.setAttribute("maxOccurs", Integer.toString(extent));
                }
                final int n = metaData.getType() & 0xFF;
                String s2 = null;
                switch (n) {
                    case 18: {
                        s2 = "prodata:blob";
                        break;
                    }
                    case 19: {
                        s2 = "prodata:clob";
                        break;
                    }
                    case 34: {
                        s2 = "prodata:dateTime";
                        break;
                    }
                }
                if (s2 != null) {
                    element3.setAttribute("prodata:dataType", s2);
                }
                element2.appendChild(element3);
            }
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildTableComplexType(final String str, final PGParam pgParam, final boolean b, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        final String currentSchemaPrefix = wsdlGenInfo.getCurrentSchemaPrefix();
        String s;
        if (b) {
            s = str + "_" + pgParam.getParamName();
        }
        else {
            s = pgParam.getParamName();
        }
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", s + "Param");
            final Element element2 = coreDocImpl.createElement("sequence");
            final Element element3 = coreDocImpl.createElement("element");
            element3.setAttribute("name", pgParam.getParamName() + "Row");
            element3.setAttribute("type", currentSchemaPrefix + ":" + s + "Row");
            element3.setAttribute("minOccurs", "0");
            element3.setAttribute("maxOccurs", "unbounded");
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
}
