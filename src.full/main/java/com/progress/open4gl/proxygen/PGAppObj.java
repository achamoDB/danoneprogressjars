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
import com.progress.wsa.admin.AppContainer;
import com.progress.wsa.WsaSOAPEngineContext;
import org.apache.xerces.dom.CoreDocumentImpl;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import org.w3c.dom.NodeList;
import com.progress.wsa.admin.WsaParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.OutputStreamWriter;
import com.progress.open4gl.wsdlgen.WSDLGenInfo;
import java.io.Writer;
import com.progress.open4gl.wsdlgen.WSDLDefinitionObj;
import java.io.FileWriter;
import javax.wsdl.WSDLException;
import java.io.IOException;
import java.io.File;
import java.util.zip.ZipOutputStream;
import java.io.BufferedOutputStream;
import java.util.zip.Checksum;
import java.io.OutputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Adler32;
import java.io.FileOutputStream;
import com.progress.wsa.admin.WSAD;
import com.progress.common.util.ProgressVersion;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.wsdlgen.DWGenInfo;
import javax.wsdl.PortType;
import javax.wsdl.Binding;
import java.util.Vector;
import com.progress.wsa.open4gl.XMLSerializable;
import com.progress.message.pgMsg;
import java.io.Serializable;

public class PGAppObj implements Serializable, pgMsg, XMLSerializable
{
    private static final long serialVersionUID = -353150832715978656L;
    String m_strAppObj;
    String m_strProPath;
    String m_strUnixProPath;
    String m_strHelp;
    String m_strODLHelp;
    String m_strTypeID;
    String m_strIID;
    String m_strCLSID;
    boolean m_bSubObject;
    boolean m_bAllowUnknown;
    Vector m_vSubObjs;
    Vector m_vProcs;
    Vector m_vPerProcs;
    transient boolean m_bIsTTResultSet;
    boolean m_bWriteXmlBeforeImage;
    transient PGAppObj[] m_pSubObjs;
    transient PGProc[] m_pProcs;
    transient PGProc[] m_pPerProcs;
    transient boolean m_bDirty;
    transient String m_strSchemaPrefix;
    transient String m_strSchemaNamespace;
    transient Binding m_bindingObj;
    transient PortType m_portTypeObj;
    transient WSInfo m_wsInfo;
    private static PGGenInfo m_genInfo;
    private static PGVersion m_version;
    public static final boolean m_isWindows;
    public static final int GENVAL_SUCCESS = 1;
    public static final int GENVAL_FAIL = 2;
    public static final int GENVAL_WARNING = 4;
    public static final int GENVAL_LOSTINFO = 8;
    public static final int OS_WINDOWS = 1;
    public static final int OS_UNIX = 2;
    public static final int OS_CURRENT = 3;
    private static String[] m_reservedWords;
    private static String[] m_dotNetReservedWords;
    
    public PGAppObj() {
        this.m_strAppObj = null;
        this.m_strProPath = null;
        this.m_strUnixProPath = null;
        this.m_strHelp = null;
        this.m_strODLHelp = null;
        this.m_strTypeID = null;
        this.m_strIID = null;
        this.m_strCLSID = null;
        this.m_bSubObject = false;
        this.m_bAllowUnknown = false;
        this.m_pSubObjs = new PGAppObj[0];
        this.m_pProcs = new PGProc[0];
        this.m_pPerProcs = new PGProc[0];
        this.m_vSubObjs = new Vector();
        this.m_vProcs = new Vector();
        this.m_vPerProcs = new Vector();
        this.m_bDirty = false;
        this.m_wsInfo = null;
        this.m_bIsTTResultSet = true;
        this.m_bWriteXmlBeforeImage = false;
    }
    
    public String getAppObjectName() {
        return this.m_strAppObj;
    }
    
    public void setAppObjectName(final String s) {
        if (this.m_strAppObj == null || !this.m_strAppObj.equals(s)) {
            this.m_bDirty = true;
            this.m_strAppObj = s;
        }
    }
    
    public String valAppObjectName(final String s) {
        String s2 = null;
        boolean b = false;
        boolean b2 = false;
        if (PGAppObj.m_genInfo.genJavaProxy() || PGAppObj.m_genInfo.genActiveXProxy()) {
            b = true;
        }
        if (PGAppObj.m_genInfo.genDotNetProxy()) {
            b2 = true;
        }
        if (s == null || s.length() == 0) {
            s2 = PGGenInfo.getMsg(8099442454849133699L, new Object[] { PGGenInfo.getResources().getTranString("PG_Name") });
        }
        else if (!validateName(s, b, b2, false)) {
            s2 = PGGenInfo.getMsg(8099442454849133701L, new Object[] { PGGenInfo.getResources().getTranString("PG_Name") });
        }
        return s2;
    }
    
    public String getProPath() {
        return this.m_strProPath;
    }
    
    public void setProPath(final String anObject) {
        if (this.m_strProPath == null || !this.m_strProPath.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strProPath = fixOSPaths(1, anObject);
        }
    }
    
    public String getHelpString() {
        return this.m_strHelp;
    }
    
    public void setHelpString(String removeReturns) {
        if (removeReturns != null) {
            removeReturns = removeReturns(removeReturns);
        }
        if (this.m_strHelp == null || (this.m_strHelp != null && !this.m_strHelp.equals(removeReturns))) {
            this.m_bDirty = true;
            this.m_strHelp = removeReturns;
        }
    }
    
    public String getODLHelpString() {
        return this.m_strODLHelp;
    }
    
    public void setODLHelpString(final String s) {
        if (this.m_strODLHelp == null || (this.m_strODLHelp != null && !this.m_strODLHelp.equals(s))) {
            this.m_bDirty = true;
            this.m_strODLHelp = s;
        }
    }
    
    public String getTypeID() {
        return this.m_strTypeID;
    }
    
    public void setTypeID(final String s) {
        if (this.m_strTypeID == null || (this.m_strTypeID != null && !this.m_strTypeID.equals(s))) {
            this.m_bDirty = true;
            this.m_strTypeID = s;
        }
    }
    
    public String getIID() {
        return this.m_strIID;
    }
    
    public void setIID(final String s) {
        if (this.m_strIID == null || (this.m_strIID != null && !this.m_strIID.equals(s))) {
            this.m_bDirty = true;
            this.m_strIID = s;
        }
    }
    
    public String getCLSID() {
        return this.m_strCLSID;
    }
    
    public void setCLSID(final String s) {
        if (this.m_strCLSID == null || (this.m_strCLSID != null && !this.m_strCLSID.equals(s))) {
            this.m_bDirty = true;
            this.m_strCLSID = s;
        }
    }
    
    public boolean isSubAppObject() {
        return this.m_bSubObject;
    }
    
    public void setSubAppObject(final boolean bSubObject) {
        if (this.m_bSubObject != bSubObject) {
            this.m_bDirty = true;
            this.m_bSubObject = bSubObject;
        }
    }
    
    public boolean allowUnknown() {
        return this.m_bAllowUnknown;
    }
    
    public void setAllowUnknown(final boolean bAllowUnknown) {
        if (this.m_bAllowUnknown != bAllowUnknown) {
            this.m_bDirty = true;
            this.m_bAllowUnknown = bAllowUnknown;
        }
    }
    
    public boolean isTTResultSet() {
        return this.m_bIsTTResultSet;
    }
    
    public void setWriteBeforeImage(final boolean bWriteXmlBeforeImage) {
        this.m_bWriteXmlBeforeImage = bWriteXmlBeforeImage;
    }
    
    public boolean getWriteBeforeImage() {
        return this.m_bWriteXmlBeforeImage;
    }
    
    public PGAppObj[] getSubObjects() {
        return this.m_pSubObjs;
    }
    
    public void setSubObjects(final PGAppObj[] pSubObjs) {
        this.m_bDirty = true;
        this.m_pSubObjs = pSubObjs;
    }
    
    public PGAppObj getSubObjects(final int n) {
        if (n >= this.m_pSubObjs.length) {
            return null;
        }
        return this.m_pSubObjs[n];
    }
    
    public void setSubObjects(final int n, final PGAppObj pgAppObj) {
        if (n < this.m_pSubObjs.length) {
            this.m_bDirty = true;
            this.m_pSubObjs[n] = pgAppObj;
        }
    }
    
    public PGProc[] getProcedures() {
        return this.m_pProcs;
    }
    
    public void setProcedures(final PGProc[] pProcs) {
        this.m_bDirty = true;
        this.m_pProcs = pProcs;
    }
    
    public PGProc getProcedures(final int n) {
        if (n >= this.m_pProcs.length) {
            return null;
        }
        return this.m_pProcs[n];
    }
    
    public void setProcedures(final int n, final PGProc pgProc) {
        if (n < this.m_pProcs.length) {
            this.m_pProcs[n] = pgProc;
            this.m_bDirty = true;
        }
    }
    
    public PGProc[] getPersistentProcedures() {
        return this.m_pPerProcs;
    }
    
    public void setPersistentProcedures(final PGProc[] pPerProcs) {
        this.m_bDirty = true;
        this.m_pPerProcs = pPerProcs;
    }
    
    public PGProc getPersistentProcedures(final int n) {
        if (n >= this.m_pPerProcs.length) {
            return null;
        }
        return this.m_pPerProcs[n];
    }
    
    public void setPersistentProcedures(final int n, final PGProc pgProc) {
        if (n < this.m_pPerProcs.length) {
            this.m_pPerProcs[n] = pgProc;
            this.m_bDirty = true;
        }
    }
    
    public Vector getSubObjectVector() {
        return this.m_vSubObjs;
    }
    
    public Vector getProcVector() {
        return this.m_vProcs;
    }
    
    public void setProcVector(final Vector vProcs) {
        this.m_vProcs = vProcs;
    }
    
    public Vector getPerProcVector() {
        return this.m_vPerProcs;
    }
    
    public void setPerProcVector(final Vector vPerProcs) {
        this.m_vPerProcs = vPerProcs;
    }
    
    public boolean isDirty() {
        return this.m_bDirty;
    }
    
    public void setDirty(final boolean bDirty) {
        this.m_bDirty = bDirty;
    }
    
    public void clearDirty() {
        this.m_bDirty = false;
        for (int i = 0; i < this.m_vSubObjs.size(); ++i) {
            ((PGAppObj)this.m_vSubObjs.elementAt(i)).clearDirty();
        }
    }
    
    public WSInfo getWSInfo() {
        return this.m_wsInfo;
    }
    
    public DWGenInfo getDWInfo() {
        if (this.m_wsInfo != null) {
            return this.m_wsInfo.getDWGenInfo();
        }
        if (PGAppObj.m_genInfo != null) {
            return PGAppObj.m_genInfo.getDWGenInfo();
        }
        return null;
    }
    
    public static PGGenInfo getGenInfo() {
        return PGAppObj.m_genInfo;
    }
    
    public static void setGenInfo(final PGGenInfo genInfo) {
        PGAppObj.m_genInfo = genInfo;
    }
    
    public static PGVersion getPGVersion() {
        return PGAppObj.m_version;
    }
    
    public int generate() throws Open4GLException {
        int validate = 0;
        int n = 1;
        PGAppObj.m_genInfo.openLogFile(this.m_strAppObj);
        try {
            if (PGAppObj.m_genInfo.genWebServicesProxy()) {
                this.setWebServicesEncoding();
            }
            validate = this.validate(true);
            if ((validate & 0x2) == 0x0) {
                if (n != 0 && PGAppObj.m_genInfo.genJavaProxy()) {
                    n = (this.buildJavaProxy() ? 1 : 0);
                }
                if (n != 0 && PGAppObj.m_genInfo.genDotNetProxy()) {
                    n = (this.buildDotNetProxy() ? 1 : 0);
                }
                if (n != 0 && PGAppObj.m_genInfo.genWebServicesProxy()) {
                    n = (this.buildWebServicesProxy() ? 1 : 0);
                }
                if (n != 0 && PGAppObj.m_genInfo.genESBProxy()) {
                    n = (this.buildESBProxy() ? 1 : 0);
                }
                if (n != 0 && PGAppObj.m_genInfo.genESB2Proxy()) {
                    n = (this.buildESB2Proxy() ? 1 : 0);
                }
            }
            else {
                n = 0;
            }
            if (n != 0) {
                PGAppObj.m_genInfo.logIt(0, "PGLOG_GenComplete", null, 1);
            }
            else {
                validate = 2;
                PGAppObj.m_genInfo.logIt(0, "PGLOG_GenTerminated", null, 1);
                PGAppObj.m_genInfo.logIt(1, "PGLOG_SearchForError", null, 3);
            }
        }
        finally {
            PGAppObj.m_genInfo.closeLogFile();
            final String property = System.getProperty("ProxyGen.FromGUI");
            if (property != null && (property.equalsIgnoreCase("yes") || property.equalsIgnoreCase(""))) {
                PGAppObj.m_genInfo.createCompletedLogFile(this.m_strAppObj);
            }
        }
        return validate;
    }
    
    public int validate(final boolean b) throws Open4GLException {
        final int[] array = { 1 };
        if (!b) {
            PGAppObj.m_genInfo.openLogFile(this.m_strAppObj);
        }
        PGAppObj.m_genInfo.logIt(0, null, "ProxyGen, " + ProgressVersion.getVersionString(), 1);
        this.m_pSubObjs = new PGAppObj[this.m_vSubObjs.size()];
        this.m_vSubObjs.copyInto(this.m_pSubObjs);
        PGAppObj.m_genInfo.logIt(0, "PGLOG_CheckingFiles", "...", 1);
        if (Validator.removeObsoleteProcs(this, PGAppObj.m_genInfo)) {
            array[0] = 12;
        }
        if (PGAppObj.m_genInfo.isVerbose()) {
            this.invalidateProcs(this);
        }
        this.fillInfo(this, array);
        this.addReturnValuesForProcedures(this);
        PGAppObj.m_genInfo.logIt(0, "PGLOG_CheckingNames", "...", 1);
        if (!Validator.checkAllNames(this, PGAppObj.m_genInfo)) {
            array[0] = 2;
        }
        if (!b) {
            PGAppObj.m_genInfo.logIt(0, "PGLOG_ValComplete", null, 1);
            PGAppObj.m_genInfo.logIt(1, "PGLOG_SearchForError", null, 3);
            PGAppObj.m_genInfo.closeLogFile();
        }
        this.m_bDirty = true;
        return array[0];
    }
    
    public boolean buildJavaProxy() throws Open4GLException {
        boolean b = true;
        final String[] array = { null };
        final String pathFromPackage = getPathFromPackage(PGAppObj.m_genInfo.getWorkDir(), PGAppObj.m_genInfo.getPackage(), true);
        try {
            PGAppObj.m_genInfo.logIt(0, "PGLOG_GeneratingJava", "...", 1);
            this.deleteJavaProxyFiles(this, pathFromPackage, true);
            this.createClassesForJava(this, pathFromPackage);
            PGGenInfo.setCurrentAppObj(this);
            if (b) {
                PGAppObj.m_genInfo.logIt(1, "PGLOG_CompilingProxies", "...", 1);
                if (ProExec.runCompiler(true, null, pathFromPackage + this.m_strAppObj + ".java", array) != 0) {
                    PGAppObj.m_genInfo.logIt(2, "PGLOG_CompileError", null, 3);
                    PGAppObj.m_genInfo.logIt(2, null, array[0], 3);
                    b = false;
                }
            }
        }
        catch (OutOfMemoryError outOfMemoryError) {
            final Open4GLException ex = new Open4GLException(8099442454849133705L, null);
            PGAppObj.m_genInfo.logIt(1, "PGLOG_JGenerateError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, ex.getMessage(), 3);
            b = false;
        }
        catch (Exception ex2) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_JGenerateError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, ex2.getMessage(), 3);
            b = false;
        }
        catch (Error error) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_JGenerateError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, error.getMessage(), 3);
            b = false;
        }
        finally {
            final String property = System.getProperty("ProxyGen.LeaveProxyFiles");
            if (property == null || (!property.equalsIgnoreCase("yes") && property.length() > 0)) {
                PGAppObj.m_genInfo.logIt(1, "PGLOG_Deleting", "...", 1);
                this.deleteJavaProxyFiles(this, pathFromPackage, false);
            }
        }
        return b;
    }
    
    public boolean buildDotNetProxy() throws Open4GLException {
        boolean b = true;
        final String[] array = { null };
        final String dnPathFromNamespace = getDNPathFromNamespace(PGAppObj.m_genInfo.getWorkDir(), PGAppObj.m_genInfo.getDNNamespace(), true);
        try {
            PGAppObj.m_genInfo.logIt(0, "PGLOG_GeneratingDN", "...", 1);
            this.createAssemblyInfoClassForDotNet(this, dnPathFromNamespace);
            this.createSTClassNamesForDotNet(this);
            this.createSchemaFilesForDotNet(this);
            this.createClassesForDotNet(this, dnPathFromNamespace);
            final PGGenInfo genInfo = PGAppObj.m_genInfo;
            final Vector dataSetList = PGGenInfo.getDataSetList();
            final PGGenInfo genInfo2 = PGAppObj.m_genInfo;
            final Vector dataTableList = PGGenInfo.getDataTableList();
            PGGenInfo.setCurrentAppObj(this);
            if (b) {
                if (dataSetList.size() > 0) {
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingDataSets", "...", 1);
                }
                for (int index = 0; index < dataSetList.size() && b; b = this.processDNExecError(ProExec.buildSTDataSet(dataSetList.elementAt(index), false, array), array), ++index) {}
                if (b) {
                    if (dataTableList.size() > 0) {
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingDataTables", "...", 1);
                    }
                    for (int index2 = 0; index2 < dataTableList.size() && b; b = this.processDNExecError(ProExec.buildSTDataSet(dataTableList.elementAt(index2), true, array), array), ++index2) {}
                }
                if (b) {
                    boolean b2 = false;
                    final String property = System.getProperty("ProxyGen.UseWildCard");
                    if (property != null && property.equalsIgnoreCase("yes")) {
                        b2 = true;
                        this.deleteDotNetDataSetFiles(this, dnPathFromNamespace, true);
                    }
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_CompilingDNProxies", "...", 1);
                    b = this.processDNExecError(ProExec.runCSCCompiler(this, dnPathFromNamespace, array, b2), array);
                }
            }
        }
        catch (OutOfMemoryError outOfMemoryError) {
            final Open4GLException ex = new Open4GLException(8099442454849133705L, null);
            PGAppObj.m_genInfo.logIt(1, "PGLOG_DNGenerateError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, ex.getMessage(), 3);
            b = false;
        }
        catch (Exception ex2) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_DNGenerateError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, ex2.getMessage(), 3);
            b = false;
        }
        catch (Error error) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_DNGenerateError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, error.getMessage(), 3);
            b = false;
        }
        finally {
            final String property2 = System.getProperty("ProxyGen.LeaveProxyFiles");
            if (property2 == null || (!property2.equalsIgnoreCase("yes") && property2.length() > 0)) {
                PGAppObj.m_genInfo.logIt(1, "PGLOG_Deleting", "...", 1);
                this.deleteDotNetProxyFiles(this, dnPathFromNamespace);
            }
        }
        return b;
    }
    
    public boolean processDNExecError(final int n, final String[] array) {
        boolean b = false;
        if (n == 1) {
            PGAppObj.m_genInfo.logIt(2, "PGLOG_DNCompileError", null, 3);
            PGAppObj.m_genInfo.logIt(2, null, array[0], 3);
        }
        else if (n == 2) {
            PGAppObj.m_genInfo.logIt(2, null, array[0], 3);
            PGAppObj.m_genInfo.logIt(2, "PGLOG_DNRunError", null, 3);
        }
        else if (n == 3) {
            PGAppObj.m_genInfo.logIt(2, null, array[0], 3);
        }
        else {
            b = true;
        }
        return b;
    }
    
    boolean buildWebServicesProxy() throws Open4GLException {
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        boolean b = true;
        boolean b2 = false;
        final String workDir = PGAppObj.m_genInfo.getWorkDir();
        final String string = this.m_strAppObj + ".wsm";
        final String[] array = { null };
        final String property = System.getProperty("java.version");
        try {
            if (property.length() >= 3) {
                b2 = (property.charAt(0) != '1' || property.charAt(1) != '.' || (property.charAt(2) != '0' && property.charAt(2) != '1' && property.charAt(2) != '2'));
            }
            else if (property.length() == 1 && property.charAt(0) != '1') {
                b2 = true;
            }
            PGAppObj.m_genInfo.logIt(0, "PGLOG_GeneratingWS", "...", 1);
            if (this.hasDatasetTtabNamespaceConflict()) {
                PGAppObj.m_genInfo.logIt(1, "PGLOG_WSGenError", null, 3);
                PGAppObj.m_genInfo.logIt(1, null, "Dataset Parameters cannot have Temp-Table members in different namespaces", 3);
                return false;
            }
            final WSAD wsad = new WSAD(this);
            this.createSTClassNamesForWebServices(this);
            final PGGenInfo genInfo = PGAppObj.m_genInfo;
            final Vector dataSetList = PGGenInfo.getDataSetList();
            if (dataSetList != null && !dataSetList.isEmpty() && this.hasDatasetXmlNodeNameConflict(dataSetList)) {
                PGAppObj.m_genInfo.logIt(1, "PGLOG_WSGenError", null, 3);
                PGAppObj.m_genInfo.logIt(1, null, "Dataset Parameters contain different definitions but same XML-NODE-NAME", 3);
                return false;
            }
            PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingWSM", "...", 1);
            wsad.saveWSMFile(getAbsFilename(workDir, string));
            PGAppObj.m_genInfo.logIt(1, "PGLOG_WSMGenComplete", "...", 1);
            if (dwGenInfo.genWSDL()) {
                PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingWSDL", "...", 1);
                if (b2) {
                    this.buildTestWSDLs();
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_WSDLGenComplete", "...", 1);
                }
                else if (ProExec.runWSDLGen(string, array) != 0) {
                    PGAppObj.m_genInfo.logIt(2, "PGLOG_WSDLGenError", null, 3);
                    PGAppObj.m_genInfo.logIt(2, null, array[0], 3);
                    b = false;
                }
                else {
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_WSDLGenComplete", "...", 1);
                }
            }
        }
        catch (Exception ex) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_WSGenError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, ex.getMessage(), 3);
            b = false;
        }
        catch (Error error) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_WSGenError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, error.getMessage(), 3);
            b = false;
        }
        return b;
    }
    
    boolean buildESB2Proxy() throws Open4GLException {
        boolean b = true;
        final String workDir = PGAppObj.m_genInfo.getWorkDir();
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        PGAppObj.m_genInfo.logIt(0, "PGLOG_GeneratingESB", "...", 1);
        if (dwGenInfo.getUseFileSystem()) {
            for (int i = 0; i < this.m_pProcs.length; ++i) {
                final PGProc pgProc = this.m_pProcs[i];
                final String string = workDir + pgProc.getProcName() + ".esboe";
                PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string, 1);
                if (!ESBInvk.GenerateESBOEFile(string, pgProc)) {
                    b = false;
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_InvGenError", null, 3);
                }
            }
            for (int j = 0; j < this.m_pPerProcs.length; ++j) {
                final PGProc pgProc2 = this.m_pPerProcs[j];
                final String string2 = workDir + pgProc2.getProcName() + ".esboe";
                PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string2, 1);
                if (!ESBInvk.GenerateESBOEFile(string2, pgProc2)) {
                    b = false;
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_InvGenError", null, 3);
                }
                final PGMethod[] internalProcs = pgProc2.getProcDetail().getInternalProcs();
                for (int k = 0; k < internalProcs.length; ++k) {
                    final String string3 = workDir + pgProc2.getProcName() + "_" + internalProcs[k].getInternalProc() + ".esboe";
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string3, 1);
                    if (!ESBInvk.GenerateESBOEFile(string3, pgProc2, k)) {
                        b = false;
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_InvGenError", null, 3);
                    }
                }
            }
        }
        if (dwGenInfo.getCreateXAR()) {
            String str = dwGenInfo.getSonicXARName();
            if (str != null && !str.endsWith(".") && !str.endsWith(".xar")) {
                str += ".xar";
            }
            PGAppObj.m_genInfo.logIt(0, "PGLOG_CreatingESBArchive", workDir + str, 1);
            try {
                final ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new CheckedOutputStream(new FileOutputStream(workDir + str), new Adler32())));
                zipOutputStream.setLevel(8);
                final String string4 = "/SonicFS/Resources/" + this.m_strAppObj + "/";
                for (int l = 0; l < this.m_pProcs.length; ++l) {
                    final PGProc pgProc3 = this.m_pProcs[l];
                    final String string5 = string4 + pgProc3.getProcName() + ".esboe";
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string5, 1);
                    if (!ESBInvk.GenerateESBOEFile(string5, zipOutputStream, pgProc3)) {
                        b = false;
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_InvGenError", null, 3);
                    }
                }
                for (int n = 0; n < this.m_pPerProcs.length; ++n) {
                    final PGProc pgProc4 = this.m_pPerProcs[n];
                    final String string6 = string4 + pgProc4.getProcName() + ".esboe";
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string6, 1);
                    if (!ESBInvk.GenerateESBOEFile(string6, zipOutputStream, pgProc4)) {
                        b = false;
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_InvGenError", null, 3);
                    }
                    final PGMethod[] internalProcs2 = pgProc4.getProcDetail().getInternalProcs();
                    for (int n2 = 0; n2 < internalProcs2.length; ++n2) {
                        final String string7 = string4 + pgProc4.getProcName() + "_" + internalProcs2[n2].getInternalProc() + ".esboe";
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string7, 1);
                        if (!ESBInvk.GenerateESBOEFile(string7, zipOutputStream, pgProc4, n2)) {
                            b = false;
                            PGAppObj.m_genInfo.logIt(1, "PGLOG_InvGenError", null, 3);
                        }
                    }
                }
                zipOutputStream.flush();
                zipOutputStream.close();
            }
            catch (Exception ex) {
                PGAppObj.m_genInfo.logIt(0, "PGLOG_GeneratingESB", "Exception creating Sonic archive: " + ex.toString(), 1);
                b = false;
            }
        }
        if (dwGenInfo.getDeployToDS()) {
            PGAppObj.m_genInfo.logIt(0, "PGLOG_ConnectToESB", "...", 1);
            final ESBDeploy esbDeploy = new ESBDeploy();
            String str2 = dwGenInfo.getSonicResourceDir();
            if (!str2.endsWith("\\") || !str2.endsWith("/")) {
                str2 += "/";
            }
            final boolean sonicOverwriteFiles = dwGenInfo.getSonicOverwriteFiles();
            try {
                if (dwGenInfo.getSonicURL() == null) {
                    esbDeploy.connect();
                }
                else {
                    esbDeploy.connect(dwGenInfo.getSonicURL(), dwGenInfo.getSonicDomain(), dwGenInfo.getSonicUser(), dwGenInfo.getSonicPassword());
                }
            }
            catch (Exception ex2) {
                PGAppObj.m_genInfo.logIt(0, "PGLOG_GeneratingESB", "Exception connecting to Sonic Domain Manager:" + ex2.toString(), 3);
                b = false;
            }
            try {
                for (int n3 = 0; n3 < this.m_pProcs.length; ++n3) {
                    final PGProc pgProc5 = this.m_pProcs[n3];
                    final File tempFile = File.createTempFile("pxg", ".esboe", new File(workDir));
                    final String string8 = pgProc5.getProcName() + ".esboe";
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string8, 1);
                    if (!ESBInvk.GenerateESBOEFile(workDir + tempFile.getName(), pgProc5)) {
                        b = false;
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_InvGenError", null, 3);
                    }
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_DeployESBInvocation", str2 + string8, 1);
                    if (!esbDeploy.deploy(tempFile, string8, str2, sonicOverwriteFiles)) {
                        b = false;
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_InvDeployError", null, 3);
                    }
                    tempFile.delete();
                }
                for (int n4 = 0; n4 < this.m_pPerProcs.length; ++n4) {
                    final PGProc pgProc6 = this.m_pPerProcs[n4];
                    final File tempFile2 = File.createTempFile("pxg", ".esboe", new File(workDir));
                    final String string9 = pgProc6.getProcName() + ".esboe";
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string9, 1);
                    if (!ESBInvk.GenerateESBOEFile(workDir + tempFile2.getName(), pgProc6)) {
                        b = false;
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_InvGenError", null, 3);
                    }
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_DeployESBInvocation", str2 + string9, 1);
                    if (!esbDeploy.deploy(tempFile2, string9, str2, sonicOverwriteFiles)) {
                        b = false;
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_InvDeployError", null, 3);
                    }
                    final String name = tempFile2.getName();
                    final File file = new File(workDir + (name.substring(0, name.indexOf(".esboe")) + "_release.esboe"));
                    final String string10 = pgProc6.getProcName() + "_release.esboe";
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string10, 1);
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_DeployESBInvocation", str2 + string10, 1);
                    if (!esbDeploy.deploy(file, string10, str2, sonicOverwriteFiles)) {
                        b = false;
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_InvDeployError", null, 3);
                    }
                    tempFile2.delete();
                    file.delete();
                    final PGMethod[] internalProcs3 = pgProc6.getProcDetail().getInternalProcs();
                    for (int n5 = 0; n5 < internalProcs3.length; ++n5) {
                        final File tempFile3 = File.createTempFile("pxg", ".esboe", new File(workDir));
                        final String string11 = pgProc6.getProcName() + "_" + internalProcs3[n5].getInternalProc() + ".esboe";
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBInvocation", string11, 1);
                        if (!ESBInvk.GenerateESBOEFile(workDir + tempFile3.getName(), pgProc6, n5)) {
                            b = false;
                            PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBGenError", null, 3);
                        }
                        PGAppObj.m_genInfo.logIt(1, "PGLOG_DeployESBInvocation", str2 + string11, 1);
                        if (!esbDeploy.deploy(tempFile3, string11, str2, sonicOverwriteFiles)) {
                            b = false;
                            PGAppObj.m_genInfo.logIt(1, "PGLOG_InvDeployError", null, 3);
                        }
                        tempFile3.delete();
                    }
                }
            }
            catch (Exception ex3) {
                b = false;
                PGAppObj.m_genInfo.logIt(0, "PGLOG_GeneratingESB", "Exception deploying files to Sonic Domain Manager:" + ex3.toString(), 3);
            }
            esbDeploy.disconnect();
        }
        if (!b) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBGenError", null, 3);
        }
        return b;
    }
    
    boolean buildESBProxy() throws Open4GLException {
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        final String workDir = PGAppObj.m_genInfo.getWorkDir();
        final String string = dwGenInfo.getFilename() + ".wsm";
        final String string2 = dwGenInfo.getFilename() + ".wsdl";
        final ESBDeploy esbDeploy = new ESBDeploy();
        boolean b;
        try {
            PGAppObj.m_genInfo.logIt(0, "PGLOG_GeneratingESB", "...", 1);
            final WSAD wsad = new WSAD(this);
            final File tempFile = File.createTempFile("pxg", ".wsm", new File(workDir));
            tempFile.deleteOnExit();
            PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBWSM", string, 1);
            wsad.saveWSMFile(tempFile.getAbsolutePath(), dwGenInfo.getAppserverURL());
            PGAppObj.m_genInfo.logIt(1, "PGLOG_WSMGenComplete", "...", 1);
            final File tempFile2 = File.createTempFile("pxg", ".wsdl", new File(workDir));
            tempFile2.deleteOnExit();
            PGAppObj.m_genInfo.logIt(1, "PGLOG_CreatingESBWSDL", string2, 1);
            this.buildESBWSDL(tempFile2);
            PGAppObj.m_genInfo.logIt(1, "PGLOG_WSDLGenComplete", "...", 1);
            PGAppObj.m_genInfo.logIt(1, "PGLOG_ConnectToESB", "...", 1);
            if (dwGenInfo.getSonicURL() == null) {
                esbDeploy.connect();
            }
            else {
                esbDeploy.connect(dwGenInfo.getSonicURL(), dwGenInfo.getSonicDomain(), dwGenInfo.getSonicUser(), dwGenInfo.getSonicPassword());
            }
            PGAppObj.m_genInfo.logIt(1, "PGLOG_DeployToESB", "...", 1);
            b = esbDeploy.deploy(tempFile, string, tempFile2, string2, dwGenInfo.getResourceDir(), dwGenInfo.overwriteFiles());
            if (!b) {
                PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBGenError", null, 3);
                PGAppObj.m_genInfo.logIt(1, "PGLOG_DeployError", null, 3);
                return b;
            }
            final String serviceName = PGAppObj.m_genInfo.getServiceName();
            if (0 < serviceName.length()) {
                String s;
                if (dwGenInfo.useDefaultEntryEndpoint()) {
                    s = serviceName + ".Entry";
                }
                else {
                    s = dwGenInfo.getEntryEndpoint();
                }
                String s2;
                if (dwGenInfo.useDefaultFaultEndpoint()) {
                    s2 = serviceName + ".Fault";
                }
                else {
                    s2 = dwGenInfo.getFaultEndpoint();
                }
                String s3;
                if (dwGenInfo.useDefaultRejectedEndpoint()) {
                    s3 = serviceName + ".RME";
                }
                else {
                    s3 = dwGenInfo.getRejectedEndpoint();
                }
                PGAppObj.m_genInfo.logIt(1, "PGLOG_CreateServiceESB", "...", 1);
                if (esbDeploy.checkEndpoint(s)) {
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBEndPtWarning", s, 4);
                }
                if (esbDeploy.checkEndpoint(s2)) {
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBEndPtWarning", s2, 4);
                }
                if (esbDeploy.checkEndpoint(s3)) {
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBEndPtWarning", s3, 4);
                }
                esbDeploy.createService(serviceName, s, s2, s3, "sonicfs://" + dwGenInfo.getResourceDir() + "/" + string, "sonicfs://" + dwGenInfo.getResourceDir() + "/" + string2, dwGenInfo.getAppserverURL());
            }
            final String container = dwGenInfo.getContainer();
            if (0 < container.length()) {
                PGAppObj.m_genInfo.logIt(1, "PGLOG_AddContainerESB", "...", 1);
                b = esbDeploy.addToContainer(serviceName, container);
                if (!b) {
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBGenError", null, 3);
                    PGAppObj.m_genInfo.logIt(1, "PGLOG_ContainerError", null, 3);
                }
            }
        }
        catch (Exception ex) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBGenError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, ex.getMessage(), 3);
            b = false;
        }
        catch (Error error) {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_ESBGenError", null, 3);
            PGAppObj.m_genInfo.logIt(1, null, error.getMessage(), 3);
            b = false;
        }
        esbDeploy.disconnect();
        return b;
    }
    
    private void setWebServicesEncoding() {
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        if (dwGenInfo.genRPCEncoded()) {
            dwGenInfo.setEncoding(1);
        }
        else if (dwGenInfo.genRPCLiteral()) {
            dwGenInfo.setEncoding(2);
        }
        else if (dwGenInfo.genDocLiteral()) {
            dwGenInfo.setEncoding(3);
        }
    }
    
    public void buildTestWSDLs() throws IOException, WSDLException {
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        final String workDir = PGAppObj.m_genInfo.getWorkDir();
        this.initWebServicesGenerate();
        this.setProxygenProductInfo();
        if (dwGenInfo.genWSDL()) {
            this.generateWSDL(workDir);
        }
    }
    
    public void buildESBWSDL(final File file) throws IOException, WSDLException {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.m_genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        final int encoding = dwGenInfo.getEncoding();
        dwGenInfo.setEncoding(dwGenInfo.getESBEncoding());
        final String soapEndPointURL = dwGenInfo.getSoapEndPointURL();
        dwGenInfo.setSoapEndPointURL("sonic:///local/" + PGAppObj.m_genInfo.getServiceName());
        this.initWebServicesGenerate();
        this.setProxygenProductInfo();
        if (dwGenInfo.getESBEncoding() == 1 && this.hasDatasetAttributeFields()) {
            throw new WSDLException("INVALID_WSDL", "Dataset Temp-Table fields MUST be represented as elements in RPC/Encoded WSDL documents.");
        }
        final FileWriter fileWriter = new FileWriter(file);
        wsdlGenInfo.init(dwGenInfo.getESBEncoding());
        WSDLDefinitionObj.out(WSDLDefinitionObj.build(this), fileWriter);
        fileWriter.flush();
        fileWriter.close();
        dwGenInfo.setEncoding(encoding);
        dwGenInfo.setSoapEndPointURL(soapEndPointURL);
    }
    
    public void setProxygenProductInfo() {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.m_genInfo.getWSDLGenInfo();
        final String productName = "Proxygen_Product";
        String string = "";
        try {
            string = "Progress Version " + Class.forName("com.progress.common.util.ProgressVersion").getMethod("getVersion", (Class<?>[])null).invoke(null, (Object[])null).toString();
        }
        catch (ClassNotFoundException ex) {}
        catch (Exception ex2) {}
        wsdlGenInfo.setProductName(productName);
        wsdlGenInfo.setProductVersion(string);
    }
    
    public boolean buildWSDL(final String name, final String productName, final String productVersion) throws IOException, WSDLException {
        final boolean b = true;
        (PGAppObj.m_genInfo = this.m_wsInfo.getPGGenInfo()).setDWGenInfo(this.m_wsInfo.getDWGenInfo());
        this.initWebServicesGenerate();
        final WSDLGenInfo wsdlGenInfo = PGAppObj.m_genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        try {
            if (dwGenInfo.getEncoding() == 1 && this.hasDatasetAttributeFields()) {
                throw new WSDLException("INVALID_WSDL", "Dataset Temp-Table fields MUST be represented as elements in RPC/Encoded WSDL documents.");
            }
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(name), "UTF-8");
            wsdlGenInfo.init(dwGenInfo.getEncoding());
            wsdlGenInfo.setProductName(productName);
            wsdlGenInfo.setProductVersion(productVersion);
            WSDLDefinitionObj.out(WSDLDefinitionObj.build(this), outputStreamWriter);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (IOException ex2) {}
        catch (WSDLException ex) {
            throw ex;
        }
        PGAppObj.m_genInfo = null;
        return b;
    }
    
    public PGProc findMatchingProc(final String s, final Vector vector, final Integer[] array) {
        PGProc pgProc = null;
        if (vector != null) {
            for (int i = 0; i < vector.size(); ++i) {
                final PGProc pgProc2 = vector.elementAt(i);
                if (s.equalsIgnoreCase(pgProc2.getProPath() + pgProc2.getProcPath() + pgProc2.getProcName() + ".r")) {
                    pgProc = vector.elementAt(i);
                    array[0] = new Integer(i);
                    break;
                }
            }
        }
        return pgProc;
    }
    
    public PGProc findMatchingProc(final String s, final boolean b, final Integer[] array) {
        return this.findMatchingProc(s, this.getRelevantProcVector(b), array);
    }
    
    public String[] fillProcNameList(final boolean b) {
        String[] array = new String[0];
        final Vector relevantProcVector = this.getRelevantProcVector(b);
        if (relevantProcVector != null) {
            final int size = relevantProcVector.size();
            array = new String[size];
            for (int i = 0; i < size; ++i) {
                final PGProc pgProc = relevantProcVector.elementAt(i);
                final String string = pgProc.getProPath() + pgProc.getProcPath() + pgProc.getProcName() + ".r";
                final File file = new File(string);
                try {
                    array[i] = file.getCanonicalPath();
                }
                catch (IOException ex) {
                    array[i] = string;
                }
            }
        }
        return array;
    }
    
    public Vector getRelevantProcVector(final boolean b) {
        Vector vector;
        if (b) {
            vector = this.m_vPerProcs;
        }
        else {
            vector = this.m_vProcs;
        }
        return vector;
    }
    
    public int findInsertLocation(final Vector vector, final String s) {
        int i = 0;
        if (vector != null) {
            for (i = 0; i < vector.size(); ++i) {
                final PGProc pgProc = vector.elementAt(i);
                if (s.compareTo(pgProc.getProPath() + pgProc.getProcPath() + pgProc.getProcName() + ".r") < 0) {
                    break;
                }
            }
        }
        return i;
    }
    
    public static String ProNameToProxyName(String s) {
        final char[] array = { '-', '#', '%', '&', '.' };
        final char[] array2 = { '-', '#', '%', '&', '.', '$' };
        final char[] array3 = { '-', '#', '%', '&', '.', '$' };
        final char[] array4 = { '-', '#', '%', '&', '.', '$' };
        char[] array5;
        if (PGAppObj.m_genInfo == null) {
            array5 = array;
        }
        else if (PGAppObj.m_genInfo.genDotNetProxy() && PGAppObj.m_genInfo.genJavaProxy()) {
            array5 = array3;
        }
        else if (PGAppObj.m_genInfo.genDotNetProxy()) {
            array5 = array2;
        }
        else if (PGAppObj.m_genInfo.genJavaProxy()) {
            array5 = array;
        }
        else if (PGAppObj.m_genInfo.genWebServicesProxy()) {
            array5 = array4;
        }
        else {
            array5 = array;
        }
        for (int i = 0; i < array5.length; ++i) {
            int index;
            while ((index = s.indexOf(array5[i])) != -1) {
                if (index == 0) {
                    s = s.substring(1);
                }
                else if (index == s.length() - 1) {
                    s = s.substring(0, index);
                }
                else {
                    s = s.substring(0, index) + s.substring(index + 1, index + 2).toUpperCase() + s.substring(index + 2);
                }
            }
        }
        return s;
    }
    
    public static boolean isKeyword(final String s, final boolean b, final boolean b2) {
        return (b && isJavaKeyword(s)) || (b2 && isDotNetKeyword(s));
    }
    
    public static boolean isJavaKeyword(final String s) {
        for (int i = 0; i < PGAppObj.m_reservedWords.length; ++i) {
            if (s.equals(PGAppObj.m_reservedWords[i])) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isDotNetKeyword(final String s) {
        for (int i = 0; i < PGAppObj.m_dotNetReservedWords.length; ++i) {
            if (s.equals(PGAppObj.m_dotNetReservedWords[i])) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean validateName(final String s, final boolean b, final boolean b2, final boolean b3) {
        return (!b || validateNameForJava(s)) && (!b2 || validateNameForDotNet(s)) && (!b3 || validateNameForWebServices(s));
    }
    
    public static boolean validateNameForJava(final String s) {
        boolean b = true;
        if (isJavaKeyword(s)) {
            b = false;
        }
        if (b) {
            if (!Character.isJavaIdentifierStart(s.charAt(0))) {
                b = false;
            }
            else {
                for (int i = 1; i < s.length(); ++i) {
                    if (!Character.isJavaIdentifierPart(s.charAt(i))) {
                        b = false;
                        break;
                    }
                }
            }
        }
        return b;
    }
    
    public static boolean validateNameForDotNet(final String s) {
        boolean b = true;
        final char[] array = { '-', '#', '%', '&', '.', '$' };
        if (isDotNetKeyword(s)) {
            b = false;
        }
        if (b) {
            final char char1 = s.charAt(0);
            if (!Character.isLetter(char1) && char1 != '_') {
                b = false;
            }
            else {
                for (int i = 0; i < array.length; ++i) {
                    if (s.indexOf(array[i]) != -1) {
                        b = false;
                        break;
                    }
                }
            }
        }
        return b;
    }
    
    public static boolean validateNameForWebServices(final String s) {
        boolean b = true;
        final char[] array = { '$', '#', '%', '&' };
        final char char1 = s.charAt(0);
        if (!Character.isLetter(char1) && char1 != '_') {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (s.indexOf(array[i]) != -1) {
                b = false;
                break;
            }
        }
        return b;
    }
    
    public static String valProPath(final String str, final boolean b) {
        String s = null;
        if (str == null || str.length() == 0) {
            s = PGGenInfo.getMsg(8099442454849133699L, new Object[] { PGGenInfo.getResources().getTranString("PG_Propath") });
        }
        else {
            Object checkDir = null;
            int n = 0;
            final StringTokenizer stringTokenizer = new StringTokenizer(str, System.getProperty("path.separator"), false);
            while (stringTokenizer.hasMoreTokens()) {
                ++n;
                if (!b && n > 1) {
                    s = PGGenInfo.getMsg(8099442454849135503L, null);
                    break;
                }
                String string = new String(stringTokenizer.nextToken());
                if (!string.endsWith(System.getProperty("file.separator"))) {
                    string += System.getProperty("file.separator");
                }
                if (string == null) {
                    continue;
                }
                checkDir = checkDir(fixOSPath(3, string));
                if (checkDir != null) {
                    break;
                }
            }
            if (checkDir != null) {
                s = PGGenInfo.getMsg(8099442454849133700L, new Object[] { checkDir });
            }
        }
        return s;
    }
    
    public static String getAbsFilename(final String s, final String pathname) {
        File file;
        String s2;
        if (s != null) {
            file = new File(s, pathname);
            s2 = s + pathname;
        }
        else {
            file = new File(pathname);
            s2 = pathname;
        }
        try {
            if (file != null) {
                s2 = file.getCanonicalPath();
            }
            return s2;
        }
        catch (IOException ex) {
            return s2;
        }
    }
    
    public static String checkDir(final String s) {
        String s2 = null;
        if (s != null) {
            final File file = new File(getAbsFilename(null, s));
            if (!file.exists() || !file.isDirectory()) {
                s2 = s;
            }
        }
        return s2;
    }
    
    public static String removeReturns(String s) {
        final char c = '\n';
        final char char1 = System.getProperty("line.separator").charAt(0);
        final char c2 = ' ';
        if (s != null) {
            s = substituteChar(s, c, c2);
            s = substituteChar(s, char1, c2);
        }
        return s;
    }
    
    public static String substituteChar(String replace, final char oldChar, final char newChar) {
        if (replace != null) {
            replace = replace.replace(oldChar, newChar);
        }
        return replace;
    }
    
    public static String fixOSPath(final int n, final String str) {
        String s = "";
        String s2;
        if (str == null) {
            s2 = null;
        }
        else if (n == 1 || (n == 3 && PGAppObj.m_isWindows)) {
            s2 = str.replace('/', '\\');
        }
        else {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, ":", false);
            while (stringTokenizer.hasMoreTokens()) {
                s = new String(stringTokenizer.nextToken());
            }
            s2 = s.replace('\\', '/');
        }
        return s2;
    }
    
    public static String fixOSPaths(final int n, final String str) {
        String str2;
        if (n == 1 || (n == 3 && PGAppObj.m_isWindows)) {
            str2 = ";";
        }
        else {
            str2 = ":";
        }
        String s = "";
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (!s.equals("")) {
                s += str2;
            }
            s += fixOSPath(n, nextToken);
        }
        return s;
    }
    
    public static int updatePropath(final PGAppObj pgAppObj, String string, String string2) {
        final StringTokenizer stringTokenizer = new StringTokenizer(pgAppObj.m_strProPath, ";");
        final String s = "\\";
        final Vector<String> vector = new Vector<String>(5, 2);
        final int n = 0;
        String proPath = "";
        while (stringTokenizer.hasMoreTokens()) {
            String str = stringTokenizer.nextToken();
            if (!str.endsWith(s)) {
                str += s;
            }
            if (!string.endsWith(s)) {
                string += s;
            }
            if (!string2.endsWith(s)) {
                string2 += s;
            }
            String s2;
            if (str.equals(string)) {
                s2 = string2;
            }
            else {
                s2 = str;
            }
            boolean b = false;
            for (int n2 = 0; n2 < vector.size() && !b; ++n2) {
                if (PGAppObj.m_isWindows) {
                    b = s2.equalsIgnoreCase(vector.elementAt(n2));
                }
                else {
                    b = s2.equals(vector.elementAt(n2));
                }
            }
            if (!b) {
                if (!proPath.equals("")) {
                    proPath += ";";
                }
                proPath += s2;
                vector.addElement(s2);
            }
        }
        pgAppObj.setProPath(proPath);
        int n3 = n + PGProc.updateProcPropath(pgAppObj.getProcVector(), string, string2) + PGProc.updateProcPropath(pgAppObj.getPerProcVector(), string, string2);
        final Vector subObjectVector = pgAppObj.getSubObjectVector();
        for (int i = 0; i < subObjectVector.size(); ++i) {
            n3 += updatePropath(subObjectVector.elementAt(i), string, string2);
        }
        return n3;
    }
    
    public void invalidateProcs(final PGAppObj pgAppObj) {
        for (int i = 1; i <= 2; ++i) {
            Vector vector;
            if (i == 1) {
                vector = pgAppObj.getProcVector();
            }
            else {
                vector = pgAppObj.getPerProcVector();
            }
            for (int size = vector.size(), j = 0; j < size; ++j) {
                final PGProc pgProc = vector.elementAt(j);
                pgProc.setValidated(false);
                final PGProcDetail procDetail = pgProc.getProcDetail();
                if (procDetail != null && !procDetail.isCustomized()) {
                    pgProc.setProcDetail(null);
                }
            }
        }
        final PGAppObj[] subObjects = pgAppObj.getSubObjects();
        if (subObjects != null) {
            for (int k = 0; k < subObjects.length; ++k) {
                this.invalidateProcs(subObjects[k]);
            }
        }
    }
    
    private void createClassesForJava(final PGAppObj currentAppObj, final String s) throws Exception {
        PrintWriter printWriter = null;
        final String package1 = PGAppObj.m_genInfo.getPackage();
        try {
            PGGenInfo.setCurrentAppObj(currentAppObj);
            PGAppObj.m_genInfo.logIt(1, "PGLOG_Creating", currentAppObj.m_strAppObj + ".java ...", 1);
            printWriter = new PrintWriter(new FileOutputStream(getAbsFilename(s, currentAppObj.m_strAppObj + ".java")));
            BuildPubJ.build(currentAppObj, printWriter);
            printWriter.close();
            PGAppObj.m_genInfo.logIt(1, "PGLOG_Creating", currentAppObj.m_strAppObj + "Impl.java ...", 1);
            printWriter = new PrintWriter(new FileOutputStream(getAbsFilename(s, currentAppObj.m_strAppObj + "Impl.java")));
            BuildImplJ.build(currentAppObj, printWriter);
            printWriter.close();
            for (int i = 0; i < currentAppObj.m_pPerProcs.length; ++i) {
                final PGProc pgProc = currentAppObj.m_pPerProcs[i];
                final String methodName = pgProc.getProcDetail().getMethodName();
                PGAppObj.m_genInfo.logIt(1, "PGLOG_Creating", methodName + ".java ...", 1);
                printWriter = new PrintWriter(new FileOutputStream(getAbsFilename(s, methodName + ".java")));
                BuildPubJ.build(pgProc, package1, printWriter);
                printWriter.close();
                printWriter = null;
                PGAppObj.m_genInfo.logIt(1, "PGLOG_Creating", methodName + "Impl.java ...", 1);
                printWriter = new PrintWriter(new FileOutputStream(getAbsFilename(s, methodName + "Impl.java")));
                BuildPProcJ.build(pgProc, package1, printWriter);
                printWriter.close();
                printWriter = null;
            }
            if (currentAppObj.m_pSubObjs != null) {
                for (int j = 0; j < currentAppObj.m_pSubObjs.length; ++j) {
                    this.createClassesForJava(currentAppObj.m_pSubObjs[j], s);
                }
            }
        }
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
    
    private void deleteJavaProxyFiles(final PGAppObj pgAppObj, final String s, final boolean b) throws Open4GLException {
        if (b) {
            new File(getAbsFilename(s, pgAppObj.m_strAppObj + ".java")).delete();
        }
        final File file = new File(getAbsFilename(s, pgAppObj.m_strAppObj + "Impl.java"));
        if (!file.delete() && !b) {
            PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file.getAbsolutePath(), 3);
        }
        for (int i = 0; i < pgAppObj.m_pPerProcs.length; ++i) {
            final String methodName = pgAppObj.m_pPerProcs[i].getProcDetail().getMethodName();
            if (b) {
                new File(getAbsFilename(s, methodName + ".java")).delete();
            }
            final File file2 = new File(getAbsFilename(s, methodName + "Impl.java"));
            if (!file2.delete() && !b) {
                PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file2.getAbsolutePath(), 3);
            }
        }
        if (pgAppObj.m_pSubObjs != null) {
            for (int j = 0; j < pgAppObj.m_pSubObjs.length; ++j) {
                this.deleteJavaProxyFiles(pgAppObj.m_pSubObjs[j], s, b);
            }
        }
    }
    
    private void createClassesForDotNet(final PGAppObj currentAppObj, final String s) throws Exception {
        PrintWriter printWriter = null;
        try {
            PGGenInfo.setCurrentAppObj(currentAppObj);
            PGAppObj.m_genInfo.logIt(1, "PGLOG_Creating", currentAppObj.m_strAppObj + ".cs ...", 1);
            printWriter = new PrintWriter(new FileOutputStream(getAbsFilename(s, currentAppObj.m_strAppObj + ".cs")));
            BuildPubDN.build(currentAppObj, printWriter);
            printWriter.close();
            for (int i = 0; i < currentAppObj.m_pPerProcs.length; ++i) {
                final PGProc pgProc = currentAppObj.m_pPerProcs[i];
                final String methodName = pgProc.getProcDetail().getMethodName();
                PGAppObj.m_genInfo.logIt(1, "PGLOG_Creating", methodName + ".cs ...", 1);
                printWriter = new PrintWriter(new FileOutputStream(getAbsFilename(s, methodName + ".cs")));
                BuildPubDN.build(pgProc, printWriter);
                printWriter.close();
                printWriter = null;
            }
            if (currentAppObj.m_pSubObjs != null) {
                for (int j = 0; j < currentAppObj.m_pSubObjs.length; ++j) {
                    this.createClassesForDotNet(currentAppObj.m_pSubObjs[j], s);
                }
            }
        }
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
    
    private void createSTClassNamesForDotNet(final PGAppObj currentAppObj) {
        final String effectiveDNDataSetNamespace = PGAppObj.m_genInfo.getEffectiveDNDataSetNamespace();
        PGGenInfo.setCurrentAppObj(currentAppObj);
        StrongTypeNames.build(currentAppObj, effectiveDNDataSetNamespace);
        for (int i = 0; i < currentAppObj.m_pPerProcs.length; ++i) {
            StrongTypeNames.build(currentAppObj.m_pPerProcs[i], PGAppObj.m_genInfo.getEffectiveDNDataSetNamespace());
        }
        if (currentAppObj.m_pSubObjs != null) {
            for (int j = 0; j < currentAppObj.m_pSubObjs.length; ++j) {
                this.createSTClassNamesForDotNet(currentAppObj.m_pSubObjs[j]);
            }
        }
        if (!currentAppObj.isSubAppObject()) {
            final PGGenInfo genInfo = PGAppObj.m_genInfo;
            StrongTypeNames.updateNames(PGGenInfo.getDataTableList());
            final PGGenInfo genInfo2 = PGAppObj.m_genInfo;
            StrongTypeNames.updateNames(PGGenInfo.getDataSetList());
        }
    }
    
    private void createSTClassNamesForWebServices(final PGAppObj currentAppObj) {
        PGGenInfo.setCurrentAppObj(currentAppObj);
        StrongTypeNames.buildForWS(currentAppObj);
        for (int i = 0; i < currentAppObj.m_pPerProcs.length; ++i) {
            StrongTypeNames.buildForWS(currentAppObj.m_pPerProcs[i]);
        }
        if (currentAppObj.m_pSubObjs != null) {
            for (int j = 0; j < currentAppObj.m_pSubObjs.length; ++j) {
                this.createSTClassNamesForWebServices(currentAppObj.m_pSubObjs[j]);
            }
        }
        if (!currentAppObj.isSubAppObject()) {
            final PGGenInfo genInfo = PGAppObj.m_genInfo;
            StrongTypeNames.updateNamesForWS(PGGenInfo.getDataSetList());
        }
    }
    
    private void createSchemaFilesForDotNet(final PGAppObj pgAppObj) throws Exception {
        final PGGenInfo genInfo = PGAppObj.m_genInfo;
        final Vector dataSetList = PGGenInfo.getDataSetList();
        final PGGenInfo genInfo2 = PGAppObj.m_genInfo;
        final Vector dataTableList = PGGenInfo.getDataTableList();
        for (int i = 0; i < dataTableList.size(); ++i) {
            BuildSchemaDN.buildSchemaFile(dataTableList.elementAt(i));
        }
        for (int j = 0; j < dataSetList.size(); ++j) {
            BuildSchemaDN.buildSchemaFile(dataSetList.elementAt(j));
        }
    }
    
    private void createAssemblyInfoClassForDotNet(final PGAppObj pgAppObj, final String s) throws Exception {
        PrintWriter printWriter = null;
        try {
            PGAppObj.m_genInfo.logIt(1, "PGLOG_Creating", "AssemblyInfo.cs ...", 1);
            printWriter = new PrintWriter(new FileOutputStream(getAbsFilename(s, "AssemblyInfo.cs")));
            BuildAssemblyInfoDN.build(pgAppObj, printWriter);
            printWriter.close();
        }
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
    
    private void deleteDotNetProxyFiles(final PGAppObj pgAppObj, final String s) throws Open4GLException {
        PGAppObj.m_genInfo.getWorkDir();
        if (!pgAppObj.isSubAppObject()) {
            this.deleteDotNetDataSetFiles(pgAppObj, s, false);
            final File file = new File(getAbsFilename(s, "AssemblyInfo.cs"));
            if (!file.delete()) {
                PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file.getAbsolutePath(), 3);
            }
        }
        final File file2 = new File(getAbsFilename(s, pgAppObj.m_strAppObj + ".cs"));
        if (!file2.delete()) {
            PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file2.getAbsolutePath(), 3);
        }
        for (int i = 0; i < pgAppObj.m_pPerProcs.length; ++i) {
            final File file3 = new File(getAbsFilename(s, pgAppObj.m_pPerProcs[i].getProcDetail().getMethodName() + ".cs"));
            if (!file3.delete()) {
                PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file3.getAbsolutePath(), 3);
            }
        }
        if (pgAppObj.m_pSubObjs != null) {
            for (int j = 0; j < pgAppObj.m_pSubObjs.length; ++j) {
                this.deleteDotNetProxyFiles(pgAppObj.m_pSubObjs[j], s);
            }
        }
    }
    
    private void deleteDotNetDataSetFiles(final PGAppObj pgAppObj, final String s, final boolean b) {
        final PGGenInfo genInfo = PGAppObj.m_genInfo;
        final Vector dataSetList = PGGenInfo.getDataSetList();
        final PGGenInfo genInfo2 = PGAppObj.m_genInfo;
        final Vector dataTableList = PGGenInfo.getDataTableList();
        IPGStrongNameParam ipgStrongNameParam = null;
        final String workDir = PGAppObj.m_genInfo.getWorkDir();
        for (int i = 0; i < dataSetList.size(); ++i) {
            ipgStrongNameParam = dataSetList.elementAt(i);
            this.deleteDotNetStrongNameFiles(ipgStrongNameParam, b);
        }
        for (int j = 0; j < dataTableList.size(); ++j) {
            ipgStrongNameParam = dataTableList.elementAt(j);
            this.deleteDotNetStrongNameFiles(ipgStrongNameParam, b);
        }
        if (ipgStrongNameParam != null && PGAppObj.m_genInfo.useDNNamespaceAsDirs() && !b) {
            final File file = new File(getAbsFilename(null, getDNPathFromNamespace(workDir, ipgStrongNameParam.getNamespace(), false)));
            if (!file.delete()) {
                PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file.getAbsolutePath(), 3);
            }
        }
    }
    
    private void deleteDotNetStrongNameFiles(final IPGStrongNameParam ipgStrongNameParam, final boolean b) {
        final String workDir = PGAppObj.m_genInfo.getWorkDir();
        final String className = ipgStrongNameParam.getClassName();
        final String dnPathFromNamespace = getDNPathFromNamespace(workDir, ipgStrongNameParam.getNamespace(), false);
        if (!b) {
            final File file = new File(getAbsFilename(dnPathFromNamespace, className + ".cs"));
            if (!file.delete()) {
                PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file.getAbsolutePath(), 3);
            }
            final File file2 = new File(getAbsFilename(dnPathFromNamespace, className + ".exe"));
            if (!file2.delete()) {
                PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file2.getAbsolutePath(), 3);
            }
            final File file3 = new File(getAbsFilename(dnPathFromNamespace, className + ".xsd"));
            if (!file3.delete()) {
                PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file3.getAbsolutePath(), 3);
            }
        }
        final String property = System.getProperty("ProxyGen.UseWildCard");
        int n = 1;
        if (!b && property != null && property.equalsIgnoreCase("yes")) {
            n = 0;
        }
        if (n == 1) {
            final File file4 = new File(getAbsFilename(dnPathFromNamespace, className + "Schema.cs"));
            if (!file4.delete()) {
                PGAppObj.m_genInfo.logIt(2, "PGLOG_DeleteWarning", file4.getAbsolutePath(), 3);
            }
        }
    }
    
    public static String getPathFromPackage(String fixOSPath, final String str, final boolean b) {
        final StringBuffer sb = new StringBuffer(64);
        final String property = System.getProperty("file.separator");
        fixOSPath = fixOSPath(3, fixOSPath);
        if (fixOSPath != null) {
            sb.append(fixOSPath);
        }
        if (str != null && str.length() > 0) {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
            while (stringTokenizer.hasMoreTokens()) {
                sb.append(stringTokenizer.nextToken());
                sb.append(property);
                final File file = new File(getAbsFilename(null, sb.toString()));
                if (b && !file.exists()) {
                    file.mkdir();
                }
            }
        }
        return sb.toString();
    }
    
    public static String getDNPathFromNamespace(final String s, final String s2, final boolean b) {
        String s3;
        if (!PGAppObj.m_genInfo.useDNNamespaceAsDirs()) {
            s3 = fixOSPath(3, s);
        }
        else {
            s3 = getPathFromPackage(s, s2, b);
        }
        return s3;
    }
    
    public void fillInfo(final PGAppObj currentAppObj, final int[] array) {
        int length = 0;
        PGGenInfo.setCurrentAppObj(currentAppObj);
        PGAppObj.m_genInfo.logIt(0, "PGLOG_ProcessingProcs", "...", 1);
        currentAppObj.m_pProcs = this.fillProcInfo(currentAppObj, currentAppObj.m_vProcs, array);
        PGAppObj.m_genInfo.logIt(0, "PGLOG_ProcessingPersProcs", "...", 1);
        currentAppObj.m_pPerProcs = this.fillProcInfo(currentAppObj, currentAppObj.m_vPerProcs, array);
        if (currentAppObj.m_pSubObjs != null) {
            length = currentAppObj.m_pSubObjs.length;
        }
        for (int i = 0; i < length; ++i) {
            currentAppObj.fillInfo(currentAppObj.m_pSubObjs[i], array);
        }
    }
    
    private PGProc[] fillProcInfo(final PGAppObj pgAppObj, final Vector vector, final int[] array) {
        final int size = vector.size();
        final PGProc[] anArray = new PGProc[size];
        vector.copyInto(anArray);
        for (int i = 0; i < size; ++i) {
            try {
                final int n = 0;
                array[n] |= anArray[i].fillDetails(pgAppObj, PGAppObj.m_genInfo, false, null);
            }
            catch (Open4GLException ex) {
                array[0] = 2;
            }
        }
        vector.removeAllElements();
        for (int length = anArray.length, j = 0; j < length; ++j) {
            vector.insertElementAt(anArray[j], j);
        }
        return anArray;
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            if (!this.isSubAppObject()) {
                if (PGAppObj.m_version == null) {
                    (PGAppObj.m_version = new PGVersion()).setCurrentPxgVersion();
                }
                if (PGAppObj.m_version != null) {
                    String string;
                    if (b) {
                        string = str + ":PGVersion";
                    }
                    else {
                        string = "PGVersion";
                    }
                    xmlSerializer.startElement(s, "PGVersion", string, (Attributes)null);
                    PGAppObj.m_version.writeXML(xmlSerializer, s, str);
                    xmlSerializer.endElement(s, "PGVersion", string);
                }
                if (this.m_wsInfo == null) {
                    if (PGAppObj.m_genInfo != null) {
                        final AttributesImpl attributesImpl = new AttributesImpl();
                        attributesImpl.addAttribute("", "isSessionFree", "isSessionFree", "CDATA", PGAppObj.m_genInfo.getConnectionFree() ? "true" : "false");
                        String string2;
                        if (b) {
                            string2 = str + ":PGGenInfo";
                        }
                        else {
                            string2 = "PGGenInfo";
                        }
                        xmlSerializer.startElement(s, "PGGenInfo", string2, (Attributes)attributesImpl);
                        PGAppObj.m_genInfo.writeXML(xmlSerializer, s, str);
                        xmlSerializer.endElement(s, "PGGenInfo", string2);
                        if (PGAppObj.m_genInfo.getDWGenInfo() != null) {
                            String string3;
                            if (b) {
                                string3 = str + ":DeploymentWizard";
                            }
                            else {
                                string3 = "DeploymentWizard";
                            }
                            xmlSerializer.startElement(s, "DeploymentWizard", string3, (Attributes)null);
                            PGAppObj.m_genInfo.getDWGenInfo().writeXML(xmlSerializer, s, str);
                            xmlSerializer.endElement(s, "DeploymentWizard", string3);
                        }
                    }
                }
                else {
                    if (this.m_wsInfo.getPGGenInfo() != null) {
                        final AttributesImpl attributesImpl2 = new AttributesImpl();
                        attributesImpl2.addAttribute("", "isSessionFree", "isSessionFree", "CDATA", this.m_wsInfo.getPGGenInfo().getConnectionFree() ? "true" : "false");
                        String string4;
                        if (b) {
                            string4 = str + ":PGGenInfo";
                        }
                        else {
                            string4 = "PGGenInfo";
                        }
                        xmlSerializer.startElement(s, "PGGenInfo", string4, (Attributes)attributesImpl2);
                        this.m_wsInfo.getPGGenInfo().writeXML(xmlSerializer, s, str);
                        xmlSerializer.endElement(s, "PGGenInfo", string4);
                    }
                    if (this.m_wsInfo.getDWGenInfo() != null) {
                        String string5;
                        if (b) {
                            string5 = str + ":DeploymentWizard";
                        }
                        else {
                            string5 = "DeploymentWizard";
                        }
                        xmlSerializer.startElement(s, "DeploymentWizard", string5, (Attributes)null);
                        this.m_wsInfo.getDWGenInfo().writeXML(xmlSerializer, s, str);
                        xmlSerializer.endElement(s, "DeploymentWizard", string5);
                    }
                }
            }
            if (this.m_strAppObj != null) {
                String string6;
                if (b) {
                    string6 = str + ":Name";
                }
                else {
                    string6 = "Name";
                }
                xmlSerializer.startElement(s, "Name", string6, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strAppObj.toCharArray(), 0, this.m_strAppObj.length());
                xmlSerializer.endElement(s, "Name", string6);
            }
            if (this.m_strProPath != null) {
                String string7;
                if (b) {
                    string7 = str + ":ProPath";
                }
                else {
                    string7 = "ProPath";
                }
                xmlSerializer.startElement(s, "ProPath", string7, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strProPath.toCharArray(), 0, this.m_strProPath.length());
                xmlSerializer.endElement(s, "ProPath", string7);
            }
            if (this.m_strHelp != null) {
                String string8;
                if (b) {
                    string8 = str + ":HelpString";
                }
                else {
                    string8 = "HelpString";
                }
                xmlSerializer.startElement(s, "HelpString", string8, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strHelp.toCharArray(), 0, this.m_strHelp.length());
                xmlSerializer.endElement(s, "HelpString", string8);
            }
            final String s2 = "ObjectType";
            final String value = String.valueOf(this.m_bSubObject);
            final String qName = this.getQName(b, str, s2);
            xmlSerializer.startElement(s, s2, qName, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value.toCharArray(), 0, value.length());
            xmlSerializer.endElement(s, s2, qName);
            final String s3 = "AllowUnknown";
            final String value2 = String.valueOf(this.m_bAllowUnknown);
            final String qName2 = this.getQName(b, str, s3);
            xmlSerializer.startElement(s, s3, qName2, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value2.toCharArray(), 0, value2.length());
            xmlSerializer.endElement(s, s3, qName2);
            final String s4 = "IsTTResultSet";
            final String value3 = String.valueOf(this.m_bIsTTResultSet);
            final String qName3 = this.getQName(b, str, s4);
            xmlSerializer.startElement(s, s4, qName3, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value3.toCharArray(), 0, value3.length());
            xmlSerializer.endElement(s, s4, qName3);
            final String s5 = "WriteDatasetBeforeImage";
            final String value4 = String.valueOf(this.m_bWriteXmlBeforeImage);
            final String qName4 = this.getQName(b, str, s5);
            xmlSerializer.startElement(s, s5, qName4, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value4.toCharArray(), 0, value4.length());
            xmlSerializer.endElement(s, s5, qName4);
            for (int i = 0; i < this.m_vSubObjs.size(); ++i) {
                if (this.m_vSubObjs.elementAt(i) != null) {
                    String string9;
                    if (b) {
                        string9 = str + ":SubAppObject";
                    }
                    else {
                        string9 = "SubAppObject";
                    }
                    xmlSerializer.startElement(s, "SubAppObject", string9, (Attributes)null);
                    ((PGAppObj)this.m_vSubObjs.elementAt(i)).writeXML(xmlSerializer, s, str);
                    xmlSerializer.endElement(s, "SubAppObject", string9);
                }
            }
            for (int j = 0; j < this.m_vProcs.size(); ++j) {
                if (this.m_vProcs.elementAt(j) != null) {
                    final AttributesImpl attributesImpl3 = new AttributesImpl();
                    attributesImpl3.addAttribute("", "isPersistent", "isPersistent", "CDATA", "false");
                    attributesImpl3.addAttribute("", "useFullName", "useFullName", "CDATA", this.m_vProcs.elementAt(j).isUseFullName() ? "true" : "false");
                    String string10;
                    if (b) {
                        string10 = str + ":Procedure";
                    }
                    else {
                        string10 = "Procedure";
                    }
                    xmlSerializer.startElement(s, "Procedure", string10, (Attributes)attributesImpl3);
                    ((PGProc)this.m_vProcs.elementAt(j)).writeXML(xmlSerializer, s, str);
                    xmlSerializer.endElement(s, "Procedure", string10);
                }
            }
            for (int k = 0; k < this.m_vPerProcs.size(); ++k) {
                if (this.m_vPerProcs.elementAt(k) != null) {
                    final AttributesImpl attributesImpl4 = new AttributesImpl();
                    attributesImpl4.addAttribute("", "isPersistent", "isPersistent", "CDATA", "true");
                    attributesImpl4.addAttribute("", "useFullName", "useFullName", "CDATA", this.m_vPerProcs.elementAt(k).isUseFullName() ? "true" : "false");
                    String string11;
                    if (b) {
                        string11 = str + ":Procedure";
                    }
                    else {
                        string11 = "Procedure";
                    }
                    xmlSerializer.startElement(s, "Procedure", string11, (Attributes)attributesImpl4);
                    ((PGProc)this.m_vPerProcs.elementAt(k)).writeXML(xmlSerializer, s, str);
                    xmlSerializer.endElement(s, "Procedure", string11);
                }
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    private String getQName(final boolean b, final String str, final String str2) {
        String string;
        if (b) {
            string = str + ":" + str2;
        }
        else {
            string = str2;
        }
        return string;
    }
    
    public void readXML(final Node node) {
        this.m_wsInfo = new WSInfo();
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue = WsaParser.extractNodeValue(item);
                if (localName.equals("PGGenInfo")) {
                    final PGGenInfo pgGenInfo = new PGGenInfo();
                    pgGenInfo.readXML(item);
                    this.m_wsInfo.setPGGenInfo(pgGenInfo);
                    PGAppObj.m_genInfo = pgGenInfo;
                }
                else if (localName.equals("PGVersion")) {
                    final PGVersion version = new PGVersion();
                    version.readXML(item);
                    PGAppObj.m_version = version;
                }
                else if (localName.equals("DeploymentWizard")) {
                    final DWGenInfo dwGenInfo = new DWGenInfo();
                    dwGenInfo.readXML(item);
                    this.m_wsInfo.setDWGenInfo(dwGenInfo);
                    PGAppObj.m_genInfo.setDWGenInfo(dwGenInfo);
                }
                else if (localName.equals("Name")) {
                    this.m_strAppObj = nodeValue;
                }
                else if (localName.equals("ProPath")) {
                    this.m_strProPath = nodeValue;
                }
                else if (localName.equals("HelpString")) {
                    this.setHelpString(nodeValue);
                }
                else if (localName.equals("ObjectType")) {
                    this.m_bSubObject = Boolean.valueOf(nodeValue);
                }
                else if (localName.equals("AllowUnknown")) {
                    this.m_bAllowUnknown = Boolean.valueOf(nodeValue);
                }
                else if (localName.equals("IsTTResultSet")) {
                    this.m_bIsTTResultSet = Boolean.valueOf(nodeValue);
                }
                else if (localName.equals("WriteDatasetBeforeImage")) {
                    this.m_bWriteXmlBeforeImage = Boolean.valueOf(nodeValue);
                }
                else if (localName.equals("SubAppObject")) {
                    final PGAppObj obj = new PGAppObj();
                    obj.setSubAppObject(true);
                    obj.readXML(item);
                    PGAppObj.m_genInfo = PGAppObj.m_genInfo;
                    this.m_vSubObjs.addElement(obj);
                }
                else if (localName.equals("Procedure")) {
                    final PGProc pgProc = new PGProc();
                    pgProc.readXML(item);
                    PGProc.setGenInfo(PGAppObj.m_genInfo);
                    if (pgProc.isPersistent()) {
                        this.m_vPerProcs.addElement(pgProc);
                    }
                    else {
                        this.m_vProcs.addElement(pgProc);
                    }
                }
            }
        }
        if (!this.m_vSubObjs.isEmpty()) {
            this.m_pSubObjs = new PGAppObj[this.m_vSubObjs.size()];
            this.m_vSubObjs.copyInto(this.m_pSubObjs);
        }
        if (!this.m_vProcs.isEmpty()) {
            this.m_pProcs = new PGProc[this.m_vProcs.size()];
            this.m_vProcs.copyInto(this.m_pProcs);
        }
        if (!this.m_vPerProcs.isEmpty()) {
            this.m_pPerProcs = new PGProc[this.m_vPerProcs.size()];
            this.m_vPerProcs.copyInto(this.m_pPerProcs);
        }
    }
    
    public static void saveObject(final PGAppObj obj, final String s) throws IOException {
        final FileOutputStream out = new FileOutputStream(getAbsFilename(null, s));
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        if (PGAppObj.m_version == null) {
            PGAppObj.m_version = new PGVersion();
        }
        PGAppObj.m_version.setCurrentPxgVersion();
        objectOutputStream.writeObject(PGAppObj.m_version);
        objectOutputStream.writeObject(PGAppObj.m_genInfo);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        out.close();
        obj.clearDirty();
    }
    
    public static PGAppObj loadObject(final String s, final PGGenInfo[] array) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(getAbsFilename(null, s));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        PGAppObj.m_version = null;
        try {
            PGAppObj.m_version = (PGVersion)objectInputStream.readObject();
        }
        catch (ClassCastException ex) {
            fileInputStream.close();
            fileInputStream = new FileInputStream(getAbsFilename(null, s));
            objectInputStream = new ObjectInputStream(fileInputStream);
        }
        array[0] = (PGGenInfo)objectInputStream.readObject();
        PGAppObj.m_genInfo = array[0];
        final PGAppObj pgAppObj = (PGAppObj)objectInputStream.readObject();
        pgAppObj.m_bIsTTResultSet = true;
        fileInputStream.close();
        return pgAppObj;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.m_pSubObjs = new PGAppObj[0];
        this.m_pProcs = new PGProc[0];
        this.m_pPerProcs = new PGProc[0];
        this.clearDirty();
    }
    
    public boolean generateWSDL(final String s) throws IOException, WSDLException {
        final WSDLGenInfo wsdlGenInfo = PGAppObj.m_genInfo.getWSDLGenInfo();
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        if (dwGenInfo.getEncoding() == 1 && this.hasDatasetAttributeFields()) {
            throw new WSDLException("INVALID_WSDL", "Dataset Temp-Table fields MUST be represented as elements in RPC/Encoded WSDL documents.");
        }
        final FileWriter fileWriter = new FileWriter(getAbsFilename(s, this.m_strAppObj + ".wsdl"));
        wsdlGenInfo.init(dwGenInfo.getEncoding());
        WSDLDefinitionObj.out(WSDLDefinitionObj.build(this), fileWriter);
        return true;
    }
    
    public boolean hasParamType(final int n, final boolean b) {
        for (int i = 0; i < this.m_pProcs.length; ++i) {
            final boolean hasParamType = this.m_pProcs[i].hasParamType(n, false);
            if (hasParamType) {
                return hasParamType;
            }
        }
        for (int j = 0; j < this.m_pPerProcs.length; ++j) {
            final boolean hasParamType2 = this.m_pPerProcs[j].hasParamType(n, false);
            if (hasParamType2) {
                return hasParamType2;
            }
        }
        if (b) {
            if (this.m_pSubObjs != null) {
                for (int k = 0; k < this.m_pSubObjs.length; ++k) {
                    final boolean hasParamType3 = this.m_pSubObjs[k].hasParamType(n, b);
                    if (hasParamType3) {
                        return hasParamType3;
                    }
                }
            }
            for (int l = 0; l < this.m_pPerProcs.length; ++l) {
                final boolean hasParamType4 = this.m_pPerProcs[l].hasParamType(n, b);
                if (hasParamType4) {
                    return hasParamType4;
                }
            }
        }
        return false;
    }
    
    public boolean hasDatasetHandleParam() {
        for (int i = 0; i < this.m_pProcs.length; ++i) {
            final boolean hasDatasetHandleParam = this.m_pProcs[i].hasDatasetHandleParam(false);
            if (hasDatasetHandleParam) {
                return hasDatasetHandleParam;
            }
        }
        for (int j = 0; j < this.m_pPerProcs.length; ++j) {
            final boolean hasDatasetHandleParam2 = this.m_pPerProcs[j].hasDatasetHandleParam(false);
            if (hasDatasetHandleParam2) {
                return hasDatasetHandleParam2;
            }
        }
        return false;
    }
    
    public boolean hasDatasetHandleChangesParam() {
        for (int i = 0; i < this.m_pProcs.length; ++i) {
            final boolean hasDatasetHandleChangesParam = this.m_pProcs[i].hasDatasetHandleChangesParam(false);
            if (hasDatasetHandleChangesParam) {
                return hasDatasetHandleChangesParam;
            }
        }
        for (int j = 0; j < this.m_pPerProcs.length; ++j) {
            final boolean hasDatasetHandleChangesParam2 = this.m_pPerProcs[j].hasDatasetHandleChangesParam(false);
            if (hasDatasetHandleChangesParam2) {
                return hasDatasetHandleChangesParam2;
            }
        }
        return false;
    }
    
    public boolean hasDatasetAttributeFields() {
        for (int i = 0; i < this.m_pProcs.length; ++i) {
            final boolean hasDatasetAttributeFields = this.m_pProcs[i].hasDatasetAttributeFields(false);
            if (hasDatasetAttributeFields) {
                return hasDatasetAttributeFields;
            }
        }
        for (int j = 0; j < this.m_pPerProcs.length; ++j) {
            final boolean hasDatasetAttributeFields2 = this.m_pPerProcs[j].hasDatasetAttributeFields(true);
            if (hasDatasetAttributeFields2) {
                return hasDatasetAttributeFields2;
            }
        }
        if (this.m_pSubObjs != null) {
            for (int k = 0; k < this.m_pSubObjs.length; ++k) {
                final boolean hasDatasetAttributeFields3 = this.m_pSubObjs[k].hasDatasetAttributeFields();
                if (hasDatasetAttributeFields3) {
                    return hasDatasetAttributeFields3;
                }
            }
        }
        return false;
    }
    
    public boolean hasDatasetTtabNamespaceConflict() {
        for (int i = 0; i < this.m_pProcs.length; ++i) {
            final boolean hasDatasetTtabNamespaceConflict = this.m_pProcs[i].hasDatasetTtabNamespaceConflict(false);
            if (hasDatasetTtabNamespaceConflict) {
                return hasDatasetTtabNamespaceConflict;
            }
        }
        for (int j = 0; j < this.m_pPerProcs.length; ++j) {
            final boolean hasDatasetTtabNamespaceConflict2 = this.m_pPerProcs[j].hasDatasetTtabNamespaceConflict(true);
            if (hasDatasetTtabNamespaceConflict2) {
                return hasDatasetTtabNamespaceConflict2;
            }
        }
        if (this.m_pSubObjs != null) {
            for (int k = 0; k < this.m_pSubObjs.length; ++k) {
                final boolean hasDatasetTtabNamespaceConflict3 = this.m_pSubObjs[k].hasDatasetTtabNamespaceConflict();
                if (hasDatasetTtabNamespaceConflict3) {
                    return hasDatasetTtabNamespaceConflict3;
                }
            }
        }
        return false;
    }
    
    public boolean hasDatasetXmlNodeNameConflict(final Vector vector) {
        for (int i = 0; i < vector.size(); ++i) {
            final PGDataSetParam pgDataSetParam = vector.elementAt(i);
            final String xmlNodeName = pgDataSetParam.getXmlNodeName();
            if (xmlNodeName != null) {
                final String className = pgDataSetParam.getClassName();
                for (int j = i + 1; j < vector.size(); ++j) {
                    final PGDataSetParam pgDataSetParam2 = vector.elementAt(j);
                    final String xmlNodeName2 = pgDataSetParam2.getXmlNodeName();
                    if (xmlNodeName2 != null) {
                        final String className2 = pgDataSetParam2.getClassName();
                        if (xmlNodeName.equals(xmlNodeName2) && !className.equals(className2)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean hasArray(final boolean b) {
        for (int i = 0; i < this.m_pProcs.length; ++i) {
            final boolean hasArray = this.m_pProcs[i].hasArray(false);
            if (hasArray) {
                return hasArray;
            }
        }
        for (int j = 0; j < this.m_pPerProcs.length; ++j) {
            final boolean hasArray2 = this.m_pPerProcs[j].hasArray(false);
            if (hasArray2) {
                return hasArray2;
            }
        }
        if (b) {
            if (this.m_pSubObjs != null) {
                for (int k = 0; k < this.m_pSubObjs.length; ++k) {
                    final boolean hasArray3 = this.m_pSubObjs[k].hasArray(b);
                    if (hasArray3) {
                        return hasArray3;
                    }
                }
            }
            for (int l = 0; l < this.m_pPerProcs.length; ++l) {
                final boolean hasArray4 = this.m_pPerProcs[l].hasArray(b);
                if (hasArray4) {
                    return hasArray4;
                }
            }
        }
        return false;
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
    
    public void initWebServicesGenerate() {
        PGAppObj.m_genInfo.getWSDLGenInfo().setCoreDocImpl(new CoreDocumentImpl());
        if (this.m_pSubObjs != null) {
            for (int i = 0; i < this.m_pSubObjs.length; ++i) {
                final PGAppObj pgAppObj = this.m_pSubObjs[i];
                setGenInfo(PGAppObj.m_genInfo);
                for (int j = 0; j < pgAppObj.m_pPerProcs.length; ++j) {
                    final PGProc pgProc = pgAppObj.m_pPerProcs[j];
                    PGProc.setGenInfo(PGAppObj.m_genInfo);
                }
            }
        }
        for (int k = 0; k < this.m_pPerProcs.length; ++k) {
            final PGProc pgProc2 = this.m_pPerProcs[k];
            PGProc.setGenInfo(PGAppObj.m_genInfo);
        }
    }
    
    public PscDeploymentDescriptor generateWSTestDeploymentDescriptor(final WsaSOAPEngineContext wsaSOAPEngineContext, final AppContainer appContainer, final DWGenInfo dwGenInfo) {
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
        sb.append("__WSTest");
        final String string = sb.toString();
        pscDeploymentDescriptor.setID(string);
        pscDeploymentDescriptor.setProgressObjectName("__WSTest");
        if (encoding == 3) {
            pscDeploymentDescriptor.setServiceType(1);
        }
        else {
            pscDeploymentDescriptor.setServiceType(0);
        }
        final MethodParam[] array = { new MethodParam(new QName("", "target"), String.class, 1, 0, 1, false, false, false, 0), new MethodParam(new QName("", "loopback"), String.class, 1, 1, 1, true, false, false, 0) };
        final QName qName = new QName(string, "__test");
        hashtable.put(qName.toString(), new MethodDescriptor(qName, null, "runInternalTest", 8, new MethodParam(new QName("", "result"), String.class, 1, 0, 0, false, false, false, 0), array));
        try {
            pscDeploymentDescriptor.initRuntime(wsaSOAPEngineContext, hashtable, appContainer);
        }
        catch (Exception ex) {}
        return pscDeploymentDescriptor;
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
        sb.append(this.m_strAppObj);
        final String string = sb.toString();
        pscDeploymentDescriptor.setID(string);
        pscDeploymentDescriptor.setProgressObjectName(this.m_strAppObj);
        if (encoding == 3) {
            pscDeploymentDescriptor.setServiceType(1);
        }
        else {
            pscDeploymentDescriptor.setServiceType(0);
        }
        if (!this.m_bSubObject) {
            final MethodParam[] array = { new MethodParam(new QName("", "userId"), String.class, 1, 1, 1, false, false, false, 0), new MethodParam(new QName("", "password"), String.class, 1, 2, 1, false, false, false, 0), new MethodParam(new QName("", "appServerInfo"), String.class, 1, 3, 1, false, false, false, 0) };
            final QName qName = new QName(string, "Connect_" + this.m_strAppObj);
            final MethodDescriptor value = new MethodDescriptor(qName, this.m_strAppObj, null, 0, new MethodParam(new QName("", "result"), Void.TYPE, 0, 0, 0, false, false, false, 0), array);
            hashtable.put(qName.toString(), value);
            if (dwGenInfo.hasConnectReturnString()) {
                value.setConnectReturnString(true);
            }
        }
        final MethodParam[] array2 = new MethodParam[0];
        final String string2 = "Release_" + this.m_strAppObj;
        final QName qName2 = new QName(string, string2);
        hashtable.put(qName2.toString(), new MethodDescriptor(qName2, string2, null, 1, new MethodParam(new QName("", "result"), Void.TYPE, 0, 0, 0, false, false, false, 0), array2));
        for (int length = this.m_pSubObjs.length, i = 0; i < length; ++i) {
            final PGAppObj pgAppObj = this.m_pSubObjs[i];
            final MethodParam[] array3 = new MethodParam[0];
            final String string3 = "CreateAO_" + pgAppObj.getAppObjectName();
            final QName qName3 = new QName(string, string3);
            hashtable.put(qName3.toString(), new MethodDescriptor(qName3, string3, null, 6, new MethodParam(new QName("", "result"), Void.TYPE, 0, 0, 0, false, false, false, 0), array3));
        }
        for (int length2 = this.m_pPerProcs.length, j = 0; j < length2; ++j) {
            final PGProc pgProc = this.m_pPerProcs[j];
            final PGProcDetail procDetail = pgProc.getProcDetail();
            final PGParam[] parameters = procDetail.getParameters();
            final int length3 = parameters.length;
            final MethodParam[] array4 = new MethodParam[length3];
            for (int k = 0; k < length3; ++k) {
                final PGMetaData[] metaData = parameters[k].getMetaData();
                final int length4;
                MethodParam methodParam;
                if (null == metaData || 0 == (length4 = metaData.length)) {
                    methodParam = new MethodParam(new QName("", parameters[k].getParamName()), Parameter.proToJavaClassObject(parameters[k].getParamType()), parameters[k].getParamType(), parameters[k].getParamOrdinal(), parameters[k].getParamMode(), parameters[k].allowUnknown(), parameters[k].getWriteBeforeImage(), parameters[k].isExtentField(), parameters[k].getExtent());
                }
                else {
                    final ResultSetMetaData resultSetMetaData = new ResultSetMetaData(0, length4);
                    for (int l = 0; l < length4; ++l) {
                        resultSetMetaData.setFieldDesc(l + 1, metaData[l].getFieldName(), metaData[l].getExtent(), metaData[l].getType());
                    }
                    methodParam = new MethodParam(new QName("", parameters[k].getParamName()), Parameter.proToJavaClassObject(parameters[k].getParamType()), parameters[k].getParamType(), parameters[k].getParamOrdinal(), parameters[k].getParamMode(), parameters[k].allowUnknown(), parameters[k].getWriteBeforeImage(), resultSetMetaData);
                }
                array4[k] = methodParam;
            }
            final QName qName4 = new QName(string, "CreatePO_" + procDetail.getMethodName());
            final String procPath = pgProc.getProcPath();
            final String procName = pgProc.getProcName();
            String string4;
            if (procPath != null) {
                string4 = procPath + procName;
            }
            else {
                string4 = procName;
            }
            hashtable.put(qName4.toString(), new MethodDescriptor(qName4, string4, null, 2, new MethodParam(new QName("", "result"), (Class)(procDetail.usesReturnValue() ? String.class : Void.TYPE), procDetail.usesReturnValue() ? 1 : 0, 0, 0, false, false, false, 0), array4));
        }
        for (int length5 = this.m_pProcs.length, n = 0; n < length5; ++n) {
            final MethodDescriptor generateMethodDescriptor = this.m_pProcs[n].generateMethodDescriptor(string, encoding);
            hashtable.put(generateMethodDescriptor.name().toString(), generateMethodDescriptor);
        }
        try {
            pscDeploymentDescriptor.initRuntime(wsaSOAPEngineContext, hashtable, appContainer);
        }
        catch (Exception ex) {}
        return pscDeploymentDescriptor;
    }
    
    public Hashtable generateDeploymentDescriptors(final WsaSOAPEngineContext wsaSOAPEngineContext, final AppContainer appContainer) {
        final Hashtable<String, PscDeploymentDescriptor> hashtable = new Hashtable<String, PscDeploymentDescriptor>();
        DWGenInfo dwGenInfo = null;
        if (this.m_wsInfo != null) {
            dwGenInfo = this.m_wsInfo.getDWGenInfo();
        }
        final PscDeploymentDescriptor generateDeploymentDescriptor = this.generateDeploymentDescriptor(wsaSOAPEngineContext, appContainer, dwGenInfo);
        hashtable.put(generateDeploymentDescriptor.getID(), generateDeploymentDescriptor);
        final PscDeploymentDescriptor generateWSTestDeploymentDescriptor = this.generateWSTestDeploymentDescriptor(wsaSOAPEngineContext, appContainer, dwGenInfo);
        hashtable.put(generateWSTestDeploymentDescriptor.getID(), generateWSTestDeploymentDescriptor);
        for (int i = 0; i < this.m_pSubObjs.length; ++i) {
            this.m_pSubObjs[i].generateSODeploymentDescriptors(wsaSOAPEngineContext, appContainer, dwGenInfo, hashtable);
        }
        for (int j = 0; j < this.m_pPerProcs.length; ++j) {
            final PscDeploymentDescriptor generateDeploymentDescriptor2 = this.m_pPerProcs[j].generateDeploymentDescriptor(wsaSOAPEngineContext, appContainer, dwGenInfo);
            hashtable.put(generateDeploymentDescriptor2.getID(), generateDeploymentDescriptor2);
        }
        return hashtable;
    }
    
    public void generateSODeploymentDescriptors(final WsaSOAPEngineContext wsaSOAPEngineContext, final AppContainer appContainer, final DWGenInfo dwGenInfo, final Hashtable hashtable) {
        final PscDeploymentDescriptor generateDeploymentDescriptor = this.generateDeploymentDescriptor(wsaSOAPEngineContext, appContainer, dwGenInfo);
        hashtable.put(generateDeploymentDescriptor.getID(), generateDeploymentDescriptor);
        for (int i = 0; i < this.m_pSubObjs.length; ++i) {
            this.m_pSubObjs[i].generateSODeploymentDescriptors(wsaSOAPEngineContext, appContainer, dwGenInfo, hashtable);
        }
        for (int j = 0; j < this.m_pPerProcs.length; ++j) {
            final PscDeploymentDescriptor generateDeploymentDescriptor2 = this.m_pPerProcs[j].generateDeploymentDescriptor(wsaSOAPEngineContext, appContainer, dwGenInfo);
            hashtable.put(generateDeploymentDescriptor2.getID(), generateDeploymentDescriptor2);
        }
    }
    
    public void addReturnValuesForProcedures(final PGAppObj pgAppObj) {
        for (int i = 0; i < pgAppObj.getProcedures().length; ++i) {
            final PGProcDetail procDetail = pgAppObj.getProcedures(i).getProcDetail();
            if (procDetail != null) {
                this.checkForReturnValue(procDetail, 1);
            }
        }
        if (pgAppObj.getSubObjects() != null) {
            for (int j = 0; j < pgAppObj.getSubObjects().length; ++j) {
                this.addReturnValuesForProcedures(pgAppObj.getSubObjects(j));
            }
        }
        for (int k = 0; k < pgAppObj.getPersistentProcedures().length; ++k) {
            final PGProc persistentProcedures = pgAppObj.getPersistentProcedures(k);
            final PGProcDetail procDetail2 = persistentProcedures.getProcDetail();
            if (procDetail2 != null) {
                this.checkForReturnValue(procDetail2, 1);
            }
            final PGMethod[] internalProcs = persistentProcedures.getProcDetail().getInternalProcs();
            for (int l = 0; l < internalProcs.length; ++l) {
                final PGMethod pgMethod = internalProcs[l];
                final PGMethodDetail methodDetail = pgMethod.getMethodDetail();
                if (methodDetail != null) {
                    this.checkForReturnValue(methodDetail, pgMethod.getProcType());
                }
            }
        }
    }
    
    private void checkForReturnValue(final IPGDetail ipgDetail, final int n) {
        final DWGenInfo dwGenInfo = PGAppObj.m_genInfo.getDWGenInfo();
        if (n == 2) {
            ipgDetail.getReturnValue();
        }
        else if (dwGenInfo.getEncoding() == 3) {
            createReturnValueObj(ipgDetail);
        }
        else if (ipgDetail.usesReturnValue()) {
            createReturnValueObj(ipgDetail);
        }
        else {
            ipgDetail.setReturnValue(null);
        }
    }
    
    public static void createReturnValueObj(final IPGDetail ipgDetail) {
        final PGParam returnValue = new PGParam();
        returnValue.setParamName("result");
        returnValue.setParamType(1);
        ipgDetail.setReturnValue(returnValue);
    }
    
    public void updateDataTypesFromXPXGFile(final PGAppObj pgAppObj) {
        for (int i = 0; i < pgAppObj.getProcedures().length; ++i) {
            final PGProcDetail procDetail = pgAppObj.getProcedures(i).getProcDetail();
            if (procDetail != null) {
                procDetail.updateDataTypesFromXPXGFile();
            }
        }
        if (pgAppObj.getSubObjects() != null) {
            for (int j = 0; j < pgAppObj.getSubObjects().length; ++j) {
                this.updateDataTypesFromXPXGFile(pgAppObj.getSubObjects(j));
            }
        }
        for (int k = 0; k < pgAppObj.getPersistentProcedures().length; ++k) {
            final PGProc persistentProcedures = pgAppObj.getPersistentProcedures(k);
            final PGProcDetail procDetail2 = persistentProcedures.getProcDetail();
            if (procDetail2 != null) {
                procDetail2.updateDataTypesFromXPXGFile();
            }
            final PGProcDetail procDetail3 = persistentProcedures.getProcDetail();
            if (procDetail3 != null) {
                final PGMethod[] internalProcs = procDetail3.getInternalProcs();
                if (internalProcs != null) {
                    for (int l = 0; l < internalProcs.length; ++l) {
                        final PGMethodDetail methodDetail = internalProcs[l].getMethodDetail();
                        if (methodDetail != null) {
                            methodDetail.updateDataTypesFromXPXGFile();
                        }
                    }
                }
            }
        }
    }
    
    public boolean containsProc(final PGProc pgProc) {
        for (int i = 0; i < this.getProcedures().length; ++i) {
            if (pgProc.isSame(this.getProcedures(i))) {
                return true;
            }
        }
        return false;
    }
    
    static {
        PGAppObj.m_genInfo = null;
        PGAppObj.m_version = null;
        m_isWindows = (System.getProperty("os.name").indexOf("Windows") >= 0);
        PGAppObj.m_reservedWords = new String[] { "boolean", "char", "byte", "short", "int", "long", "float", "double", "abstract", "final", "native", "private", "protected", "public", "static", "synchronized", "transient", "volatile", "break", "byvalue", "case", "cast", "catch", "class", "const", "continue", "default", "do", "else", "extends", "false", "finally", "for", "future", "generic", "goto", "if", "implements", "import", "inner", "instanceof", "interface", "native", "new", "null", "operator", "outer", "package", "rest", "return", "super", "switch", "this", "throw", "throws", "true", "try", "var", "void", "volatile", "while", "Object", "Boolean", "Byte", "Character", "Class", "ClassLoader", "Compiler", "Double", "Float", "Integer", "Long", "Math", "Number", "Object", "Process", "Runtime", "SecurityManager", "Short", "String", "StringBuffer", "System", "Thread", "ThreadGroup", "Throwable", "Void", "clone", "equals", "finalize", "getClass", "hashcode", "notify", "notifyAll", "toString", "wait", "coclass", "dispinterface", "enum", "importlib", "library", "module", "struct", "typedef", "union", "inline" };
        PGAppObj.m_dotNetReservedWords = new String[] { "abstract", "as", "base", "bool", "break", "byte", "case", "catch", "char", "checked", "class", "const", "continue", "decimal", "default", "delegate", "do", "double", "else", "enum", "event", "explicit", "extern", "false", "finally", "fixed", "float", "for", "foreach", "get", "goto", "if", "implicit", "in", "int", "interface", "internal", "is", "lock", "long", "namespace", "new", "null", "object", "operator", "out", "override", "params", "private", "protected", "public", "readonly", "ref", "return", "sbyte", "sealed", "set", "short", "sizeof", "stackalloc", "static", "string", "struct", "switch", "this", "throw", "true", "try", "typeof", "uint", "ulong", "unchecked", "unsafe", "ushort", "using", "value", "vitrual", "void", "volatile", "while" };
    }
}
