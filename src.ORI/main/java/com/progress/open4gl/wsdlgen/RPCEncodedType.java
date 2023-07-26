// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGMetaData;
import com.progress.open4gl.proxygen.PGParam;
import com.progress.open4gl.proxygen.PGMethod;
import com.progress.open4gl.proxygen.PGProc;
import com.progress.open4gl.proxygen.IPGDetail;
import org.apache.xerces.dom.CoreDocumentImpl;
import com.progress.open4gl.proxygen.PGGenInfo;
import org.w3c.dom.Node;
import java.util.Vector;
import org.w3c.dom.Element;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.Types;
import com.progress.open4gl.proxygen.PGAppObj;

public class RPCEncodedType
{
    public static void buildTypesElement(final PGAppObj pgAppObj, final Types types) {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.getGenInfo().getWSDLGenInfo();
        wsdlGenInfo.getCoreDocImpl();
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
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        genInfo.getDWGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
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
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", "FaultDetail");
            final Element element2 = coreDocImpl.createElement("sequence");
            final Element element3 = coreDocImpl.createElement("element");
            element3.setAttribute("name", "errorMessage");
            element3.setAttribute("type", "xsd:string");
            element2.appendChild(element3);
            final Element element4 = coreDocImpl.createElement("element");
            element4.setAttribute("name", "requestID");
            element4.setAttribute("type", "xsd:string");
            element2.appendChild(element4);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildTableHandleElement(final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", "TableHandleParam");
            final Element element2 = coreDocImpl.createElement("sequence");
            final Element element3 = coreDocImpl.createElement("any");
            element3.setAttribute("namespace", "##local");
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildSchemaElement(final PGAppObj pgAppObj) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final WSDLGenInfo wsdlGenInfo = genInfo.getWSDLGenInfo();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        final String schemaPrefix = pgAppObj.getSchemaPrefix();
        final String schemaNamespace = pgAppObj.getSchemaNamespace();
        try {
            wsdlGenInfo.setCurrentSchemaPrefix(schemaPrefix);
            element = coreDocImpl.createElement("schema");
            element.setAttribute("targetNamespace", schemaNamespace);
            element.setAttribute("xmlns", wsdlGenInfo.getXSDSchemaNamespace());
            element.setAttribute("elementFormDefault", "unqualified");
            if (pgAppObj.hasParamType(15, false) || pgAppObj.hasArray(false)) {
                element.appendChild(buildImportSoapEncElement(wsdlGenInfo));
            }
            if ((!genInfo.getConnectionFree() && !pgAppObj.isSubAppObject()) || pgAppObj.isSubAppObject()) {
                element.appendChild(buildObjectIDElement(pgAppObj.getAppObjectName(), wsdlGenInfo));
            }
            if (pgAppObj.hasParamType(17, false)) {
                element.appendChild(buildTableHandleElement(wsdlGenInfo));
            }
            if (pgAppObj.hasDatasetHandleParam()) {
                element.appendChild(WSDLDataSetTypes.buildGenericDataSetHandleComplexType(wsdlGenInfo));
            }
            if (pgAppObj.hasDatasetHandleChangesParam()) {
                element.appendChild(WSDLDataSetTypes.buildGenericDataSetHandleChangesComplexType(wsdlGenInfo));
            }
            buildArrayComplexTypes(pgAppObj, element, wsdlGenInfo);
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
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildSchemaElement(final PGProc pgProc) {
        final WSDLGenInfo wsdlGenInfo = PGProc.getGenInfo().getWSDLGenInfo();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        final String schemaPrefix = pgProc.getSchemaPrefix();
        final String schemaNamespace = pgProc.getSchemaNamespace();
        final String methodName = pgProc.getProcDetail().getMethodName();
        try {
            wsdlGenInfo.setCurrentSchemaPrefix(schemaPrefix);
            element = coreDocImpl.createElement("schema");
            element.setAttribute("targetNamespace", schemaNamespace);
            element.setAttribute("xmlns", wsdlGenInfo.getXSDSchemaNamespace());
            element.setAttribute("elementFormDefault", "unqualified");
            if (pgProc.hasParamType(15, true) || pgProc.hasArray(true)) {
                element.appendChild(buildImportSoapEncElement(wsdlGenInfo));
            }
            element.appendChild(buildObjectIDElement(methodName, wsdlGenInfo));
            if (pgProc.hasParamType(17, true)) {
                element.appendChild(buildTableHandleElement(wsdlGenInfo));
            }
            if (pgProc.hasDatasetHandleParam(true)) {
                element.appendChild(WSDLDataSetTypes.buildGenericDataSetHandleComplexType(wsdlGenInfo));
            }
            if (pgProc.hasDatasetHandleChangesParam(true)) {
                element.appendChild(WSDLDataSetTypes.buildGenericDataSetHandleChangesComplexType(wsdlGenInfo));
            }
            buildArrayComplexTypes(pgProc, element, wsdlGenInfo);
            final PGMethod[] internalProcs = pgProc.getProcDetail().getInternalProcs();
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
                    element.appendChild(buildRowElement(methodName, pgParam, !b, wsdlGenInfo));
                    element.appendChild(buildTableElement(methodName, pgParam, !b, wsdlGenInfo));
                    if (b) {
                        tempTableList.add(pgParam.getParamName());
                    }
                }
            }
        }
    }
    
    private static Element buildObjectIDElement(final String str, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", str + "ID");
            final Element element2 = coreDocImpl.createElement("sequence");
            final Element element3 = coreDocImpl.createElement("element");
            element3.setAttribute("name", "UUID");
            element3.setAttribute("type", "xsd:string");
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildImportSoapEncElement(final WSDLGenInfo wsdlGenInfo) {
        wsdlGenInfo.getDefinitionObj();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("import");
            element.setAttribute("namespace", wsdlGenInfo.getSoapEncNamespace());
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildRowElement(final String str, final PGParam pgParam, final boolean b, final WSDLGenInfo wsdlGenInfo) {
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
                element2.appendChild(element3);
            }
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static Element buildTableElement(final String str, final PGParam pgParam, final boolean b, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        final String currentSchemaPrefix = wsdlGenInfo.getCurrentSchemaPrefix();
        String s;
        if (b) {
            s = str + "_" + pgParam.getParamName() + "Row";
        }
        else {
            s = pgParam.getParamName() + "Row";
        }
        final String string = "ArrayOf" + s;
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", string);
            final Element element2 = coreDocImpl.createElement("complexContent");
            final Element element3 = coreDocImpl.createElement("restriction");
            element3.setAttribute("base", "soapenc:Array");
            final Element element4 = coreDocImpl.createElement("attribute");
            element4.setAttribute("ref", "soapenc:arrayType");
            element4.setAttribute("wsdl:arrayType", currentSchemaPrefix + ":" + s + "[]");
            element3.appendChild(element4);
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    private static void buildArrayComplexTypes(final PGAppObj pgAppObj, final Element element, final WSDLGenInfo wsdlGenInfo) {
        final Vector vector = new Vector();
        for (int i = 0; i < pgAppObj.getProcedures().length; ++i) {
            buildArrayComplexTypesForProc(pgAppObj.getProcedures(i).getProcDetail(), element, vector, wsdlGenInfo);
        }
        for (int j = 0; j < pgAppObj.getPersistentProcedures().length; ++j) {
            buildArrayComplexTypesForProc(pgAppObj.getPersistentProcedures(j).getProcDetail(), element, vector, wsdlGenInfo);
        }
    }
    
    private static void buildArrayComplexTypes(final PGProc pgProc, final Element element, final WSDLGenInfo wsdlGenInfo) {
        final Vector vector = new Vector();
        final PGMethod[] internalProcs = pgProc.getProcDetail().getInternalProcs();
        for (int i = 0; i < internalProcs.length; ++i) {
            final PGMethod pgMethod = internalProcs[i];
            if (!pgMethod.isExcluded()) {
                buildArrayComplexTypesForProc(pgMethod.getMethodDetail(), element, vector, wsdlGenInfo);
            }
        }
    }
    
    private static void buildArrayComplexTypesForProc(final IPGDetail ipgDetail, final Element element, final Vector vector, final WSDLGenInfo wsdlGenInfo) {
        final PGParam[] parameters = ipgDetail.getParameters();
        final PGParam returnValue = ipgDetail.getReturnValue();
        if (returnValue != null && returnValue.isExtentField()) {
            final String proToXMLArrayType = WSDLDataTypes.proToXMLArrayType(returnValue.getParamType());
            if (!vector.contains(proToXMLArrayType)) {
                element.appendChild(buildArrayElement(returnValue, wsdlGenInfo));
                vector.add(proToXMLArrayType);
            }
        }
        for (int i = 0; i < parameters.length; ++i) {
            final PGParam pgParam = parameters[i];
            if (pgParam.isExtentField()) {
                final String proToXMLArrayType2 = WSDLDataTypes.proToXMLArrayType(pgParam.getParamType());
                if (!vector.contains(proToXMLArrayType2)) {
                    element.appendChild(buildArrayElement(pgParam, wsdlGenInfo));
                    vector.add(proToXMLArrayType2);
                }
            }
        }
    }
    
    private static Element buildArrayElement(final PGParam pgParam, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        wsdlGenInfo.getCurrentSchemaPrefix();
        final String proToXMLType = WSDLDataTypes.proToXMLType(pgParam.getParamType());
        final String proToXMLArrayType = WSDLDataTypes.proToXMLArrayType(pgParam.getParamType());
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", proToXMLArrayType);
            final Element element2 = coreDocImpl.createElement("complexContent");
            final Element element3 = coreDocImpl.createElement("restriction");
            element3.setAttribute("base", "soapenc:Array");
            final Element element4 = coreDocImpl.createElement("attribute");
            element4.setAttribute("ref", "soapenc:arrayType");
            element4.setAttribute("wsdl:arrayType", "xsd:" + proToXMLType + "[]");
            element3.appendChild(element4);
            element2.appendChild(element3);
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
}
