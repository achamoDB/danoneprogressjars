// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import com.progress.open4gl.Parameter;
import org.apache.soap.util.xml.QName;
import com.progress.wsa.admin.MethodParam;
import com.progress.wsa.admin.MethodDescriptor;
import java.util.Hashtable;
import com.progress.wsa.admin.PscDeploymentDescriptor;
import com.progress.open4gl.wsdlgen.DWGenInfo;
import com.progress.wsa.admin.AppContainer;
import com.progress.wsa.WsaSOAPEngineContext;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import com.progress.wsa.admin.WsaParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import java.util.Vector;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.Holder;
import javax.wsdl.PortType;
import javax.wsdl.Binding;
import com.progress.wsa.open4gl.XMLSerializable;
import java.io.Serializable;

public class PGProc implements Serializable, XMLSerializable
{
    private static final long serialVersionUID = -353150828421011360L;
    String m_strProcPath;
    String m_strUnixProcPath;
    String m_strProcName;
    String m_strProcExt;
    String m_strProPath;
    String m_strUnixProPath;
    String m_strIID;
    String m_strCLSID;
    boolean m_bPersistent;
    boolean m_bUseFullName;
    int m_executionMode;
    private PGProcDetail m_pProcDetail;
    transient boolean m_bValidated;
    transient String m_strSchemaPrefix;
    transient String m_strSchemaNamespace;
    transient Binding m_bindingObj;
    transient PortType m_portTypeObj;
    private static transient PGGenInfo m_genInfo;
    public static final boolean m_isWindows;
    public static final String m_fileSep;
    
    public PGProc() {
        this.m_strProcPath = null;
        this.m_strUnixProcPath = null;
        this.m_strProcName = null;
        this.m_strProcExt = null;
        this.m_strProPath = null;
        this.m_strUnixProPath = null;
        this.m_strIID = null;
        this.m_strCLSID = null;
        this.m_bPersistent = false;
        this.m_bValidated = false;
        this.m_bUseFullName = false;
        this.m_pProcDetail = null;
        this.m_executionMode = 3;
    }
    
    public String getProcPath() {
        if (PGProc.m_isWindows) {
            return this.m_strProcPath;
        }
        return PGAppObj.fixOSPath(2, this.m_strProcPath);
    }
    
    public void setProcPath(final String s) {
        this.m_strProcPath = PGAppObj.fixOSPath(1, s);
    }
    
    public String getProcName() {
        return this.m_strProcName;
    }
    
    public void setProcName(final String strProcName) {
        this.m_strProcName = strProcName;
    }
    
    public String getProPath() {
        if (PGProc.m_isWindows) {
            return this.m_strProPath;
        }
        return PGAppObj.fixOSPath(2, this.m_strProPath);
    }
    
    public void setProPath(final String s) {
        this.m_strProPath = PGAppObj.fixOSPath(1, s);
    }
    
    public String getProcExt() {
        return this.m_strProcExt;
    }
    
    public void setProcExt(String substring) {
        if (substring.length() > 0 && substring.charAt(0) == '.') {
            substring = substring.substring(1);
        }
        this.m_strProcExt = substring;
    }
    
    public String getCLSID() {
        return this.m_strCLSID;
    }
    
    public void setCLSID(final String strCLSID) {
        this.m_strCLSID = strCLSID;
    }
    
    public String getIID() {
        return this.m_strIID;
    }
    
    public void setIID(final String strIID) {
        this.m_strIID = strIID;
    }
    
    public boolean isPersistent() {
        return this.m_bPersistent;
    }
    
    public void setPersistent(final boolean bPersistent) {
        this.m_bPersistent = bPersistent;
        if (bPersistent) {
            this.m_executionMode = 1;
        }
        else {
            this.m_executionMode = 3;
        }
    }
    
    public int getExecutionMode() {
        return this.m_executionMode;
    }
    
    public void setExecutionMode(final int executionMode) {
        if (executionMode >= 1 && executionMode <= 12) {
            this.m_executionMode = executionMode;
        }
        if (executionMode == 1) {
            this.m_bPersistent = true;
        }
        else {
            this.m_bPersistent = false;
        }
    }
    
    boolean isValidated() {
        return this.m_bValidated;
    }
    
    public void setValidated(final boolean bValidated) {
        this.m_bValidated = bValidated;
    }
    
    public boolean isUseFullName() {
        return this.m_bUseFullName;
    }
    
    public void setUseFullName(final boolean bUseFullName) {
        this.m_bUseFullName = bUseFullName;
    }
    
    public PGProcDetail getProcDetail() {
        PGGenInfo.setCurrentProcDetail(this.m_pProcDetail);
        return this.m_pProcDetail;
    }
    
    public void setProcDetail(final PGProcDetail pProcDetail) {
        this.m_pProcDetail = pProcDetail;
    }
    
    public int fillDetails(final PGAppObj pgAppObj, final PGGenInfo pgGenInfo, final boolean b, final PGMethod[] array) throws Open4GLException {
        int fillDetails = 1;
        String str = this.m_strProcName + ".r";
        if (this.getProcPath() != null) {
            str = this.getProcPath() + str;
        }
        if (this.getProPath() != null) {
            str = this.getProPath() + str;
        }
        if (!b && pgGenInfo != null) {
            String string = "";
            if (this.m_pProcDetail != null) {
                final String methodName = this.m_pProcDetail.getMethodName();
                if (!this.m_strProcName.equals(methodName)) {
                    String str2;
                    if (this.m_bPersistent) {
                        str2 = " (" + PGGenInfo.getResources().getTranString("PG_ProcObjName");
                    }
                    else {
                        str2 = " (" + PGGenInfo.getResources().getTranString("PG_MethodName");
                    }
                    string = str2 + ": " + methodName + ")";
                }
            }
            pgGenInfo.logIt(0, "PGLOG_Processing", str + string + " ...", 1);
        }
        if (this.m_bValidated) {
            if (pgGenInfo != null) {
                pgGenInfo.logIt(1, "PGLOG_NoVal", null, 1);
            }
            return fillDetails;
        }
        if (this.m_pProcDetail == null) {
            final String[] array2 = { null };
            final Holder holder = new Holder(null);
            final Holder holder2 = new Holder(null);
            String replace = "";
            this.m_pProcDetail = new PGProcDetail();
            if (this.m_bUseFullName) {
                replace = this.getProcPath().replace(PGProc.m_fileSep.charAt(0), '_');
            }
            this.m_pProcDetail.setMethodName(PGAppObj.ProNameToProxyName(replace + this.m_strProcName));
            RCodeParser.parseFile(str, this.m_bPersistent, array, pgGenInfo, array2, holder, holder2);
            this.m_strProcExt = array2[0];
            this.m_pProcDetail.setParameters((PGParam[])holder.getValue());
            if (this.m_pProcDetail.hasReferenceOnlyParameter()) {
                PGProc.m_genInfo.logIt(1, "PGLOG_ParsingError", null, 3);
                PGProc.m_genInfo.logIt(1, null, "Reference-only temp-tables and Prodatasets cannot be used as parameters for Open Clients", 3);
                fillDetails = 2;
            }
            if (this.m_bPersistent) {
                this.m_pProcDetail.setInternalProcs((PGMethod[])holder2.getValue());
                final PGMethod[] internalProcs = this.m_pProcDetail.getInternalProcs();
                for (int i = 0; i < internalProcs.length; ++i) {
                    final PGMethod pgMethod = internalProcs[i];
                    if (!pgMethod.hasBadParams()) {
                        if (pgMethod.hasReferenceOnlyParameter()) {
                            PGProc.m_genInfo.logIt(1, "PGLOG_ParsingError", null, 3);
                            PGProc.m_genInfo.logIt(1, null, "Reference-only temp-tables and Prodatasets cannot be used as parameters for Open Clients", 3);
                            fillDetails = 2;
                            break;
                        }
                    }
                    else {
                        fillDetails = 4;
                    }
                }
            }
            if (!b && pgGenInfo != null) {
                pgGenInfo.logIt(5, "PGLOG_NoCusts", null, 1);
            }
        }
        else {
            PGMethod[] internalProcs2 = null;
            if (this.m_bPersistent) {
                internalProcs2 = this.m_pProcDetail.getInternalProcs();
            }
            final PGProcDetail pProcDetail = this.m_pProcDetail;
            this.m_pProcDetail = null;
            fillDetails = this.fillDetails(pgAppObj, pgGenInfo, true, internalProcs2);
            if (pgGenInfo != null) {
                pgGenInfo.logIt(5, "PGLOG_Validating", "...", 1);
                pgGenInfo.logIt(2, "PGLOG_CustsApplied", null, 2);
            }
            final PGProcDetail pProcDetail2 = this.m_pProcDetail;
            this.m_pProcDetail = pProcDetail;
            if (!Validator.resolveDifferences(this.m_pProcDetail, pProcDetail2, pgAppObj, pgGenInfo) && fillDetails != 2) {
                fillDetails = 8;
            }
        }
        this.m_pProcDetail.updateTTMappings();
        this.m_bValidated = true;
        return fillDetails;
    }
    
    public static int updateProcPropath(final Vector vector, final String anObject, final String proPath) {
        int n = 0;
        for (int i = 0; i < vector.size(); ++i) {
            final PGProc pgProc = vector.elementAt(i);
            if (pgProc.m_strProPath.equals(anObject)) {
                pgProc.setProPath(proPath);
                ++n;
            }
        }
        return n;
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            if (this.m_strProcName != null) {
                String string;
                if (b) {
                    string = str + ":Name";
                }
                else {
                    string = "Name";
                }
                xmlSerializer.startElement(s, "Name", string, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strProcName.toCharArray(), 0, this.m_strProcName.length());
                xmlSerializer.endElement(s, "Name", string);
            }
            String string2;
            if (b) {
                string2 = str + ":ProcPath";
            }
            else {
                string2 = "ProcPath";
            }
            xmlSerializer.startElement(s, "ProcPath", string2, (Attributes)null);
            if (this.m_strProcPath != null) {
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strProcPath.toCharArray(), 0, this.m_strProcPath.length());
            }
            xmlSerializer.endElement(s, "ProcPath", string2);
            String string3;
            if (b) {
                string3 = str + ":ProPath";
            }
            else {
                string3 = "ProPath";
            }
            xmlSerializer.startElement(s, "ProPath", string3, (Attributes)null);
            if (this.m_strProPath != null) {
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strProPath.toCharArray(), 0, this.m_strProPath.length());
            }
            xmlSerializer.endElement(s, "ProPath", string3);
            if (this.m_strProcExt != null) {
                String string4;
                if (b) {
                    string4 = str + ":ProcExt";
                }
                else {
                    string4 = "ProcExt";
                }
                xmlSerializer.startElement(s, "ProcExt", string4, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strProcExt.toCharArray(), 0, this.m_strProcExt.length());
                xmlSerializer.endElement(s, "ProcExt", string4);
            }
            if (this.m_strCLSID != null) {
                String string5;
                if (b) {
                    string5 = str + ":CLSID";
                }
                else {
                    string5 = "CLSID";
                }
                xmlSerializer.startElement(s, "CLSID", string5, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strCLSID.toCharArray(), 0, this.m_strCLSID.length());
                xmlSerializer.endElement(s, "CLSID", string5);
            }
            if (this.m_strIID != null) {
                String string6;
                if (b) {
                    string6 = str + ":IID";
                }
                else {
                    string6 = "IID";
                }
                xmlSerializer.startElement(s, "IID", string6, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strIID.toCharArray(), 0, this.m_strIID.length());
                xmlSerializer.endElement(s, "IID", string6);
            }
            if (this.m_pProcDetail != null) {
                final AttributesImpl attributesImpl = new AttributesImpl();
                attributesImpl.addAttribute("", "isCustomized", "isCustomized", "CDATA", this.m_pProcDetail.isCustomized() ? "true" : "false");
                attributesImpl.addAttribute("", "usesUnknownDefault", "usesUnknownDefault", "CDATA", this.m_pProcDetail.usesUnknownDefault() ? "true" : "false");
                attributesImpl.addAttribute("", "useRetVal", "useRetVal", "CDATA", this.m_pProcDetail.usesReturnValue() ? "true" : "false");
                attributesImpl.addAttribute("", "usesTTMappingDefault", "usesTTMappingDefault", "CDATA", this.m_pProcDetail.usesTTMappingDefault() ? "true" : "false");
                attributesImpl.addAttribute("", "IsTTResultSet", "IsTTResultSet", "CDATA", this.m_pProcDetail.isTTResultSet() ? "true" : "false");
                attributesImpl.addAttribute("", "usesBeforeImageDefault", "usesBeforeImageDefault", "CDATA", this.m_pProcDetail.usesBeforeImageDefault() ? "true" : "false");
                String string7;
                if (b) {
                    string7 = str + ":ProcDetail";
                }
                else {
                    string7 = "ProcDetail";
                }
                xmlSerializer.startElement(s, "ProcDetail", string7, (Attributes)attributesImpl);
                this.m_pProcDetail.writeXML(xmlSerializer, s, str);
                xmlSerializer.endElement(s, "ProcDetail", string7);
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final NamedNodeMap attributes = node.getAttributes();
        final String nodeValue = attributes.getNamedItem("isPersistent").getNodeValue();
        if (nodeValue.equals("true") || nodeValue.equals("1")) {
            this.m_bPersistent = true;
        }
        else {
            this.m_bPersistent = false;
        }
        this.m_bValidated = false;
        final String nodeValue2 = attributes.getNamedItem("useFullName").getNodeValue();
        if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
            this.m_bUseFullName = true;
        }
        else {
            this.m_bUseFullName = false;
        }
        if (this.m_bPersistent) {
            this.m_executionMode = 1;
        }
        else {
            this.m_executionMode = 3;
        }
        final Node namedItem = attributes.getNamedItem("executionMode");
        if (null != namedItem) {
            try {
                this.setExecutionMode(Integer.parseInt(namedItem.getNodeValue()));
            }
            catch (NumberFormatException ex) {}
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue3 = WsaParser.extractNodeValue(item);
                if (localName.equals("Name")) {
                    this.m_strProcName = nodeValue3;
                }
                else if (localName.equals("ProcPath")) {
                    this.m_strProcPath = nodeValue3;
                }
                else if (localName.equals("ProPath")) {
                    this.m_strProPath = nodeValue3;
                }
                else if (localName.equals("ProcExt")) {
                    this.m_strProcExt = nodeValue3;
                }
                else if (localName.equals("CLSID")) {
                    this.m_strCLSID = nodeValue3;
                }
                else if (localName.equals("IID")) {
                    this.m_strIID = nodeValue3;
                }
                else if (localName.equals("ProcDetail")) {
                    (this.m_pProcDetail = new PGProcDetail()).readXML(item);
                }
            }
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.m_pProcDetail != null && !this.m_pProcDetail.isCustomized()) {
            final PGProcDetail pProcDetail = this.m_pProcDetail;
            this.m_pProcDetail = null;
            objectOutputStream.defaultWriteObject();
            this.m_pProcDetail = pProcDetail;
        }
        else {
            objectOutputStream.defaultWriteObject();
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.m_bValidated = false;
        objectInputStream.defaultReadObject();
    }
    
    public boolean hasParamType(final int n, final boolean b) {
        final boolean hasParamType = this.m_pProcDetail.hasParamType(n, b);
        return hasParamType && hasParamType;
    }
    
    public boolean hasDatasetHandleParam(final boolean b) {
        return this.m_pProcDetail.hasDatasetHandleParam(b);
    }
    
    public boolean hasDatasetHandleChangesParam(final boolean b) {
        return this.m_pProcDetail.hasDatasetHandleChangesParam(b);
    }
    
    public boolean hasDatasetAttributeFields(final boolean b) {
        return this.m_pProcDetail.hasDatasetAttributeFields(b);
    }
    
    public boolean hasDatasetTtabNamespaceConflict(final boolean b) {
        return this.m_pProcDetail.hasDatasetTtabNamespaceConflict(b);
    }
    
    public boolean hasArray(final boolean b) {
        final boolean hasArray = this.m_pProcDetail.hasArray(b);
        return hasArray && hasArray;
    }
    
    public String getSchemaPrefix() {
        return this.m_strSchemaPrefix;
    }
    
    public void setSchemaPrefix(final String strSchemaPrefix) {
        this.m_strSchemaPrefix = strSchemaPrefix;
    }
    
    public String getSchemaNamespace() {
        return this.m_strSchemaNamespace;
    }
    
    public void setSchemaNamespace(final String strSchemaNamespace) {
        this.m_strSchemaNamespace = strSchemaNamespace;
    }
    
    public Binding getBindingObj() {
        return this.m_bindingObj;
    }
    
    public void setBindingObj(final Binding bindingObj) {
        this.m_bindingObj = bindingObj;
    }
    
    public PortType getPortTypeObj() {
        return this.m_portTypeObj;
    }
    
    public void setPortTypeObj(final PortType portTypeObj) {
        this.m_portTypeObj = portTypeObj;
    }
    
    public static PGGenInfo getGenInfo() {
        return PGProc.m_genInfo;
    }
    
    public static void setGenInfo(final PGGenInfo genInfo) {
        PGProc.m_genInfo = genInfo;
    }
    
    public boolean isSame(final PGProc pgProc) {
        if (this.m_strProcName.equals(pgProc.getProcName())) {
            final PGParam[] parameters = this.m_pProcDetail.getParameters();
            final PGParam[] parameters2 = pgProc.m_pProcDetail.getParameters();
            if (parameters.length == parameters2.length) {
                for (int i = 0; i < parameters.length; ++i) {
                    if (parameters[i].getParamType() != parameters2[i].getParamType() || !parameters[i].getParamName().equals(parameters2[i].getParamName())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public PscDeploymentDescriptor generateDeploymentDescriptor(final WsaSOAPEngineContext wsaSOAPEngineContext, final AppContainer appContainer, final DWGenInfo dwGenInfo) {
        final Hashtable<String, MethodDescriptor> hashtable = new Hashtable<String, MethodDescriptor>();
        final String webServiceNameSpace = dwGenInfo.getWebServiceNameSpace();
        final StringBuffer sb = new StringBuffer(webServiceNameSpace.length() + 32);
        final boolean startsWith = webServiceNameSpace.startsWith("urn:");
        final int encoding = dwGenInfo.getEncoding();
        final PscDeploymentDescriptor pscDeploymentDescriptor = new PscDeploymentDescriptor();
        sb.append(webServiceNameSpace);
        if (startsWith) {
            sb.append(":");
        }
        else {
            sb.append("/");
        }
        sb.append(this.m_pProcDetail.getMethodName());
        final String string = sb.toString();
        pscDeploymentDescriptor.setID(string);
        pscDeploymentDescriptor.setProgressObjectName(this.m_pProcDetail.getMethodName());
        if (encoding == 3) {
            pscDeploymentDescriptor.setServiceType(1);
        }
        else {
            pscDeploymentDescriptor.setServiceType(0);
        }
        final PGMethod[] internalProcs = this.m_pProcDetail.getInternalProcs();
        for (int length = internalProcs.length, i = 0; i < length; ++i) {
            final PGMethodDetail methodDetail = internalProcs[i].getMethodDetail();
            final PGParam[] parameters = methodDetail.getParameters();
            final int length2 = parameters.length;
            final MethodParam[] array = new MethodParam[length2];
            for (int j = 0; j < length2; ++j) {
                final PGMetaData[] metaData = parameters[j].getMetaData();
                final int length3;
                MethodParam methodParam;
                if (null == metaData || 0 == (length3 = metaData.length)) {
                    final String paramName = parameters[j].getParamName();
                    final int paramType = parameters[j].getParamType();
                    QName qName;
                    if (paramType == 36 && parameters[j] instanceof PGDataSetParam) {
                        final String xmlNodeName = ((PGDataSetParam)parameters[j]).getXmlNodeName();
                        if (xmlNodeName != null) {
                            qName = new QName("", xmlNodeName);
                        }
                        else {
                            qName = new QName("", paramName);
                        }
                    }
                    else {
                        qName = new QName("", paramName);
                    }
                    methodParam = new MethodParam(qName, Parameter.proToJavaClassObject(paramType), paramType, parameters[j].getParamOrdinal(), parameters[j].getParamMode(), parameters[j].allowUnknown(), parameters[j].getWriteBeforeImage(), parameters[j].isExtentField(), parameters[j].getExtent());
                }
                else {
                    final ResultSetMetaData resultSetMetaData = new ResultSetMetaData(0, length3);
                    for (int k = 0; k < length3; ++k) {
                        resultSetMetaData.setFieldDesc(k + 1, metaData[k].getFieldName(), metaData[k].getExtent(), metaData[k].getType());
                    }
                    methodParam = new MethodParam(new QName("", parameters[j].getParamName()), Parameter.proToJavaClassObject(parameters[j].getParamType()), parameters[j].getParamType(), parameters[j].getParamOrdinal(), parameters[j].getParamMode(), parameters[j].allowUnknown(), parameters[j].getWriteBeforeImage(), resultSetMetaData);
                }
                array[j] = methodParam;
            }
            int n;
            Class<Void> clazz;
            int paramType2;
            boolean allowUnknown;
            boolean extentField;
            int extent;
            if (1 == internalProcs[i].getProcType()) {
                n = 7;
                if (methodDetail.usesReturnValue()) {
                    clazz = (Class<Void>)String.class;
                    paramType2 = 1;
                }
                else {
                    clazz = Void.TYPE;
                    paramType2 = 0;
                }
                allowUnknown = false;
                extentField = false;
                extent = 0;
            }
            else {
                n = 4;
                clazz = (Class<Void>)Parameter.proToJavaClassObject(methodDetail.getReturnValue().getParamType());
                final PGParam returnValue = methodDetail.getReturnValue();
                paramType2 = returnValue.getParamType();
                allowUnknown = returnValue.allowUnknown();
                extentField = returnValue.isExtentField();
                extent = returnValue.getExtent();
            }
            final String internalProc = internalProcs[i].getInternalProc();
            final QName qName2 = new QName(string, methodDetail.getMethodName());
            hashtable.put(qName2.toString(), new MethodDescriptor(qName2, internalProc, null, n, new MethodParam(new QName("", "result"), clazz, paramType2, 0, 0, allowUnknown, false, extentField, extent), array));
        }
        final MethodParam[] array2 = new MethodParam[0];
        final String string2 = "Release_" + this.m_strProcName;
        final QName qName3 = new QName(string, "Release_" + this.m_pProcDetail.getMethodName());
        hashtable.put(qName3.toString(), new MethodDescriptor(qName3, string2, null, 1, new MethodParam(new QName("", ""), Void.TYPE, 0, 0, 0, false, false, false, 0), array2));
        try {
            pscDeploymentDescriptor.initRuntime(wsaSOAPEngineContext, hashtable, appContainer);
        }
        catch (Exception ex) {}
        return pscDeploymentDescriptor;
    }
    
    public MethodDescriptor generateMethodDescriptor(final String s, final int n) {
        final PGParam[] parameters = this.m_pProcDetail.getParameters();
        final int length = parameters.length;
        final MethodParam[] array = new MethodParam[length];
        for (int i = 0; i < length; ++i) {
            final PGMetaData[] metaData = parameters[i].getMetaData();
            final int length2;
            MethodParam methodParam;
            if (null == metaData || 0 == (length2 = metaData.length)) {
                String paramName = parameters[i].getParamName();
                final int paramType = parameters[i].getParamType();
                QName qName;
                if (paramType == 36 && parameters[i] instanceof PGDataSetParam) {
                    final PGDataSetParam pgDataSetParam = (PGDataSetParam)parameters[i];
                    final String className = pgDataSetParam.getClassName();
                    if (null != className && 3 == n) {
                        paramName = className;
                    }
                    final String xmlNodeName = pgDataSetParam.getXmlNodeName();
                    if (xmlNodeName != null) {
                        qName = new QName("", xmlNodeName);
                    }
                    else {
                        qName = new QName("", paramName);
                    }
                }
                else {
                    qName = new QName("", paramName);
                }
                methodParam = new MethodParam(qName, Parameter.proToJavaClassObject(paramType), paramType, parameters[i].getParamOrdinal(), parameters[i].getParamMode(), parameters[i].allowUnknown(), parameters[i].getWriteBeforeImage(), parameters[i].isExtentField(), parameters[i].getExtent());
            }
            else {
                final ResultSetMetaData resultSetMetaData = new ResultSetMetaData(0, length2);
                for (int j = 0; j < length2; ++j) {
                    resultSetMetaData.setFieldDesc(j + 1, metaData[j].getFieldName(), metaData[j].getExtent(), metaData[j].getType());
                }
                methodParam = new MethodParam(new QName("", parameters[i].getParamName()), Parameter.proToJavaClassObject(parameters[i].getParamType()), parameters[i].getParamType(), parameters[i].getParamOrdinal(), parameters[i].getParamMode(), parameters[i].allowUnknown(), parameters[i].getWriteBeforeImage(), resultSetMetaData);
            }
            array[i] = methodParam;
        }
        String str;
        if (this.m_strProcPath != null) {
            str = new String(this.m_strProcPath);
        }
        else {
            str = "";
        }
        return new MethodDescriptor(new QName(s, this.m_pProcDetail.getMethodName()), str + this.m_strProcName, null, 3, new MethodParam(new QName("", "result"), (Class)(this.m_pProcDetail.usesReturnValue() ? String.class : Void.TYPE), this.m_pProcDetail.usesReturnValue() ? 1 : 0, 0, 0, false, false, false, 0), array);
    }
    
    static {
        PGProc.m_genInfo = null;
        m_isWindows = (System.getProperty("os.name").indexOf("Windows") >= 0);
        m_fileSep = System.getProperty("file.separator");
    }
}
