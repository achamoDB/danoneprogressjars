// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import java.util.Vector;
import org.apache.xerces.dom.CoreDocumentImpl;
import javax.wsdl.Definition;
import javax.wsdl.PortType;

public class WSDLGenInfo
{
    private String m_strXSDSchemaNamespace;
    private String m_strDataTypeNamespace;
    private String m_strSoapNamespace;
    private String m_strSoapEncNamespace;
    private String m_strDefaultNamespace;
    private String m_strWSDLNamespace;
    private String m_strFaultNamespace;
    private String m_strProdataNamespace;
    private int m_schemaPrefixCtr;
    private String m_strMessagePrefix;
    private String m_strTypePrefix;
    private String m_strBindingStyleAttr;
    private String m_strBindingTransportAttr;
    private String m_strBindingUseAttr;
    private String m_strBindingEncodingStyleAttr;
    private DWGenInfo m_dwGenInfo;
    private String m_strCurSchemaNamespace;
    private String m_strCurSchemaPrefix;
    private PortType m_curPortTypeObj;
    private String m_strCurObjectName;
    private Definition m_defObj;
    private boolean m_bUsePrefixForTempTables;
    private CoreDocumentImpl m_coreDocImpl;
    private String m_strProductName;
    private String m_strProductVersion;
    private Vector m_vTempTableList;
    private String m_strFaultDetailSchemaPrefix;
    private Vector m_vDataSetsCreated;
    private Vector m_vDataSetChangesCreated;
    private Vector m_vNamespacesImported;
    private Vector m_vDataSetNspaceNames;
    private Vector m_vDataSetNspacePrefixes;
    private Vector m_vDataSetsWithNamespace;
    private Vector m_vUniqueIdxNames;
    private Vector m_vKeyrefNames;
    
    public WSDLGenInfo() {
        this.m_strXSDSchemaNamespace = "";
        this.m_strDataTypeNamespace = "";
        this.m_strSoapNamespace = "";
        this.m_strSoapEncNamespace = "";
        this.m_strDefaultNamespace = "";
        this.m_strWSDLNamespace = "";
        this.m_schemaPrefixCtr = 1;
        this.m_strMessagePrefix = null;
        this.m_strTypePrefix = null;
        this.m_strBindingStyleAttr = "";
        this.m_strBindingTransportAttr = "";
        this.m_strBindingUseAttr = "";
        this.m_strBindingEncodingStyleAttr = "";
        this.m_dwGenInfo = null;
        this.m_strCurSchemaNamespace = "";
        this.m_strCurSchemaPrefix = "";
        this.m_curPortTypeObj = null;
        this.m_strCurObjectName = null;
        this.m_defObj = null;
        this.m_coreDocImpl = null;
        this.m_bUsePrefixForTempTables = true;
        this.m_strFaultDetailSchemaPrefix = "";
        this.m_vDataSetsCreated = null;
        this.m_vDataSetChangesCreated = null;
        this.m_vNamespacesImported = null;
        this.m_vTempTableList = null;
        this.m_vDataSetNspaceNames = null;
        this.m_vDataSetNspacePrefixes = null;
        this.m_vDataSetsWithNamespace = null;
    }
    
    public String getTargetNamespace() {
        return this.m_dwGenInfo.getWebServiceNameSpace();
    }
    
    public String getXSDSchemaNamespace() {
        return this.m_strXSDSchemaNamespace;
    }
    
    public void setXSDSchemaNamespace(final String strXSDSchemaNamespace) {
        this.m_strXSDSchemaNamespace = strXSDSchemaNamespace;
    }
    
    public String getDataTypeNamespace() {
        return this.m_strDataTypeNamespace;
    }
    
    public void setDataTypeNamespace(final String strDataTypeNamespace) {
        this.m_strDataTypeNamespace = strDataTypeNamespace;
    }
    
    public String getSoapNamespace() {
        return this.m_strSoapNamespace;
    }
    
    public void setSoapNamespace(final String strSoapNamespace) {
        this.m_strSoapNamespace = strSoapNamespace;
    }
    
    public String getSoapEncNamespace() {
        return this.m_strSoapEncNamespace;
    }
    
    public void setSoapEncNamespace(final String strSoapEncNamespace) {
        this.m_strSoapEncNamespace = strSoapEncNamespace;
    }
    
    public String getWSDLNamespace() {
        return this.m_strWSDLNamespace;
    }
    
    public void setWSDLNamespace(final String strWSDLNamespace) {
        this.m_strWSDLNamespace = strWSDLNamespace;
    }
    
    public String getDefaultNamespace() {
        return this.m_strDefaultNamespace;
    }
    
    public void setDefaultNamespace(final String strDefaultNamespace) {
        this.m_strDefaultNamespace = strDefaultNamespace;
    }
    
    public String getFaultNamespace() {
        return this.m_strFaultNamespace;
    }
    
    public void setFaultNamespace(final String strFaultNamespace) {
        this.m_strFaultNamespace = strFaultNamespace;
    }
    
    public String getProdataNamespace() {
        return this.m_strProdataNamespace;
    }
    
    public void setProdataNamespace(final String strProdataNamespace) {
        this.m_strProdataNamespace = strProdataNamespace;
    }
    
    public String getUniqueSchemaPrefix() {
        return "S" + Integer.toString(this.m_schemaPrefixCtr);
    }
    
    public void updateUniqueSchemaPrefix() {
        ++this.m_schemaPrefixCtr;
    }
    
    public String getMessagePrefix() {
        return this.m_strMessagePrefix;
    }
    
    public void setMessagePrefix(final String strMessagePrefix) {
        this.m_strMessagePrefix = strMessagePrefix;
    }
    
    public String getTypePrefix() {
        return this.m_strTypePrefix;
    }
    
    public void setTypePrefix(final String strTypePrefix) {
        this.m_strTypePrefix = strTypePrefix;
    }
    
    public String getBindingStyleAttr() {
        return this.m_strBindingStyleAttr;
    }
    
    public void setBindingStyleAttr(final String strBindingStyleAttr) {
        this.m_strBindingStyleAttr = strBindingStyleAttr;
    }
    
    public String getBindingTransportAttr() {
        return this.m_strBindingTransportAttr;
    }
    
    public void setBindingTransportAttr(final String strBindingTransportAttr) {
        this.m_strBindingTransportAttr = strBindingTransportAttr;
    }
    
    public String getBindingUseAttr() {
        return this.m_strBindingUseAttr;
    }
    
    public void setBindingUseAttr(final String strBindingUseAttr) {
        this.m_strBindingUseAttr = strBindingUseAttr;
    }
    
    public String getBindingEncodingStyleAttr() {
        return this.m_strBindingEncodingStyleAttr;
    }
    
    public void setBindingEncodingStyleAttr(final String strBindingEncodingStyleAttr) {
        this.m_strBindingEncodingStyleAttr = strBindingEncodingStyleAttr;
    }
    
    public DWGenInfo getDWGenInfo() {
        return this.m_dwGenInfo;
    }
    
    public void setDWGenInfo(final DWGenInfo dwGenInfo) {
        this.m_dwGenInfo = dwGenInfo;
    }
    
    public String getCurrentSchemaNamespace() {
        return this.m_strCurSchemaNamespace;
    }
    
    public void setCurrentSchemaNamespace(final String strCurSchemaNamespace) {
        this.m_strCurSchemaNamespace = strCurSchemaNamespace;
    }
    
    public String getFaultDetailSchemaPrefix() {
        return this.m_strFaultDetailSchemaPrefix;
    }
    
    public void setFaultDetailSchemaPrefix(final String strFaultDetailSchemaPrefix) {
        this.m_strFaultDetailSchemaPrefix = strFaultDetailSchemaPrefix;
    }
    
    public boolean usePrefixForTempTables() {
        return this.m_bUsePrefixForTempTables;
    }
    
    public void setUsePrefixForTempTables(final boolean bUsePrefixForTempTables) {
        this.m_bUsePrefixForTempTables = bUsePrefixForTempTables;
    }
    
    public String getCurrentSchemaPrefix() {
        return this.m_strCurSchemaPrefix;
    }
    
    public void setCurrentSchemaPrefix(final String strCurSchemaPrefix) {
        this.m_strCurSchemaPrefix = strCurSchemaPrefix;
    }
    
    public PortType getCurrentPortTypeObj() {
        return this.m_curPortTypeObj;
    }
    
    public void setCurrentPortTypeObj(final PortType curPortTypeObj) {
        this.m_curPortTypeObj = curPortTypeObj;
    }
    
    public String getCurrentObjectName() {
        return this.m_strCurObjectName;
    }
    
    public void setCurrentObjectName(final String strCurObjectName) {
        this.m_strCurObjectName = strCurObjectName;
    }
    
    public Definition getDefinitionObj() {
        return this.m_defObj;
    }
    
    public void setDefinitionObj(final Definition defObj) {
        this.m_defObj = defObj;
    }
    
    public CoreDocumentImpl getCoreDocImpl() {
        return this.m_coreDocImpl;
    }
    
    public void setCoreDocImpl(final CoreDocumentImpl coreDocImpl) {
        this.m_coreDocImpl = coreDocImpl;
    }
    
    public String getProductName() {
        return this.m_strProductName;
    }
    
    public void setProductName(final String strProductName) {
        this.m_strProductName = strProductName;
    }
    
    public String getProductVersion() {
        return this.m_strProductVersion;
    }
    
    public void setProductVersion(final String strProductVersion) {
        this.m_strProductVersion = strProductVersion;
    }
    
    public Vector getTempTableList() {
        return this.m_vTempTableList;
    }
    
    public void setTempTableList(final Vector vTempTableList) {
        this.m_vTempTableList = vTempTableList;
    }
    
    public Vector getDataSetsCreatedList() {
        return this.m_vDataSetsCreated;
    }
    
    public void setDataSetsCreatedList(final Vector vDataSetsCreated) {
        this.m_vDataSetsCreated = vDataSetsCreated;
    }
    
    public Vector getDataSetChangessCreatedList() {
        return this.m_vDataSetChangesCreated;
    }
    
    public void setDataSetChangesCreatedList(final Vector vDataSetChangesCreated) {
        this.m_vDataSetChangesCreated = vDataSetChangesCreated;
    }
    
    public Vector getNamespacesImportedList() {
        return this.m_vNamespacesImported;
    }
    
    public void setNamespacesImportedList(final Vector vNamespacesImported) {
        this.m_vNamespacesImported = vNamespacesImported;
    }
    
    public Vector getDataSetNspaceNames() {
        return this.m_vDataSetNspaceNames;
    }
    
    public Vector getDataSetNspacePrefixes() {
        return this.m_vDataSetNspacePrefixes;
    }
    
    public Vector getDataSetsWithNamespace() {
        return this.m_vDataSetsWithNamespace;
    }
    
    public Vector getUniqueIndexNames() {
        return this.m_vUniqueIdxNames;
    }
    
    public void setUniqueIndexNames(final Vector vUniqueIdxNames) {
        this.m_vUniqueIdxNames = vUniqueIdxNames;
    }
    
    public Vector getKeyrefNames() {
        return this.m_vKeyrefNames;
    }
    
    public void setKeyRefNames(final Vector vKeyrefNames) {
        this.m_vKeyrefNames = vKeyrefNames;
    }
    
    public void init(final int n) {
        this.m_strXSDSchemaNamespace = "http://www.w3.org/2001/XMLSchema";
        this.m_strSoapNamespace = "http://schemas.xmlsoap.org/wsdl/soap/";
        this.m_strSoapEncNamespace = "http://schemas.xmlsoap.org/soap/encoding/";
        this.m_strWSDLNamespace = "http://schemas.xmlsoap.org/wsdl/";
        this.m_strFaultNamespace = "urn:soap-fault:details";
        this.m_strProdataNamespace = "urn:schemas-progress-com:xml-prodata:0001";
        this.m_strDataTypeNamespace = this.getTargetNamespace() + "/Schema";
        this.m_strBindingTransportAttr = "http://schemas.xmlsoap.org/soap/http";
        this.m_vDataSetNspaceNames = new Vector();
        this.m_vDataSetNspacePrefixes = new Vector();
        this.m_vDataSetsWithNamespace = new Vector();
        switch (n) {
            case 1: {
                this.m_strBindingStyleAttr = "rpc";
                this.m_strBindingUseAttr = "encoded";
                this.m_strBindingEncodingStyleAttr = "http://schemas.xmlsoap.org/soap/encoding/";
                break;
            }
            case 2: {
                this.m_strBindingStyleAttr = "rpc";
                this.m_strBindingUseAttr = "literal";
                this.m_strBindingEncodingStyleAttr = null;
                break;
            }
            case 3: {
                this.m_strBindingStyleAttr = "document";
                this.m_strBindingUseAttr = "literal";
                this.m_strBindingEncodingStyleAttr = null;
                break;
            }
        }
    }
}
