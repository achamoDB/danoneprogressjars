// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.PGMetaData;
import com.progress.open4gl.proxygen.PGDataLink;
import com.progress.open4gl.proxygen.PGDataTableParam;
import com.progress.open4gl.proxygen.PGParam;
import java.util.Vector;
import com.progress.open4gl.proxygen.PGDataSetParam;
import com.progress.open4gl.proxygen.IPGDetail;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class WSDLDataSetTypes
{
    protected static Element buildGenericDataSetHandleComplexType(final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", "DataSetHandleParam");
            final Element element2 = coreDocImpl.createElement("annotation");
            element.appendChild(element2);
            final Element element3 = coreDocImpl.createElement("documentation");
            element3.appendChild(coreDocImpl.createTextNode("This is the schema definition for an OpenEdge dynamic ProDataSet parameter.  The first element in this sequence must be a w3c XML Schema document describing the definition of the ProDataSet.  The second element contains the serialized data."));
            element2.appendChild(element3);
            final Element element4 = coreDocImpl.createElement("sequence");
            final Element element5 = coreDocImpl.createElement("any");
            element5.setAttribute("minOccurs", "2");
            element5.setAttribute("maxOccurs", "2");
            element4.appendChild(element5);
            element.appendChild(element4);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static Element buildGenericDataSetHandleChangesComplexType(final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", "DataSetHandleChangesParam");
            element.setAttribute("prodata:isDsChanges", "true");
            final Element element2 = coreDocImpl.createElement("annotation");
            element.appendChild(element2);
            final Element element3 = coreDocImpl.createElement("documentation");
            element3.appendChild(coreDocImpl.createTextNode("This is the schema definition for an OpenEdge dynamic ProDataSet parameter.  The first element in this sequence must be a w3c XML Schema document describing the definition of the ProDataSet.  The second element contains the serialized data, including before-image data."));
            element2.appendChild(element3);
            final Element element4 = coreDocImpl.createElement("sequence");
            final Element element5 = coreDocImpl.createElement("any");
            element5.setAttribute("minOccurs", "2");
            element5.setAttribute("maxOccurs", "2");
            element4.appendChild(element5);
            element.appendChild(element4);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static void buildDataSetComplexTypesForProc(final IPGDetail ipgDetail, final Element element, final WSDLGenInfo wsdlGenInfo) {
        final PGParam[] parameters = ipgDetail.getParameters();
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        final Vector dataSetsCreatedList = wsdlGenInfo.getDataSetsCreatedList();
        final Vector dataSetChangessCreatedList = wsdlGenInfo.getDataSetChangessCreatedList();
        final Vector namespacesImportedList = wsdlGenInfo.getNamespacesImportedList();
        for (int i = 0; i < parameters.length; ++i) {
            final PGParam pgParam = parameters[i];
            if (pgParam.getParamType() == 36) {
                int n = 1;
                final PGDataSetParam obj = (PGDataSetParam)pgParam;
                final String xmlNamespace = obj.getXmlNamespace();
                final Vector dataSetNspaceNames = wsdlGenInfo.getDataSetNspaceNames();
                final Vector dataSetNspacePrefixes = wsdlGenInfo.getDataSetNspacePrefixes();
                final String currentSchemaNamespace = wsdlGenInfo.getCurrentSchemaNamespace();
                final String className = obj.getClassName();
                String s;
                if (xmlNamespace == null) {
                    s = "null";
                }
                else {
                    s = xmlNamespace;
                }
                if (pgParam.getWriteBeforeImage()) {
                    int size;
                    int j;
                    for (size = dataSetChangessCreatedList.size(), j = 0; j < size; ++j) {
                        final Vector<String> vector = dataSetChangessCreatedList.elementAt(j);
                        final String s2 = vector.elementAt(0);
                        final String s3 = vector.elementAt(1);
                        if (s2.equals(className) && s3.equals(s)) {
                            break;
                        }
                    }
                    if (j == size) {
                        element.appendChild(buildDatasetChangesComplexType(pgParam, wsdlGenInfo));
                        final Vector<String> obj2 = new Vector<String>();
                        obj2.addElement(className);
                        obj2.addElement(s);
                        dataSetChangessCreatedList.addElement(obj2);
                    }
                }
                int size2;
                int k;
                for (size2 = dataSetsCreatedList.size(), k = 0; k < size2; ++k) {
                    final Vector<String> vector2 = dataSetsCreatedList.elementAt(k);
                    final String s4 = vector2.elementAt(0);
                    final String s5 = vector2.elementAt(1);
                    if (s4.equals(className) && s5.equals(s)) {
                        break;
                    }
                }
                if (k < size2) {
                    n = 0;
                    if (xmlNamespace == null || xmlNamespace.length() == 0) {
                        obj.setXmlNamespacePrefix(wsdlGenInfo.getCurrentSchemaPrefix());
                    }
                    else {
                        int size3;
                        int n2;
                        for (size3 = dataSetNspaceNames.size(), n2 = 0; n2 < size3 && !dataSetNspaceNames.elementAt(n2).equals(xmlNamespace); ++n2) {}
                        if (n2 < size3) {
                            obj.setXmlNamespacePrefix(dataSetNspacePrefixes.elementAt(n2));
                        }
                    }
                }
                if (n != 0 && xmlNamespace != null && xmlNamespace.length() > 0 && !currentSchemaNamespace.equals(xmlNamespace)) {
                    n = 0;
                    int size4;
                    int n3;
                    for (size4 = dataSetNspaceNames.size(), n3 = 0; n3 < size4 && !dataSetNspaceNames.elementAt(n3).equals(xmlNamespace); ++n3) {}
                    String uniqueSchemaPrefix;
                    if (n3 == size4) {
                        uniqueSchemaPrefix = wsdlGenInfo.getUniqueSchemaPrefix();
                        wsdlGenInfo.updateUniqueSchemaPrefix();
                        wsdlGenInfo.getDefinitionObj().addNamespace(uniqueSchemaPrefix, xmlNamespace);
                        dataSetNspaceNames.addElement(xmlNamespace);
                        dataSetNspacePrefixes.addElement(uniqueSchemaPrefix);
                        if (!pgParam.getWriteBeforeImage() && dwGenInfo.getEncoding() == 3 && !namespacesImportedList.contains(xmlNamespace)) {
                            final Element element2 = coreDocImpl.createElement("import");
                            element2.setAttribute("namespace", xmlNamespace);
                            final Node firstChild = element.getFirstChild();
                            if (firstChild != null) {
                                element.insertBefore(element2, firstChild);
                            }
                            else {
                                element.appendChild(element2);
                            }
                            namespacesImportedList.addElement(xmlNamespace);
                        }
                    }
                    else {
                        uniqueSchemaPrefix = dataSetNspacePrefixes.elementAt(n3);
                        if (!pgParam.getWriteBeforeImage() && dwGenInfo.getEncoding() == 3 && !namespacesImportedList.contains(xmlNamespace)) {
                            final Element element3 = coreDocImpl.createElement("import");
                            element3.setAttribute("namespace", xmlNamespace);
                            final Node firstChild2 = element.getFirstChild();
                            if (firstChild2 != null) {
                                element.insertBefore(element3, firstChild2);
                            }
                            else {
                                element.appendChild(element3);
                            }
                            namespacesImportedList.addElement(xmlNamespace);
                        }
                    }
                    obj.setXmlNamespacePrefix(uniqueSchemaPrefix);
                    final Vector dataSetsWithNamespace = wsdlGenInfo.getDataSetsWithNamespace();
                    int size5;
                    int l;
                    for (size5 = dataSetsWithNamespace.size(), l = 0; l < size5; ++l) {
                        final PGDataSetParam pgDataSetParam = dataSetsWithNamespace.elementAt(l);
                        final String xmlNamespace2 = pgDataSetParam.getXmlNamespace();
                        final String className2 = pgDataSetParam.getClassName();
                        if (xmlNamespace.equals(xmlNamespace2) && className.equals(className2)) {
                            break;
                        }
                    }
                    if (l == size5) {
                        dataSetsWithNamespace.addElement(obj);
                    }
                }
                if (n != 0) {
                    obj.setXmlNamespacePrefix(wsdlGenInfo.getCurrentSchemaPrefix());
                    element.appendChild(buildDataSetComplexType(pgParam, wsdlGenInfo));
                    final Vector<String> obj3 = new Vector<String>();
                    obj3.addElement(className);
                    obj3.addElement(s);
                    dataSetsCreatedList.addElement(obj3);
                }
            }
        }
    }
    
    protected static Element buildDataSetComplexType(final PGParam pgParam, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        final DWGenInfo dwGenInfo = wsdlGenInfo.getDWGenInfo();
        Element element = null;
        Node element2 = null;
        Element element3 = null;
        final PGDataSetParam pgDataSetParam = (PGDataSetParam)pgParam;
        final Vector vector = new Vector<Vector<PGDataTableParam>>();
        final Vector vector2 = new Vector<PGDataLink>();
        final Vector vector3 = new Vector<Vector<String>>();
        final Vector uniqueIndexNames = wsdlGenInfo.getUniqueIndexNames();
        final Vector keyrefNames = wsdlGenInfo.getKeyrefNames();
        final String className = pgDataSetParam.getClassName();
        boolean b = false;
        if (pgParam.getWriteBeforeImage() || dwGenInfo.getEncoding() == 3) {
            b = true;
        }
        try {
            Element element4;
            if (b) {
                final String xmlNodeName = pgDataSetParam.getXmlNodeName();
                String s;
                if (xmlNodeName != null) {
                    s = xmlNodeName;
                }
                else {
                    s = className;
                }
                element = coreDocImpl.createElement("element");
                element.setAttribute("name", s);
                if (xmlNodeName != null) {
                    element.setAttribute("prodata:datasetName", pgDataSetParam.getParamName());
                }
                final String xmlNamespace = pgDataSetParam.getXmlNamespace();
                if (xmlNamespace != null && xmlNamespace.length() == 0) {
                    element.setAttribute("form", "unqualified");
                }
                element4 = coreDocImpl.createElement("complexType");
                element.appendChild(element4);
            }
            else {
                element4 = (element = coreDocImpl.createElement("complexType"));
                element.setAttribute("name", className + "Param");
            }
            final Element element5 = coreDocImpl.createElement("sequence");
            final PGDataTableParam[] dataSetTables = pgDataSetParam.getDataSetTables();
            final PGDataLink[] dataLinks = pgDataSetParam.getDataLinks();
            if (dataSetTables != null) {
                for (int i = 0; i < dataSetTables.length; ++i) {
                    boolean b2 = false;
                    if (dataLinks != null) {
                        for (int j = 0; j < dataLinks.length; ++j) {
                            final int flag = dataLinks[j].getFlag();
                            boolean b3 = false;
                            if ((flag & 0x2) == 0x2) {
                                b3 = true;
                            }
                            if (b3 && dataLinks[j].getChildBuffer().equalsIgnoreCase(dataSetTables[i].getParamName())) {
                                b2 = true;
                            }
                        }
                    }
                    if (!b2) {
                        element5.appendChild(buildDataSetTtabComplexType(dataSetTables[i], pgDataSetParam, wsdlGenInfo));
                    }
                }
            }
            element4.appendChild(element5);
            Element element6 = element;
            if (!b && dataSetTables != null) {
                for (int n = 0; n < dataSetTables.length && element2 == null; ++n) {
                    final PGDataTableParam pgDataTableParam = dataSetTables[n];
                    if (pgDataTableParam.hasUniquePrimaryKey() || pgDataTableParam.getNonPrimeKeyIndexNames() != null) {
                        element2 = coreDocImpl.createElement("annotation");
                        final Node firstChild = element.getFirstChild();
                        if (firstChild != null) {
                            element.insertBefore(element2, firstChild);
                        }
                        else {
                            element.appendChild(element2);
                        }
                        element3 = coreDocImpl.createElement("appinfo");
                        element2.appendChild(element3);
                        element6 = element3;
                    }
                }
            }
            if (dataSetTables != null) {
                for (int k = 0; k < dataSetTables.length; ++k) {
                    final PGDataTableParam obj = dataSetTables[k];
                    final String primeKeyIndexName = obj.getPrimeKeyIndexName();
                    if (obj.hasUniquePrimaryKey()) {
                        int size;
                        int index;
                        for (size = uniqueIndexNames.size(), index = 0; index < size && !uniqueIndexNames.elementAt(index).equals(primeKeyIndexName); ++index) {}
                        if (index == size) {
                            uniqueIndexNames.addElement(primeKeyIndexName);
                            element6.appendChild(buildUniqueIndex(obj, primeKeyIndexName, obj.getPrimeKeyColNames(), true, wsdlGenInfo));
                        }
                        else {
                            final String uniqueName = makeUniqueName(primeKeyIndexName, uniqueIndexNames);
                            if (uniqueName.length() > 32) {
                                final Vector<PGDataTableParam> obj2 = new Vector<PGDataTableParam>();
                                obj2.addElement((PGDataTableParam)primeKeyIndexName);
                                obj2.addElement(obj);
                                vector.addElement(obj2);
                            }
                            else {
                                uniqueIndexNames.addElement(uniqueName);
                                element6.appendChild(buildUniqueIndex(obj, uniqueName, obj.getPrimeKeyColNames(), true, wsdlGenInfo));
                                final Vector<String> obj3 = new Vector<String>();
                                obj3.addElement(primeKeyIndexName);
                                obj3.addElement(uniqueName);
                                obj3.addElement(obj.getParamName());
                                vector3.addElement(obj3);
                            }
                        }
                    }
                    else if (null != primeKeyIndexName) {
                        final Vector<PGDataTableParam> obj4 = new Vector<PGDataTableParam>();
                        obj4.addElement((PGDataTableParam)primeKeyIndexName);
                        obj4.addElement(obj);
                        vector.addElement(obj4);
                    }
                    final String[] nonPrimeKeyIndexNames = obj.getNonPrimeKeyIndexNames();
                    if (nonPrimeKeyIndexNames != null) {
                        final String[][] nonPrimeKeyIndexes = obj.getNonPrimeKeyIndexes();
                        for (int l = 0; l < nonPrimeKeyIndexNames.length; ++l) {
                            String s2;
                            int size2;
                            int index2;
                            for (s2 = nonPrimeKeyIndexNames[l], size2 = uniqueIndexNames.size(), index2 = 0; index2 < size2 && !uniqueIndexNames.elementAt(index2).equals(s2); ++index2) {}
                            if (index2 == size2) {
                                uniqueIndexNames.addElement(s2);
                                element6.appendChild(buildUniqueIndex(obj, s2, nonPrimeKeyIndexes[l], false, wsdlGenInfo));
                            }
                            else {
                                final String uniqueName2 = makeUniqueName(s2, uniqueIndexNames);
                                if (uniqueName2.length() > 32) {
                                    final Vector<PGDataTableParam> obj5 = new Vector<PGDataTableParam>();
                                    obj5.addElement((PGDataTableParam)s2);
                                    obj5.addElement(obj);
                                    vector.addElement(obj5);
                                }
                                else {
                                    uniqueIndexNames.addElement(uniqueName2);
                                    element6.appendChild(buildUniqueIndex(obj, uniqueName2, nonPrimeKeyIndexes[l], false, wsdlGenInfo));
                                    final Vector<String> obj6 = new Vector<String>();
                                    obj6.addElement(s2);
                                    obj6.addElement(uniqueName2);
                                    obj6.addElement(obj.getParamName());
                                    vector3.addElement(obj6);
                                }
                            }
                        }
                    }
                }
            }
            if (dataLinks != null) {
                for (int n2 = 0; n2 < dataLinks.length; ++n2) {
                    final String parentBuffer = dataLinks[n2].getParentBuffer();
                    final String childBuffer = dataLinks[n2].getChildBuffer();
                    final Vector parentFieldVector = dataLinks[n2].getParentFieldVector();
                    final Vector childFieldVector = dataLinks[n2].getChildFieldVector();
                    final String linkName = dataLinks[n2].getLinkName();
                    final int flag2 = dataLinks[n2].getFlag();
                    boolean b4 = false;
                    int n3 = 0;
                    String s3 = null;
                    if ((flag2 & 0x2) == 0x2) {
                        b4 = true;
                    }
                    final PGDataTableParam dataSetTable = pgDataSetParam.getDataSetTable(parentBuffer);
                    final PGDataTableParam dataSetTable2 = pgDataSetParam.getDataSetTable(childBuffer);
                    if (dataSetTable != null) {
                        final String[] primeKeyColNames = dataSetTable.getPrimeKeyColNames();
                        if (dataSetTable.hasUniquePrimaryKey() && primeKeyColNames.length == parentFieldVector.size()) {
                            int index3;
                            for (index3 = 0; index3 < primeKeyColNames.length && primeKeyColNames[index3].equalsIgnoreCase(parentFieldVector.elementAt(index3)); ++index3) {}
                            if (index3 == primeKeyColNames.length) {
                                n3 = 1;
                                s3 = dataSetTable.getPrimeKeyIndexName();
                            }
                        }
                        if (n3 == 0) {
                            final String[] nonPrimeKeyIndexNames2 = dataSetTable.getNonPrimeKeyIndexNames();
                            if (nonPrimeKeyIndexNames2 != null) {
                                final String[][] nonPrimeKeyIndexes2 = dataSetTable.getNonPrimeKeyIndexes();
                                for (int n4 = 0; n4 < nonPrimeKeyIndexNames2.length && n3 == 0; ++n4) {
                                    final String[] array = nonPrimeKeyIndexes2[n4];
                                    if (array.length == parentFieldVector.size()) {
                                        int index4;
                                        for (index4 = 0; index4 < array.length && array[index4].equalsIgnoreCase(parentFieldVector.elementAt(index4)); ++index4) {}
                                        if (index4 == array.length) {
                                            n3 = 1;
                                            s3 = nonPrimeKeyIndexNames2[n4];
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (n3 == 0 || (!vector.isEmpty() && referNameIsProdataIndex(s3, parentBuffer, vector))) {
                        vector2.addElement(dataLinks[n2]);
                    }
                    else if (dataSetTable2 != null) {
                        if (!vector3.isEmpty()) {
                            s3 = findNewReferName(s3, parentBuffer, vector3);
                        }
                        int size3;
                        int index5;
                        for (size3 = keyrefNames.size(), index5 = 0; index5 < size3 && !keyrefNames.elementAt(index5).equals(linkName); ++index5) {}
                        if (index5 == size3) {
                            element6.appendChild(buildKeyref(linkName, b4, s3, dataSetTable2, childFieldVector, wsdlGenInfo));
                            keyrefNames.addElement(linkName);
                        }
                        else {
                            final String uniqueName3 = makeUniqueName(linkName, keyrefNames);
                            if (uniqueName3.length() > 32) {
                                vector2.addElement(dataLinks[n2]);
                            }
                            else {
                                element6.appendChild(buildKeyref(uniqueName3, b4, s3, dataSetTable2, childFieldVector, wsdlGenInfo));
                                keyrefNames.addElement(uniqueName3);
                            }
                        }
                    }
                }
            }
            if (!vector.isEmpty() || !vector2.isEmpty()) {
                if (element2 == null) {
                    final Element element7 = coreDocImpl.createElement("annotation");
                    final Node firstChild2 = element.getFirstChild();
                    if (firstChild2 != null) {
                        element.insertBefore(element7, firstChild2);
                    }
                    else {
                        element.appendChild(element7);
                    }
                    element3 = coreDocImpl.createElement("appinfo");
                    element7.appendChild(element3);
                }
                for (int index6 = 0; index6 < vector.size(); ++index6) {
                    element3.appendChild(buildProdataIndexDef(vector.elementAt(index6), wsdlGenInfo));
                }
                for (int index7 = 0; index7 < vector2.size(); ++index7) {
                    element3.appendChild(buildProdataRelationDef(vector2.elementAt(index7), wsdlGenInfo));
                }
            }
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static Element buildDatasetChangesComplexType(final PGParam pgParam, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        final PGDataSetParam pgDataSetParam = (PGDataSetParam)pgParam;
        final String className = pgDataSetParam.getClassName();
        String xmlNodeName = pgDataSetParam.getXmlNodeName();
        if (xmlNodeName == null) {
            xmlNodeName = className;
        }
        try {
            element = coreDocImpl.createElement("complexType");
            element.setAttribute("name", className + "Changes");
            element.setAttribute("prodata:isDsChanges", "true");
            element.setAttribute("prodata:datasetName", xmlNodeName);
            String s = pgDataSetParam.getXmlNamespace();
            if (s == null || s.length() == 0) {
                s = wsdlGenInfo.getCurrentSchemaNamespace();
            }
            element.setAttribute("prodata:namespace", s);
            final Element element2 = coreDocImpl.createElement("sequence");
            element2.appendChild(coreDocImpl.createElement("any"));
            element.appendChild(element2);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static Element buildDataSetTtabComplexType(final PGDataTableParam pgDataTableParam, final PGDataSetParam pgDataSetParam, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        final String paramName = pgDataTableParam.getParamName();
        PGMetaData pgMetaData = null;
        final PGDataLink[] dataLinks = pgDataSetParam.getDataLinks();
        final String xmlNodeName = pgDataTableParam.getXmlNodeName();
        String str;
        if (xmlNodeName != null) {
            str = xmlNodeName;
        }
        else {
            str = paramName;
        }
        try {
            element = coreDocImpl.createElement("element");
            element.setAttribute("name", str);
            element.setAttribute("minOccurs", "0");
            element.setAttribute("maxOccurs", "unbounded");
            if (pgDataTableParam.hasBeforeTable()) {
                element.setAttribute("prodata:beforeTable", "BI" + str);
            }
            if (xmlNodeName != null) {
                element.setAttribute("prodata:tableName", paramName);
            }
            Element element2 = coreDocImpl.createElement("complexType");
            element.appendChild(element2);
            for (int i = 0; i < pgDataTableParam.getMetaData().length; ++i) {
                final PGMetaData metaData = pgDataTableParam.getMetaData(i);
                if (metaData.getXMLMapping() == 2) {
                    pgMetaData = metaData;
                    break;
                }
            }
            Element element3;
            if (pgMetaData == null) {
                element3 = coreDocImpl.createElement("sequence");
                element2.appendChild(element3);
            }
            else {
                element3 = coreDocImpl.createElement("simpleContent");
                element3.setAttribute("prodata:fieldName", pgMetaData.getFieldName());
                element2.appendChild(element3);
                final Element element4 = coreDocImpl.createElement("extension");
                element3.appendChild(element4);
                element4.setAttribute("base", "xsd:" + WSDLDataTypes.proToXMLType(pgMetaData.getType()));
                final int n = pgMetaData.getType() & 0xFF;
                String s = null;
                switch (n) {
                    case 18: {
                        s = "prodata:blob";
                        break;
                    }
                    case 19: {
                        s = "prodata:clob";
                        break;
                    }
                    case 34: {
                        s = "prodata:dateTime";
                        break;
                    }
                }
                if (s != null) {
                    element4.setAttribute("prodata:dataType", s);
                }
                element2 = element4;
            }
            if (pgMetaData == null) {
                for (int j = 0; j < pgDataTableParam.getMetaData().length; ++j) {
                    final PGMetaData metaData2 = pgDataTableParam.getMetaData(j);
                    if (metaData2.getXMLMapping() == 0) {
                        final String fieldName = metaData2.getFieldName();
                        final String xmlNodeName2 = metaData2.getXmlNodeName();
                        String s2;
                        if (xmlNodeName2 != null) {
                            s2 = xmlNodeName2;
                        }
                        else {
                            s2 = fieldName;
                        }
                        final Element element5 = coreDocImpl.createElement("element");
                        element5.setAttribute("name", s2);
                        if (xmlNodeName2 != null) {
                            element5.setAttribute("prodata:fieldName", fieldName);
                        }
                        element5.setAttribute("type", "xsd:" + WSDLDataTypes.proToXMLType(metaData2.getType()));
                        element5.setAttribute("nillable", "true");
                        final int extent = metaData2.getExtent();
                        if (extent > 1) {
                            element5.setAttribute("minOccurs", Integer.toString(extent));
                            element5.setAttribute("maxOccurs", Integer.toString(extent));
                        }
                        final int n2 = metaData2.getType() & 0xFF;
                        String s3 = null;
                        switch (n2) {
                            case 18: {
                                s3 = "prodata:blob";
                                break;
                            }
                            case 19: {
                                s3 = "prodata:clob";
                                break;
                            }
                            case 34: {
                                s3 = "prodata:dateTime";
                                break;
                            }
                        }
                        if (s3 != null) {
                            element5.setAttribute("prodata:dataType", s3);
                        }
                        element3.appendChild(element5);
                    }
                }
            }
            if (dataLinks != null) {
                for (int k = 0; k < dataLinks.length; ++k) {
                    final int flag = dataLinks[k].getFlag();
                    boolean b = false;
                    if ((flag & 0x2) == 0x2) {
                        b = true;
                    }
                    if (b && dataLinks[k].getParentBuffer().equalsIgnoreCase(pgDataTableParam.getParamName())) {
                        element3.appendChild(buildDataSetTtabComplexType(pgDataSetParam.getDataSetTable(dataLinks[k].getChildBuffer()), pgDataSetParam, wsdlGenInfo));
                    }
                }
            }
            for (int l = 0; l < pgDataTableParam.getMetaData().length; ++l) {
                final PGMetaData metaData3 = pgDataTableParam.getMetaData(l);
                if (metaData3.getXMLMapping() == 1) {
                    final String fieldName2 = metaData3.getFieldName();
                    final String xmlNodeName3 = metaData3.getXmlNodeName();
                    String s4;
                    if (xmlNodeName3 != null) {
                        s4 = xmlNodeName3;
                    }
                    else {
                        s4 = fieldName2;
                    }
                    final Element element6 = coreDocImpl.createElement("attribute");
                    element6.setAttribute("name", s4);
                    if (xmlNodeName3 != null) {
                        element6.setAttribute("prodata:fieldName", fieldName2);
                    }
                    element6.setAttribute("type", "xsd:" + WSDLDataTypes.proToXMLType(metaData3.getType()));
                    final int n3 = metaData3.getType() & 0xFF;
                    String s5 = null;
                    switch (n3) {
                        case 18: {
                            s5 = "prodata:blob";
                            break;
                        }
                        case 19: {
                            s5 = "prodata:clob";
                            break;
                        }
                        case 34: {
                            s5 = "prodata:dateTime";
                            break;
                        }
                    }
                    if (s5 != null) {
                        element6.setAttribute("prodata:dataType", s5);
                    }
                    element2.appendChild(element6);
                }
            }
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static Element buildUniqueIndex(final PGDataTableParam pgDataTableParam, final String s, final String[] array, final boolean b, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        final PGMetaData[] metaData = pgDataTableParam.getMetaData();
        String xmlNodeName = null;
        final String paramName = pgDataTableParam.getParamName();
        String xmlNodeName2 = pgDataTableParam.getXmlNodeName();
        if (xmlNodeName2 == null) {
            xmlNodeName2 = paramName;
        }
        try {
            element = coreDocImpl.createElement("unique");
            element.setAttribute("name", s);
            if (b) {
                element.setAttribute("prodata:primaryIndex", "true");
            }
            final Element element2 = coreDocImpl.createElement("selector");
            element2.setAttribute("xpath", ".//" + wsdlGenInfo.getCurrentSchemaPrefix() + ":" + xmlNodeName2);
            element.appendChild(element2);
            for (int i = 0; i < array.length; ++i) {
                int n = 0;
                int j = 0;
                while (j < metaData.length) {
                    if (metaData[j].getFieldName().equalsIgnoreCase(array[i])) {
                        if (metaData[j].getXMLMapping() == 1) {
                            n = 1;
                        }
                        xmlNodeName = metaData[j].getXmlNodeName();
                        if (xmlNodeName == null) {
                            xmlNodeName = array[i];
                            break;
                        }
                        break;
                    }
                    else {
                        ++j;
                    }
                }
                final Element element3 = coreDocImpl.createElement("field");
                if (n == 1) {
                    element3.setAttribute("xpath", "@" + xmlNodeName);
                }
                else {
                    element3.setAttribute("xpath", wsdlGenInfo.getCurrentSchemaPrefix() + ":" + xmlNodeName);
                }
                element.appendChild(element3);
            }
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static Element buildKeyref(final String s, final boolean b, final String str, final PGDataTableParam pgDataTableParam, final Vector vector, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        final String currentSchemaPrefix = wsdlGenInfo.getCurrentSchemaPrefix();
        Element element = null;
        final PGMetaData[] metaData = pgDataTableParam.getMetaData();
        String xmlNodeName = null;
        try {
            element = coreDocImpl.createElement("keyref");
            element.setAttribute("name", s);
            element.setAttribute("refer", currentSchemaPrefix + ":" + str);
            if (b) {
                element.setAttribute("prodata:nested", "true");
            }
            final Element element2 = coreDocImpl.createElement("selector");
            String str2 = pgDataTableParam.getXmlNodeName();
            if (str2 == null) {
                str2 = pgDataTableParam.getParamName();
            }
            element2.setAttribute("xpath", ".//" + currentSchemaPrefix + ":" + str2);
            element.appendChild(element2);
            for (int i = 0; i < vector.size(); ++i) {
                int n = 0;
                int j = 0;
                while (j < metaData.length) {
                    final String fieldName = metaData[j].getFieldName();
                    if (fieldName.equalsIgnoreCase(vector.elementAt(i))) {
                        if (metaData[j].getXMLMapping() == 1) {
                            n = 1;
                        }
                        xmlNodeName = metaData[j].getXmlNodeName();
                        if (xmlNodeName == null) {
                            xmlNodeName = fieldName;
                            break;
                        }
                        break;
                    }
                    else {
                        ++j;
                    }
                }
                final Element element3 = coreDocImpl.createElement("field");
                if (n == 1) {
                    element3.setAttribute("xpath", "@" + xmlNodeName);
                }
                else {
                    element3.setAttribute("xpath", currentSchemaPrefix + ":" + xmlNodeName);
                }
                element.appendChild(element3);
            }
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static Element buildProdataIndexDef(final Vector vector, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        String[] primeKeyColNames = null;
        final String anObject = vector.elementAt(0);
        final PGDataTableParam pgDataTableParam = (PGDataTableParam)vector.elementAt(1);
        boolean b = false;
        boolean b2 = false;
        final String primeKeyIndexName = pgDataTableParam.getPrimeKeyIndexName();
        if (null != primeKeyIndexName && primeKeyIndexName.equals(anObject)) {
            b = true;
            if (pgDataTableParam.hasUniquePrimaryKey()) {
                b2 = true;
            }
            primeKeyColNames = pgDataTableParam.getPrimeKeyColNames();
        }
        else {
            b2 = true;
            final String[] nonPrimeKeyIndexNames = pgDataTableParam.getNonPrimeKeyIndexNames();
            if (null != nonPrimeKeyIndexNames) {
                final String[][] nonPrimeKeyIndexes = pgDataTableParam.getNonPrimeKeyIndexes();
                for (int i = 0; i < nonPrimeKeyIndexNames.length; ++i) {
                    if (anObject.equals(nonPrimeKeyIndexNames[i])) {
                        primeKeyColNames = nonPrimeKeyIndexes[i];
                        break;
                    }
                }
            }
        }
        try {
            element = coreDocImpl.createElement("prodata:index");
            element.setAttribute("name", anObject);
            if (b) {
                element.setAttribute("prodata:primaryIndex", "true");
            }
            if (b2) {
                element.setAttribute("prodata:uniqueIndex", "true");
            }
            final Element element2 = coreDocImpl.createElement("prodata:table");
            element2.setAttribute("name", pgDataTableParam.getParamName());
            element.appendChild(element2);
            for (int j = 0; j < primeKeyColNames.length; ++j) {
                final Element element3 = coreDocImpl.createElement("prodata:field");
                element3.setAttribute("name", primeKeyColNames[j]);
                element.appendChild(element3);
            }
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static Element buildProdataRelationDef(final PGDataLink pgDataLink, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        Element element = null;
        try {
            element = coreDocImpl.createElement("prodata:relation");
            element.setAttribute("name", pgDataLink.getLinkName());
            if ((pgDataLink.getFlag() & 0x2) == 0x2) {
                element.setAttribute("prodata:nested", "true");
            }
            element.setAttribute("prodata:parent", pgDataLink.getParentBuffer());
            element.setAttribute("prodata:child", pgDataLink.getChildBuffer());
            element.setAttribute("prodata:relationFields", pgDataLink.getPairsList());
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static Element buildDataSetSchemaElement(final String s, final String currentSchemaPrefix, final WSDLGenInfo wsdlGenInfo) {
        final CoreDocumentImpl coreDocImpl = wsdlGenInfo.getCoreDocImpl();
        final Vector dataSetsWithNamespace = wsdlGenInfo.getDataSetsWithNamespace();
        Element element = null;
        try {
            wsdlGenInfo.setCurrentSchemaPrefix(currentSchemaPrefix);
            wsdlGenInfo.setCurrentSchemaNamespace(s);
            element = coreDocImpl.createElement("schema");
            element.setAttribute("targetNamespace", s);
            element.setAttribute("xmlns", wsdlGenInfo.getXSDSchemaNamespace());
            element.setAttribute("elementFormDefault", "qualified");
            wsdlGenInfo.setUniqueIndexNames(new Vector());
            wsdlGenInfo.setKeyRefNames(new Vector());
            for (int i = 0; i < dataSetsWithNamespace.size(); ++i) {
                final PGDataSetParam pgDataSetParam = dataSetsWithNamespace.elementAt(i);
                if (pgDataSetParam.getXmlNamespace().equals(s)) {
                    element.appendChild(buildDataSetComplexType(pgDataSetParam, wsdlGenInfo));
                }
            }
            wsdlGenInfo.setUniqueIndexNames(null);
            wsdlGenInfo.setKeyRefNames(null);
        }
        catch (ClassCastException ex) {}
        return element;
    }
    
    protected static String makeUniqueName(final String str, final Vector vector) {
        int n = 2;
        final int size = vector.size();
        int i;
        String string;
        do {
            for (string = str + "_" + String.valueOf(n++), i = 0; i < size && !vector.elementAt(i).equals(string); ++i) {}
        } while (i != size);
        return string;
    }
    
    protected static String findNewReferName(final String anObject, final String anObject2, final Vector vector) {
        for (int size = vector.size(), i = 0; i < size; ++i) {
            final Vector<String> vector2 = vector.elementAt(i);
            final String s = vector2.elementAt(0);
            final String s2 = vector2.elementAt(1);
            final String s3 = vector2.elementAt(2);
            if (s.equals(anObject) && s3.equals(anObject2)) {
                return s2;
            }
        }
        return anObject;
    }
    
    protected static boolean referNameIsProdataIndex(final String s, final String s2, final Vector vector) {
        for (int size = vector.size(), i = 0; i < size; ++i) {
            final Vector<String> vector2 = vector.elementAt(i);
            final String anObject = vector2.elementAt(0);
            final PGDataTableParam pgDataTableParam = (PGDataTableParam)vector2.elementAt(1);
            if (s.equals(anObject) && s2.equals(pgDataTableParam.getParamName())) {
                return true;
            }
        }
        return false;
    }
}
