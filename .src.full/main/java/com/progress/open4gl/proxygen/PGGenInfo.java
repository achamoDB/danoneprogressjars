// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import org.w3c.dom.NodeList;
import com.progress.wsa.admin.WsaParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.wsdlgen.DWGenInfo;
import com.progress.open4gl.wsdlgen.WSDLGenInfo;
import java.util.Vector;
import java.io.PrintWriter;
import com.progress.international.resources.ProgressResources;
import com.progress.wsa.open4gl.XMLSerializable;
import com.progress.message.pgMsg;
import java.io.Serializable;

public class PGGenInfo implements Serializable, pgMsg, XMLSerializable
{
    private static ProgressResources m_resources;
    public static final int LOG_STATUS = 1;
    public static final int LOG_DETAIL = 2;
    public static final int LOG_ERROR = 3;
    public static final int LOG_WARNING = 4;
    private static final String[] m_indent;
    public static final String fileSep;
    public static final String winFileSep = "\\";
    public static final String winPathSep = ";";
    public static final boolean isWindows;
    public static final int JAVACDEFAULTCOMPILER = 1;
    public static final int JVCDEFAULTCOMPILER = 2;
    public static final int CUSTOMCOMPILER = 3;
    public static final int DOTNETDEFAULTCOMPILER = 4;
    public static final int DOTNETDEFAULTXSD = 1;
    public static final int DOTNETCUSTOMXSD = 2;
    public static final String INSTALLDIR;
    public static final String JAVACCP;
    public static final String JAVACCPSWITCH = "-classpath";
    public static final String JAVACOPTS = "";
    public static final String JVC = "jvc";
    public static final String JVCCPSWITCH = "/cp:p";
    public static final String JVCOPTS = "/x- /nomessage";
    public static final String CSC = "csc";
    public static final String XSD = "xsd";
    public static final String DNPATH_FINDER = "PathFinder";
    public static final String PROGRESSJAVAC = "jdk\\bin\\javac";
    public static final String PROGRESSJAVA = "jdk\\bin\\java";
    public static final String SYM_TEMPDIR = "<Temp Dir>";
    public static final String SYM_PROXYDIR = "<Proxy Dir>";
    public static final String SYM_INSTALLDIR = "<Install Dir>";
    public static final String SYM_PROGRESSCP = "<Install Dir>\\java\\progress.jar;<Install Dir>\\java\\messages.jar;<Install Dir>\\java\\prowin.jar";
    public static final String SYM_EMFCP_JAR1 = "ecore.sdo.jar";
    public static final String SYM_EMFCP = "<Install Dir>\\java\\ext\\ecore.sdo.jar;<Install Dir>\\java\\ext\\ecore.change.jar;<Install Dir>\\java\\ext\\commonj.sdo.jar;<Install Dir>\\java\\ext\\ecore.jar;<Install Dir>\\java\\ext\\ecore.resources.jar;<Install Dir>\\java\\ext\\common.jar;<Install Dir>\\java\\ext\\ecore.xmi.jar";
    public static final String OLD_PROGRESSCP = "<Install Dir>\\java\\progress.zip";
    public static final String SYM_JAVACCP = "<Default classes.zip>";
    public static final String SYM_JAVAPOST11CP = "<Install Dir>\\java\\ext\\wsdl4j.jar;<Install Dir>\\java\\progress.jar;<Install Dir>\\java\\messages.jar;<Install Dir>\\java\\ext\\xmlParserAPIs.jar;<Install Dir>\\java\\ext\\soap.jar;<Install Dir>\\java\\ext\\xercesImpl.jar";
    public static final String SYM_DOTNETREFERENCE_PATH = "<Install Dir>\\dotnet\\deploy\\signed\\";
    public static final String SYM_DOTNETREFERENCE_SNS_PATH = "<Install Dir>\\dotnet\\deploy\\strongnamed-signed\\";
    public static final String SYM_DOTNETREFERENCE_SN_PATH = "<Install Dir>\\dotnet\\deploy\\strongnamed\\";
    public static final String SYM_DOTNETREFERENCE = "<Install Dir>\\dotnet\\deploy\\signed\\Progress.o4glrt.dll";
    public static final String SYM_DOTNETREFERENCE_SNS = "<Install Dir>\\dotnet\\deploy\\strongnamed-signed\\Progress.o4glrt.dll";
    public static final String SYM_DOTNETREFERENCE_SN = "<Install Dir>\\dotnet\\deploy\\strongnamed\\Progress.o4glrt.dll";
    public static final String SYM_DNPATH_FINDER = "<Install Dir>\\bin\\dotnet\\PathFinder";
    private static final long serialVersionUID = -348647233088608160L;
    private static final String m_errCantAccessMsgDB = "Can't access message database. ";
    private String m_strAuthor;
    private String m_strVersion;
    private String m_strWorkDir;
    private String m_strJCompiler;
    private String m_strAXCompiler;
    private String m_strJSwitches;
    private String m_strAXSwitches;
    private boolean m_bVerbose;
    private int m_nPGVersion;
    private boolean m_bSaveBeforeGen;
    private String m_strPackage;
    private String m_strService;
    private String m_strProgId;
    private boolean m_bJava;
    private boolean m_bActiveX;
    private boolean m_bWebServices;
    private boolean m_bDotNet;
    private boolean m_bESB;
    private boolean m_bESB2;
    private boolean m_bUserDefinedAppService;
    private boolean m_bUserDefinedProgId;
    private int m_nJCompilerType;
    private String m_strJCPSwitch;
    private String m_strJClasspath;
    private int m_nAXCompilerType;
    private String m_strAXCPSwitch;
    private String m_strAXClasspath;
    private int m_nDNCompilerType;
    private String m_strDNCompiler;
    private String m_strDNSwitches;
    private String m_strDNXSDGenerator;
    private String m_strDNNamespace;
    private String m_strDNDSNamespace;
    private boolean m_bDNUseNamespaceAsDirs;
    private boolean m_bDNUseDefDSNamespace;
    private String m_strDNTitle;
    private String m_strDNVersion;
    private String m_strDNDesc;
    private String m_strDNCompany;
    private String m_strDNProduct;
    private String m_strDNPublicKey;
    private boolean m_bDNDelaySign;
    private String m_DNRuntime;
    private boolean m_bDNUseNullableTypes;
    private transient String m_strDNPathFinder;
    private transient PrintWriter m_logFile;
    private transient PrintWriter m_completedLogFile;
    private transient boolean m_bDirty;
    private transient boolean m_bRegisterActiveX;
    private transient String m_strPrefFilename;
    private transient boolean m_bPrefFileLoaded;
    private transient boolean m_batch;
    private static PGAppObj m_currAppObj;
    private static PGProcDetail m_currProcDetail;
    private static PGMethodDetail m_currMethodDetail;
    private static Vector m_vDataSetList;
    private static Vector m_vDataTableList;
    private transient WSDLGenInfo m_wsdlGenInfo;
    private DWGenInfo m_dwGenInfo;
    private boolean m_bConnectionFree;
    
    public PGGenInfo() {
        this.m_bPrefFileLoaded = false;
        this.m_batch = false;
        this.m_strAuthor = "";
        this.m_strVersion = "";
        this.m_strWorkDir = "";
        this.m_strJCompiler = this.getJCompiler(1, false);
        this.m_strAXCompiler = "jvc";
        this.m_strJSwitches = "";
        this.m_strAXSwitches = "/x- /nomessage";
        this.m_logFile = null;
        this.m_completedLogFile = null;
        this.m_bVerbose = true;
        this.m_nPGVersion = 1;
        this.m_bSaveBeforeGen = true;
        this.m_strPackage = null;
        this.m_strService = null;
        this.m_strProgId = null;
        this.m_bJava = true;
        this.m_bActiveX = false;
        this.m_bWebServices = false;
        this.m_bDotNet = false;
        this.m_bESB = false;
        this.m_bESB2 = false;
        this.m_bDirty = false;
        this.m_strPrefFilename = null;
        this.m_bUserDefinedAppService = false;
        this.m_bUserDefinedProgId = false;
        this.m_bRegisterActiveX = false;
        this.m_nJCompilerType = 1;
        this.m_strJClasspath = this.getJClasspath(1, false);
        this.m_strJCPSwitch = "-classpath";
        this.m_nAXCompilerType = 2;
        this.m_strAXClasspath = this.getAXClasspath(2, false, null);
        this.m_strAXCPSwitch = "/cp:p";
        this.m_wsdlGenInfo = new WSDLGenInfo();
        this.m_dwGenInfo = new DWGenInfo();
        this.m_bConnectionFree = false;
        this.m_nDNCompilerType = 4;
        this.m_strDNCompiler = "csc";
        this.m_strDNPathFinder = "PathFinder";
        this.m_strDNSwitches = "";
        this.m_strDNXSDGenerator = "xsd";
        this.m_strDNNamespace = "";
        this.m_strDNDSNamespace = "";
        this.m_bDNUseNamespaceAsDirs = true;
        this.m_bDNUseDefDSNamespace = true;
        this.m_strDNTitle = "";
        this.m_strDNVersion = "";
        this.m_strDNDesc = "";
        this.m_strDNCompany = "";
        this.m_strDNProduct = "";
        this.m_strDNPublicKey = "";
        this.m_bDNDelaySign = false;
        this.m_DNRuntime = "";
        this.m_bDNUseNullableTypes = false;
        this.m_wsdlGenInfo.setDWGenInfo(this.m_dwGenInfo);
        PGGenInfo.m_vDataSetList = new Vector();
        PGGenInfo.m_vDataTableList = new Vector();
    }
    
    public String getAuthor() {
        return this.m_strAuthor;
    }
    
    public void setAuthor(final String s) {
        if (this.m_strAuthor == null || !this.m_strAuthor.equals(s)) {
            this.m_bDirty = true;
            this.m_strAuthor = s;
        }
    }
    
    public String getVersion() {
        return this.m_strVersion;
    }
    
    public void setVersion(final String s) {
        if (this.m_strVersion == null || !this.m_strVersion.equals(s)) {
            this.m_bDirty = true;
            this.m_strVersion = s;
        }
    }
    
    public String getWorkDir() {
        if (PGGenInfo.isWindows) {
            return this.m_strWorkDir;
        }
        return PGAppObj.fixOSPath(2, this.m_strWorkDir);
    }
    
    public void setWorkDir(final String anObject) {
        if (this.m_strWorkDir == null || !this.m_strWorkDir.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strWorkDir = PGAppObj.fixOSPath(1, anObject);
        }
    }
    
    public String getJCompiler(final int n, final boolean b) {
        String s;
        if (b) {
            s = Generator.insertVariable(PGAppObj.fixOSPath(3, this.m_strJCompiler), "<Install Dir>", PGGenInfo.INSTALLDIR);
        }
        else if (n == 1) {
            s = "<Install Dir>\\jdk\\bin\\javac";
        }
        else if (n == 2) {
            s = "jvc";
        }
        else {
            s = PGAppObj.fixOSPath(1, this.m_strJCompiler);
        }
        return s;
    }
    
    public void setJCompiler(final String anObject) {
        if (this.m_strJCompiler == null || !this.m_strJCompiler.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strJCompiler = PGAppObj.fixOSPath(1, anObject);
        }
    }
    
    public String getAXCompiler(final int n) {
        if (n == 2) {
            return "jvc";
        }
        return this.m_strAXCompiler;
    }
    
    public void setAXCompiler(final String anObject) {
        if (this.m_strAXCompiler == null || !this.m_strAXCompiler.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strAXCompiler = PGAppObj.fixOSPath(1, anObject);
        }
    }
    
    public String getAXSwitches() {
        return this.m_strAXSwitches;
    }
    
    public void setAXSwitches(final String s) {
        if (this.m_strAXSwitches == null || !this.m_strAXSwitches.equals(s)) {
            this.m_bDirty = true;
            this.m_strAXSwitches = s;
        }
    }
    
    public String getDNCompiler(final int n, final boolean b) {
        String str;
        if (b) {
            str = PGAppObj.fixOSPath(3, this.m_strDNCompiler);
            if (n == 4) {
                final String dnPath = ProExec.getDNPath("/csc");
                if (dnPath != null) {
                    str = dnPath + str;
                }
            }
        }
        else if (n == 4) {
            str = "csc";
        }
        else {
            str = PGAppObj.fixOSPath(1, this.m_strDNCompiler);
        }
        return str;
    }
    
    public void setDNCompiler(final String anObject) {
        if (this.m_strDNCompiler == null || !this.m_strDNCompiler.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strDNCompiler = PGAppObj.fixOSPath(1, anObject);
        }
    }
    
    public String getDNSwitches() {
        return this.m_strDNSwitches;
    }
    
    public void setDNSwitches(final String s) {
        if (this.m_strDNSwitches == null || !this.m_strDNSwitches.equals(s)) {
            this.m_bDirty = true;
            this.m_strDNSwitches = s;
        }
    }
    
    public String getDNXSDGenerator(final int n, final boolean b) {
        String str;
        if (b) {
            str = PGAppObj.fixOSPath(3, this.m_strDNXSDGenerator);
            if (n == 4) {
                final String dnPath = ProExec.getDNPath("/xsd");
                if (dnPath != null) {
                    str = dnPath + str;
                }
            }
        }
        else if (n == 4) {
            str = "xsd";
        }
        else {
            str = PGAppObj.fixOSPath(1, this.m_strDNXSDGenerator);
        }
        return str;
    }
    
    public void setDNXSDGenerator(final String anObject) {
        if (this.m_strDNXSDGenerator == null || !this.m_strDNXSDGenerator.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strDNXSDGenerator = PGAppObj.fixOSPath(1, anObject);
        }
    }
    
    public String getDNRuntime() {
        return this.m_DNRuntime;
    }
    
    public boolean getDNUseNullableTypes() {
        return this.m_bDNUseNullableTypes;
    }
    
    public void setDNStrongNamedRuntime(final String dnRuntime) {
        if (this.m_DNRuntime != dnRuntime) {
            this.m_bDirty = true;
            this.m_DNRuntime = dnRuntime;
        }
    }
    
    public String getDNNamespace() {
        return this.m_strDNNamespace;
    }
    
    public void setDNNamespace(final String anObject) {
        if (this.m_strDNNamespace == null || !this.m_strDNNamespace.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strDNNamespace = PGAppObj.fixOSPath(1, anObject);
        }
    }
    
    public String getDNDataSetNamespace() {
        return this.m_strDNDSNamespace;
    }
    
    public String getEffectiveDNDataSetNamespace() {
        String s;
        if (this.useDNDefaultDSNamespace()) {
            if (this.getDNNamespace() == null || this.getDNNamespace().length() == 0) {
                s = "StrongTypesNS";
            }
            else {
                s = this.getDNNamespace() + "." + "StrongTypesNS";
            }
        }
        else {
            s = this.getDNDataSetNamespace();
        }
        return s;
    }
    
    public void setDNDataSetNamespace(final String anObject) {
        if (this.m_strDNDSNamespace == null || !this.m_strDNDSNamespace.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strDNDSNamespace = PGAppObj.fixOSPath(1, anObject);
        }
    }
    
    public boolean useDNNamespaceAsDirs() {
        return this.m_bDNUseNamespaceAsDirs;
    }
    
    public void setUseDNNamespaceAsDirs(final boolean bdnUseNamespaceAsDirs) {
        if (this.m_bDNUseNamespaceAsDirs != bdnUseNamespaceAsDirs) {
            this.m_bDirty = true;
            this.m_bDNUseNamespaceAsDirs = bdnUseNamespaceAsDirs;
        }
    }
    
    public boolean useDNDefaultDSNamespace() {
        return this.m_bDNUseDefDSNamespace;
    }
    
    public void setUseDNDefaultDSNamespace(final boolean bdnUseDefDSNamespace) {
        if (this.m_bDNUseDefDSNamespace != bdnUseDefDSNamespace) {
            this.m_bDirty = true;
            this.m_bDNUseDefDSNamespace = bdnUseDefDSNamespace;
        }
    }
    
    public String getDNPathFinder() {
        final String property = System.getProperty("ProxyGen.UseLocalDotNET");
        String insertVariable;
        if (property != null && (property.equalsIgnoreCase("yes") || property.length() == 0)) {
            insertVariable = ".\\PathFinder";
        }
        else {
            insertVariable = Generator.insertVariable("<Install Dir>\\bin\\dotnet\\PathFinder", "<Install Dir>", PGGenInfo.INSTALLDIR);
        }
        return insertVariable;
    }
    
    public String getDNTitle() {
        return this.m_strDNTitle;
    }
    
    public void setDNTitle(final String s) {
        if (this.m_strDNTitle == null || !this.m_strDNTitle.equals(s)) {
            this.m_bDirty = true;
            this.m_strDNTitle = s;
        }
    }
    
    public String getDNVersion() {
        return this.m_strDNVersion;
    }
    
    public void setDNVersion(final String s) {
        if (this.m_strDNVersion == null || !this.m_strDNVersion.equals(s)) {
            this.m_bDirty = true;
            this.m_strDNVersion = s;
        }
    }
    
    public String getDNDesc() {
        return this.m_strDNDesc;
    }
    
    public void setDNDesc(final String s) {
        if (this.m_strDNDesc == null || !this.m_strDNDesc.equals(s)) {
            this.m_bDirty = true;
            this.m_strDNDesc = s;
        }
    }
    
    public String getDNCompany() {
        return this.m_strDNCompany;
    }
    
    public void setDNCompanyn(final String s) {
        if (this.m_strDNCompany == null || !this.m_strDNCompany.equals(s)) {
            this.m_bDirty = true;
            this.m_strDNCompany = s;
        }
    }
    
    public String getDNProduct() {
        return this.m_strDNProduct;
    }
    
    public void setDNProduct(final String s) {
        if (this.m_strDNProduct == null || !this.m_strDNProduct.equals(s)) {
            this.m_bDirty = true;
            this.m_strDNProduct = s;
        }
    }
    
    public String getDNReference(final boolean b) {
        String insertVariable;
        if (this.m_DNRuntime.compareToIgnoreCase("Strongnamed") == 0) {
            insertVariable = "<Install Dir>\\dotnet\\deploy\\strongnamed\\Progress.o4glrt.dll";
        }
        else if (this.m_DNRuntime.compareToIgnoreCase("Strongnamed Signed") == 0) {
            insertVariable = "<Install Dir>\\dotnet\\deploy\\strongnamed-signed\\Progress.o4glrt.dll";
        }
        else {
            insertVariable = "<Install Dir>\\dotnet\\deploy\\signed\\Progress.o4glrt.dll";
        }
        if (b) {
            insertVariable = Generator.insertVariable(insertVariable, "<Install Dir>", PGGenInfo.INSTALLDIR);
        }
        return insertVariable;
    }
    
    public String getDNReferencePath(final boolean b) {
        String insertVariable;
        if (this.m_DNRuntime.compareToIgnoreCase("Strongnamed") == 0) {
            insertVariable = "<Install Dir>\\dotnet\\deploy\\strongnamed\\";
        }
        else if (this.m_DNRuntime.compareToIgnoreCase("Strongnamed Signed") == 0) {
            insertVariable = "<Install Dir>\\dotnet\\deploy\\strongnamed-signed\\";
        }
        else {
            insertVariable = "<Install Dir>\\dotnet\\deploy\\signed\\";
        }
        if (b) {
            insertVariable = Generator.insertVariable(insertVariable, "<Install Dir>", PGGenInfo.INSTALLDIR);
        }
        return insertVariable;
    }
    
    public String getDNPublicKey() {
        return this.m_strDNPublicKey;
    }
    
    public void setDNPublicKey(final String s) {
        if (this.m_strDNPublicKey == null || !this.m_strDNPublicKey.equals(s)) {
            this.m_bDirty = true;
            this.m_strDNPublicKey = s;
        }
    }
    
    public boolean isDNDelaySign() {
        return this.m_bDNDelaySign;
    }
    
    public void setDNDelaySign(final boolean bdnDelaySign) {
        if (this.m_bDNDelaySign != bdnDelaySign) {
            this.m_bDirty = true;
            this.m_bDNDelaySign = bdnDelaySign;
        }
    }
    
    public boolean isUserDefinedProgId() {
        return this.m_bUserDefinedProgId;
    }
    
    public void setUserDefinedProgId(final boolean bUserDefinedProgId) {
        if (this.m_bUserDefinedProgId != bUserDefinedProgId) {
            this.m_bDirty = true;
            this.m_bUserDefinedProgId = bUserDefinedProgId;
        }
    }
    
    public String getProgId() {
        return this.m_strProgId;
    }
    
    public void setProgId(final String s) {
        if (this.m_strProgId == null || (this.m_strProgId != null && !this.m_strProgId.equals(s))) {
            this.m_bDirty = true;
            this.m_strProgId = s;
        }
    }
    
    public boolean genActiveXProxy() {
        return this.m_bActiveX;
    }
    
    public void setGenActiveXProxy(final boolean bActiveX) {
        if (this.m_bActiveX != bActiveX) {
            this.m_bDirty = true;
            this.m_bActiveX = bActiveX;
        }
    }
    
    public boolean isRegisterActiveX() {
        return this.m_bRegisterActiveX;
    }
    
    public void setRegisterActiveX(final boolean bRegisterActiveX) {
        if (this.m_bRegisterActiveX != bRegisterActiveX) {
            this.m_bRegisterActiveX = bRegisterActiveX;
        }
    }
    
    public int getAXCompilerType() {
        return this.m_nAXCompilerType;
    }
    
    public void setAXCompilerType(final int naxCompilerType) {
        if (this.m_nAXCompilerType != naxCompilerType) {
            this.m_bDirty = true;
            this.m_nAXCompilerType = naxCompilerType;
        }
    }
    
    public String getAXClasspath(final int n, final boolean b, final String s) {
        String s2;
        if (b) {
            s2 = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(this.m_strAXClasspath, "<Install Dir>", PGGenInfo.INSTALLDIR), "<Proxy Dir>", PGAppObj.getAbsFilename(null, this.getWorkDir())), "<Temp Dir>", s);
        }
        else if (n == 2) {
            s2 = "<Proxy Dir>\\<Temp Dir>;<Install Dir>\\java\\progress.jar;<Install Dir>\\java\\messages.jar;<Install Dir>\\java\\prowin.jar";
        }
        else {
            s2 = this.m_strAXClasspath;
        }
        return s2;
    }
    
    public void setAXClasspath(final String anObject) {
        if (this.m_strAXClasspath != null) {
            final int index = this.m_strAXClasspath.indexOf("<Install Dir>\\java\\progress.zip");
            if (index > -1) {
                this.m_strAXClasspath = this.m_strAXClasspath.substring(0, index).concat("<Install Dir>\\java\\progress.jar;<Install Dir>\\java\\messages.jar;<Install Dir>\\java\\prowin.jar").concat(this.m_strAXClasspath.substring(index + "<Install Dir>\\java\\progress.zip".length() - 1 + 1));
            }
        }
        if (this.m_strAXClasspath == null || !this.m_strAXClasspath.equals(anObject)) {
            this.m_bDirty = true;
            this.m_strAXClasspath = PGAppObj.fixOSPaths(1, anObject);
        }
    }
    
    public String getAXCPSwitch(final int n) {
        if (n == 2) {
            return "/cp:p";
        }
        return this.m_strAXCPSwitch;
    }
    
    public void setAXCPSwitch(final String s) {
        if (this.m_strAXCPSwitch == null || !this.m_strAXCPSwitch.equals(s)) {
            this.m_bDirty = true;
            this.m_strAXCPSwitch = s;
        }
    }
    
    public String getJSwitches() {
        return this.m_strJSwitches;
    }
    
    public void setJSwitches(final String s) {
        if (this.m_strJSwitches == null || !this.m_strJSwitches.equals(s)) {
            this.m_bDirty = true;
            this.m_strJSwitches = s;
        }
    }
    
    public boolean isVerbose() {
        return this.m_bVerbose;
    }
    
    public void setVerbose(final boolean bVerbose) {
        if (this.m_bVerbose != bVerbose) {
            this.m_bDirty = true;
            this.m_bVerbose = bVerbose;
        }
    }
    
    public boolean isUserDefinedAppService() {
        return this.m_bUserDefinedAppService;
    }
    
    public void setUserDefinedAppService(final boolean bUserDefinedAppService) {
        if (this.m_bUserDefinedAppService != bUserDefinedAppService) {
            this.m_bDirty = true;
            this.m_bUserDefinedAppService = bUserDefinedAppService;
        }
    }
    
    public boolean saveBeforeGen() {
        return this.m_bSaveBeforeGen;
    }
    
    public void setSaveBeforeGen(final boolean bSaveBeforeGen) {
        if (this.m_bSaveBeforeGen != bSaveBeforeGen) {
            this.m_bDirty = true;
            this.m_bSaveBeforeGen = bSaveBeforeGen;
        }
    }
    
    public String getPackage() {
        return this.m_strPackage;
    }
    
    public void setPackage(final String s) {
        if (this.m_strPackage == null || !this.m_strPackage.equals(s)) {
            this.m_bDirty = true;
            this.m_strPackage = s;
        }
    }
    
    public String getServiceName() {
        return this.m_strService;
    }
    
    public void setServiceName(final String s) {
        if (this.m_strService == null || (this.m_strService != null && !this.m_strService.equals(s))) {
            this.m_bDirty = true;
            this.m_strService = s;
        }
    }
    
    public boolean genJavaProxy() {
        return this.m_bJava;
    }
    
    public void setGenJavaProxy(final boolean bJava) {
        if (this.m_bJava != bJava) {
            this.m_bDirty = true;
            this.m_bJava = bJava;
        }
    }
    
    public boolean genWebServicesProxy() {
        return this.m_bWebServices;
    }
    
    public void setGenWebServicesProxy(final boolean bWebServices) {
        if (this.m_bWebServices != bWebServices) {
            this.m_bDirty = true;
            this.m_bWebServices = bWebServices;
        }
    }
    
    public boolean genESBProxy() {
        return this.m_bESB;
    }
    
    public void setESBProxy(final boolean besb) {
        if (this.m_bESB != besb) {
            this.m_bDirty = true;
            this.m_bESB = besb;
        }
    }
    
    public boolean genESB2Proxy() {
        return this.m_bESB2;
    }
    
    public void setESB2Proxy(final boolean besb2) {
        if (this.m_bESB2 != besb2) {
            this.m_bDirty = true;
            this.m_bESB2 = besb2;
        }
    }
    
    public boolean genDotNetProxy() {
        return this.m_bDotNet;
    }
    
    public void setGenDotNetProxy(final boolean bDotNet) {
        if (this.m_bDotNet != bDotNet) {
            this.m_bDirty = true;
            this.m_bDotNet = bDotNet;
        }
    }
    
    public boolean isDirty() {
        return this.m_bDirty;
    }
    
    public int getJCompilerType() {
        return this.m_nJCompilerType;
    }
    
    public int getDNCompilerType() {
        return this.m_nDNCompilerType;
    }
    
    public void setDNCompilerType(final int ndnCompilerType) {
        if (this.m_nDNCompilerType != ndnCompilerType) {
            this.m_bDirty = true;
            this.m_nDNCompilerType = ndnCompilerType;
        }
    }
    
    static boolean isCSCCompiler(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        return lowerCase.endsWith("csc") || lowerCase.endsWith("csc.exe");
    }
    
    static boolean isDNPathFinder(final String s) {
        return s.endsWith("PathFinder");
    }
    
    public void setJCompilerType(final int njCompilerType) {
        if (this.m_nJCompilerType != njCompilerType) {
            this.m_bDirty = true;
            this.m_nJCompilerType = njCompilerType;
        }
    }
    
    public String getJClasspath(final int n, final boolean b) {
        String s;
        if (b) {
            s = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(PGAppObj.fixOSPaths(3, this.m_strJClasspath), "<Install Dir>", PGGenInfo.INSTALLDIR), "<Proxy Dir>", PGAppObj.getAbsFilename(null, this.getWorkDir())), "<Default classes.zip>", PGGenInfo.JAVACCP);
        }
        else if (n == 1) {
            s = "<Proxy Dir>;<Install Dir>\\java\\progress.jar;<Install Dir>\\java\\messages.jar;<Install Dir>\\java\\prowin.jar;<Install Dir>\\java\\ext\\ecore.sdo.jar;<Install Dir>\\java\\ext\\ecore.change.jar;<Install Dir>\\java\\ext\\commonj.sdo.jar;<Install Dir>\\java\\ext\\ecore.jar;<Install Dir>\\java\\ext\\ecore.resources.jar;<Install Dir>\\java\\ext\\common.jar;<Install Dir>\\java\\ext\\ecore.xmi.jar;<Default classes.zip>";
        }
        else if (n == 2) {
            s = "<Proxy Dir>;<Install Dir>\\java\\progress.jar;<Install Dir>\\java\\messages.jar;<Install Dir>\\java\\prowin.jar;<Install Dir>\\java\\ext\\ecore.sdo.jar;<Install Dir>\\java\\ext\\ecore.change.jar;<Install Dir>\\java\\ext\\commonj.sdo.jar;<Install Dir>\\java\\ext\\ecore.jar;<Install Dir>\\java\\ext\\ecore.resources.jar;<Install Dir>\\java\\ext\\common.jar;<Install Dir>\\java\\ext\\ecore.xmi.jar";
        }
        else {
            s = PGAppObj.fixOSPaths(1, this.m_strJClasspath);
        }
        return s;
    }
    
    public void setJClasspath(String concat) {
        if (concat != null) {
            final int index = concat.indexOf("<Install Dir>\\java\\progress.zip");
            if (index > -1) {
                concat = concat.substring(0, index).concat("<Install Dir>\\java\\progress.jar;<Install Dir>\\java\\messages.jar;<Install Dir>\\java\\prowin.jar").concat(concat.substring(index + "<Install Dir>\\java\\progress.zip".length() - 1 + 1));
            }
        }
        if (this.m_strJClasspath == null || !this.m_strJClasspath.equals(concat)) {
            this.m_bDirty = true;
            this.m_strJClasspath = PGAppObj.fixOSPaths(1, concat);
        }
    }
    
    public String getJavaPost11Classpath() {
        return Generator.insertVariable(PGAppObj.fixOSPaths(3, "<Install Dir>\\java\\ext\\wsdl4j.jar;<Install Dir>\\java\\progress.jar;<Install Dir>\\java\\messages.jar;<Install Dir>\\java\\ext\\xmlParserAPIs.jar;<Install Dir>\\java\\ext\\soap.jar;<Install Dir>\\java\\ext\\xercesImpl.jar"), "<Install Dir>", PGGenInfo.INSTALLDIR);
    }
    
    public String getJVM() {
        return Generator.insertVariable(PGAppObj.fixOSPath(3, "<Install Dir>\\jdk\\bin\\java"), "<Install Dir>", PGGenInfo.INSTALLDIR);
    }
    
    public String getJCPSwitch(final int n) {
        if (n == 1) {
            return "-classpath";
        }
        if (n == 2) {
            return "/cp:p";
        }
        return this.m_strJCPSwitch;
    }
    
    public void setJCPSwitch(final String s) {
        if (this.m_strJCPSwitch == null || !this.m_strJCPSwitch.equals(s)) {
            this.m_bDirty = true;
            this.m_strJCPSwitch = s;
        }
    }
    
    public void setBatch(final boolean batch) {
        this.m_batch = batch;
    }
    
    static void setCurrentAppObj(final PGAppObj currAppObj) {
        PGGenInfo.m_currAppObj = currAppObj;
    }
    
    static PGAppObj getCurrentAppObj() {
        return PGGenInfo.m_currAppObj;
    }
    
    static void setCurrentProcDetail(final PGProcDetail currProcDetail) {
        PGGenInfo.m_currProcDetail = currProcDetail;
        PGGenInfo.m_currMethodDetail = null;
    }
    
    static PGProcDetail getCurrentProcDetail() {
        return PGGenInfo.m_currProcDetail;
    }
    
    static void setCurrentMethodDetail(final PGMethodDetail currMethodDetail) {
        PGGenInfo.m_currMethodDetail = currMethodDetail;
    }
    
    static PGMethodDetail getCurrentMethodDetail() {
        return PGGenInfo.m_currMethodDetail;
    }
    
    static void setDataSetList(final Vector vDataSetList) {
        PGGenInfo.m_vDataSetList = vDataSetList;
    }
    
    static Vector getDataSetList() {
        return PGGenInfo.m_vDataSetList;
    }
    
    static void setDataTableList(final Vector vDataTableList) {
        PGGenInfo.m_vDataTableList = vDataTableList;
    }
    
    static Vector getDataTableList() {
        return PGGenInfo.m_vDataTableList;
    }
    
    public static String valWorkDir(final String s) {
        String s2 = null;
        if (s == null || s.length() == 0) {
            s2 = getMsg(8099442454849133699L, new Object[] { getResources().getTranString("PG_WorkDir") });
        }
        else {
            final String checkDir = PGAppObj.checkDir(s);
            if (checkDir != null) {
                s2 = getMsg(8099442454849133704L, new Object[] { checkDir });
            }
            else if (s.indexOf(" ") > 0) {
                s2 = getMsg(8099442454849133704L, new Object[] { s });
            }
        }
        return s2;
    }
    
    public static String valProgId(final String s) {
        String msg = null;
        if (s.length() == 0 || s == null) {
            msg = getMsg(8099442454849133699L, new Object[] { getResources().getTranString("PG_ProgId") });
        }
        return msg;
    }
    
    public static ProgressResources getResources() {
        return PGGenInfo.m_resources;
    }
    
    static String getMsg(final long n, final Object[] array) {
        return new Open4GLException(n, array).getMessage();
    }
    
    static boolean isJVCCompiler(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        return lowerCase.endsWith("jvc") || lowerCase.endsWith("jvc.exe");
    }
    
    static boolean packageEqualsObjName(final PGAppObj pgAppObj, final String anObject) {
        if (pgAppObj == null) {
            return false;
        }
        final Vector perProcVector = pgAppObj.getPerProcVector();
        final Vector subObjectVector = pgAppObj.getSubObjectVector();
        if (pgAppObj.getAppObjectName().equals(anObject)) {
            return true;
        }
        if (perProcVector != null) {
            for (int i = 0; i < perProcVector.size(); ++i) {
                final PGProc pgProc = perProcVector.elementAt(i);
                final PGProcDetail procDetail = pgProc.getProcDetail();
                if (procDetail == null) {
                    if (pgProc.getProcName().equals(anObject)) {
                        return true;
                    }
                }
                else if (procDetail.getMethodName().equals(anObject)) {
                    return true;
                }
            }
        }
        if (subObjectVector != null) {
            for (int j = 0; j < subObjectVector.size(); ++j) {
                if (packageEqualsObjName(subObjectVector.elementAt(j), anObject)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void openLogFile(final String str) throws Open4GLException {
        final String string = this.getWorkDir() + str + ".log";
        try {
            this.m_logFile = new PrintWriter(new FileOutputStream(PGAppObj.getAbsFilename(null, string)));
        }
        catch (IOException ex) {
            throw new Open4GLException(8099442454849134356L, new Object[] { string });
        }
    }
    
    public void logIt(final int n, final String s, final String str, final int n2) {
        String s2 = "";
        if (s != null) {
            s2 += getResources().getTranString(s);
        }
        if (str != null) {
            s2 = s2 + " " + str;
        }
        if (s2 != "" && n2 == 1 && !this.m_batch) {
            try {
                Class.forName("com.progress.open4gl.proxygen.gui.ProxyGen").getMethod("setStatusInfo", Class.forName("java.lang.String")).invoke(null, new String(s2));
            }
            catch (ClassNotFoundException ex) {}
            catch (Exception ex2) {}
        }
        this.logIt(PGGenInfo.m_indent[n] + s2, n2);
    }
    
    public void logIt(final String s, final int n) {
        if (n == 2) {
            if (this.m_bVerbose) {
                this.m_logFile.println(s);
            }
        }
        else {
            this.m_logFile.println(s);
        }
    }
    
    public void closeLogFile() {
        if (this.m_logFile != null) {
            this.m_logFile.close();
            this.m_logFile = null;
        }
    }
    
    public void createCompletedLogFile(final String str) throws Open4GLException {
        final String string = this.getWorkDir() + str + "_cmpl_x_045_8_y.log";
        try {
            this.m_completedLogFile = new PrintWriter(new FileOutputStream(PGAppObj.getAbsFilename(null, string)));
        }
        catch (IOException ex) {
            throw new Open4GLException(8099442454849134356L, new Object[] { string });
        }
        this.m_completedLogFile.println("Generation completed. See log file for details.");
        this.m_completedLogFile.close();
        this.m_completedLogFile = null;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.m_logFile = null;
        boolean b = false;
        boolean b2 = false;
        final PGVersion pgVersion = PGAppObj.getPGVersion();
        if (pgVersion == null || pgVersion.getPxgVersion().startsWith("9.1A")) {
            b = true;
        }
        else if (pgVersion.getPxgVersion().equals("01")) {
            b2 = true;
        }
        if (b) {
            if (this.m_strJCompiler != null && isJVCCompiler(this.m_strJCompiler)) {
                this.m_nJCompilerType = 3;
                this.m_strJClasspath = this.getAXClasspath(2, false, null);
                this.m_strJCPSwitch = "/cp:p";
                this.m_strJSwitches = "/x- /nomessage";
            }
            else {
                this.m_nJCompilerType = 3;
                this.m_strJClasspath = this.getJClasspath(1, false);
                this.m_strJCPSwitch = "-classpath";
            }
            this.m_nAXCompilerType = 2;
            this.m_strAXClasspath = this.getAXClasspath(2, false, null);
            this.m_strAXCPSwitch = "/cp:p";
            this.m_strAXSwitches = "/x- /nomessage";
            if (pgVersion != null) {
                pgVersion.setCurrentPxgVersion();
            }
        }
        else {
            this.setJClasspath(this.m_strJClasspath);
            this.setAXClasspath(this.m_strAXClasspath);
        }
        this.m_wsdlGenInfo = new WSDLGenInfo();
        if (b || b2) {
            this.m_bWebServices = false;
            this.m_dwGenInfo = new DWGenInfo();
            this.m_bConnectionFree = false;
        }
        this.m_wsdlGenInfo.setDWGenInfo(this.m_dwGenInfo);
        this.m_nDNCompilerType = 4;
        this.m_strDNCompiler = this.getDNCompiler(4, false);
        this.m_strDNSwitches = "";
        this.m_strDNXSDGenerator = this.getDNXSDGenerator(4, false);
        this.m_strDNNamespace = "";
        this.m_strDNDSNamespace = "";
        this.m_bDNUseNamespaceAsDirs = true;
        this.m_bDNUseDefDSNamespace = true;
        this.m_strDNTitle = "";
        this.m_strDNVersion = "";
        this.m_strDNDesc = "";
        this.m_strDNCompany = "";
        this.m_strDNProduct = "";
        this.m_strDNPublicKey = "";
        this.m_bDNDelaySign = false;
        this.m_bDirty = false;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        this.m_bDirty = false;
    }
    
    public WSDLGenInfo getWSDLGenInfo() {
        return this.m_wsdlGenInfo;
    }
    
    public void setWSDLGenInfo(final WSDLGenInfo wsdlGenInfo) {
        this.m_wsdlGenInfo = wsdlGenInfo;
    }
    
    public DWGenInfo getDWGenInfo() {
        return this.m_dwGenInfo;
    }
    
    public void setDWGenInfo(final DWGenInfo dwGenInfo) {
        this.m_dwGenInfo = dwGenInfo;
        this.m_wsdlGenInfo.setDWGenInfo(this.m_dwGenInfo);
    }
    
    public boolean getConnectionFree() {
        return this.m_bConnectionFree;
    }
    
    public void setConnectionFree(final boolean bConnectionFree) {
        this.m_bConnectionFree = bConnectionFree;
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            if (this.m_strAuthor != null) {
                String string;
                if (b) {
                    string = str + ":Author";
                }
                else {
                    string = "Author";
                }
                xmlSerializer.startElement(s, "Author", string, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strAuthor.toCharArray(), 0, this.m_strAuthor.length());
                xmlSerializer.endElement(s, "Author", string);
            }
            if (this.m_strVersion != null) {
                String string2;
                if (b) {
                    string2 = str + ":VersionString";
                }
                else {
                    string2 = "VersionString";
                }
                xmlSerializer.startElement(s, "VersionString", string2, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strVersion.toCharArray(), 0, this.m_strVersion.length());
                xmlSerializer.endElement(s, "VersionString", string2);
            }
            String string3;
            if (b) {
                string3 = str + ":VersionNumber";
            }
            else {
                string3 = "VersionNumber";
            }
            xmlSerializer.startElement(s, "VersionNumber", string3, (Attributes)null);
            final String string4 = Integer.toString(this.m_nPGVersion);
            ((BaseMarkupSerializer)xmlSerializer).characters(string4.toCharArray(), 0, string4.length());
            xmlSerializer.endElement(s, "VersionNumber", string3);
            if (this.m_strPackage != null) {
                String string5;
                if (b) {
                    string5 = str + ":Package";
                }
                else {
                    string5 = "Package";
                }
                xmlSerializer.startElement(s, "Package", string5, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strPackage.toCharArray(), 0, this.m_strPackage.length());
                xmlSerializer.endElement(s, "Package", string5);
            }
            if (this.m_strService != null) {
                String string6;
                if (b) {
                    string6 = str + ":Service";
                }
                else {
                    string6 = "Service";
                }
                xmlSerializer.startElement(s, "Service", string6, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strService.toCharArray(), 0, this.m_strService.length());
                xmlSerializer.endElement(s, "Service", string6);
            }
            final String s2 = "WorkDir";
            if (this.m_strWorkDir != null) {
                final String qName = this.getQName(b, str, s2);
                xmlSerializer.startElement(s, s2, qName, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strWorkDir.toCharArray(), 0, this.m_strWorkDir.length());
                xmlSerializer.endElement(s, s2, qName);
            }
            final String s3 = "VerboseLogging";
            final String value = String.valueOf(this.m_bVerbose);
            final String qName2 = this.getQName(b, str, s3);
            xmlSerializer.startElement(s, s3, qName2, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value.toCharArray(), 0, value.length());
            xmlSerializer.endElement(s, s3, qName2);
            final String s4 = "SaveBeforeGen";
            final String value2 = String.valueOf(this.m_bSaveBeforeGen);
            final String qName3 = this.getQName(b, str, s4);
            xmlSerializer.startElement(s, s4, qName3, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value2.toCharArray(), 0, value2.length());
            xmlSerializer.endElement(s, s4, qName3);
            final String s5 = "DotNetClient";
            final String value3 = String.valueOf(this.m_bDotNet);
            final String qName4 = this.getQName(b, str, s5);
            xmlSerializer.startElement(s, s5, qName4, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value3.toCharArray(), 0, value3.length());
            xmlSerializer.endElement(s, s5, qName4);
            final String s6 = "JavaClient";
            final String value4 = String.valueOf(this.m_bJava);
            final String qName5 = this.getQName(b, str, s6);
            xmlSerializer.startElement(s, s6, qName5, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value4.toCharArray(), 0, value4.length());
            xmlSerializer.endElement(s, s6, qName5);
            final String s7 = "WebServicesClient";
            final String value5 = String.valueOf(this.m_bWebServices);
            final String qName6 = this.getQName(b, str, s7);
            xmlSerializer.startElement(s, s7, qName6, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value5.toCharArray(), 0, value5.length());
            xmlSerializer.endElement(s, s7, qName6);
            final String s8 = "ESBClient";
            final String value6 = String.valueOf(this.m_bESB);
            final String qName7 = this.getQName(b, str, s8);
            xmlSerializer.startElement(s, s8, qName7, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value6.toCharArray(), 0, value6.length());
            xmlSerializer.endElement(s, s8, qName7);
            final String s9 = "ESBClient2";
            final String value7 = String.valueOf(this.m_bESB2);
            final String qName8 = this.getQName(b, str, s9);
            xmlSerializer.startElement(s, s9, qName8, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value7.toCharArray(), 0, value7.length());
            xmlSerializer.endElement(s, s9, qName8);
            final String s10 = "UserDefinedAppService";
            final String value8 = String.valueOf(this.m_bUserDefinedAppService);
            final String qName9 = this.getQName(b, str, s10);
            xmlSerializer.startElement(s, s10, qName9, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value8.toCharArray(), 0, value8.length());
            xmlSerializer.endElement(s, s10, qName9);
            final String s11 = "JavaCompilerType";
            final String string7 = Integer.toString(this.m_nJCompilerType);
            final String qName10 = this.getQName(b, str, s11);
            xmlSerializer.startElement(s, s11, qName10, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string7.toCharArray(), 0, string7.length());
            xmlSerializer.endElement(s, s11, qName10);
            final String s12 = "JavaCompiler";
            if (this.m_strJCompiler != null) {
                final String qName11 = this.getQName(b, str, s12);
                xmlSerializer.startElement(s, s12, qName11, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strJCompiler.toCharArray(), 0, this.m_strJCompiler.length());
                xmlSerializer.endElement(s, s12, qName11);
            }
            final String s13 = "JavaSwitches";
            if (this.m_strJSwitches != null) {
                final String qName12 = this.getQName(b, str, s13);
                xmlSerializer.startElement(s, s13, qName12, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strJSwitches.toCharArray(), 0, this.m_strJSwitches.length());
                xmlSerializer.endElement(s, s13, qName12);
            }
            final String s14 = "JavaClasspathSwitch";
            if (this.m_strJCPSwitch != null) {
                final String qName13 = this.getQName(b, str, s14);
                xmlSerializer.startElement(s, s14, qName13, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strJCPSwitch.toCharArray(), 0, this.m_strJCPSwitch.length());
                xmlSerializer.endElement(s, s14, qName13);
            }
            final String s15 = "JavaClasspath";
            if (this.m_strJClasspath != null) {
                final String qName14 = this.getQName(b, str, s15);
                xmlSerializer.startElement(s, s15, qName14, (Attributes)null);
                this.m_strJClasspath.toCharArray();
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strJClasspath.toCharArray(), 0, this.m_strJClasspath.length());
                xmlSerializer.endElement(s, s15, qName14);
            }
            final String s16 = "DotNetCompilerType";
            final String string8 = Integer.toString(this.m_nDNCompilerType);
            final String qName15 = this.getQName(b, str, s16);
            xmlSerializer.startElement(s, s16, qName15, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string8.toCharArray(), 0, string8.length());
            xmlSerializer.endElement(s, s16, qName15);
            final String s17 = "DotNetCompiler";
            if (this.m_strDNCompiler != null) {
                final String qName16 = this.getQName(b, str, s17);
                xmlSerializer.startElement(s, s17, qName16, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNCompiler.toCharArray(), 0, this.m_strDNCompiler.length());
                xmlSerializer.endElement(s, s17, qName16);
            }
            final String s18 = "DotNetSwitches";
            if (this.m_strDNSwitches != null) {
                final String qName17 = this.getQName(b, str, s18);
                xmlSerializer.startElement(s, s18, qName17, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNSwitches.toCharArray(), 0, this.m_strDNSwitches.length());
                xmlSerializer.endElement(s, s18, qName17);
            }
            final String s19 = "DotNetXSDGenerator";
            if (this.m_strDNXSDGenerator != null) {
                final String qName18 = this.getQName(b, str, s19);
                xmlSerializer.startElement(s, s19, qName18, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNXSDGenerator.toCharArray(), 0, this.m_strDNXSDGenerator.length());
                xmlSerializer.endElement(s, s19, qName18);
            }
            final String s20 = "DotNetNamespace";
            if (this.m_strDNNamespace != null) {
                final String qName19 = this.getQName(b, str, s20);
                xmlSerializer.startElement(s, s20, qName19, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNNamespace.toCharArray(), 0, this.m_strDNNamespace.length());
                xmlSerializer.endElement(s, s20, qName19);
            }
            final String s21 = "DotNetTitle";
            if (this.m_strDNTitle != null) {
                final String qName20 = this.getQName(b, str, s21);
                xmlSerializer.startElement(s, s21, qName20, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNTitle.toCharArray(), 0, this.m_strDNTitle.length());
                xmlSerializer.endElement(s, s21, qName20);
            }
            final String s22 = "DotNetVersion";
            if (this.m_strDNVersion != null) {
                final String qName21 = this.getQName(b, str, s22);
                xmlSerializer.startElement(s, s22, qName21, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNVersion.toCharArray(), 0, this.m_strDNVersion.length());
                xmlSerializer.endElement(s, s22, qName21);
            }
            final String s23 = "DotNetDesc";
            if (this.m_strDNDesc != null) {
                final String qName22 = this.getQName(b, str, s23);
                xmlSerializer.startElement(s, s23, qName22, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNDesc.toCharArray(), 0, this.m_strDNDesc.length());
                xmlSerializer.endElement(s, s23, qName22);
            }
            final String s24 = "DotNetCompany";
            if (this.m_strDNCompany != null) {
                final String qName23 = this.getQName(b, str, s24);
                xmlSerializer.startElement(s, s24, qName23, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNCompany.toCharArray(), 0, this.m_strDNCompany.length());
                xmlSerializer.endElement(s, s24, qName23);
            }
            final String s25 = "DotNetProduct";
            if (this.m_strDNProduct != null) {
                final String qName24 = this.getQName(b, str, s25);
                xmlSerializer.startElement(s, s25, qName24, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNProduct.toCharArray(), 0, this.m_strDNProduct.length());
                xmlSerializer.endElement(s, s25, qName24);
            }
            final String s26 = "DotNetPublicKey";
            if (this.m_strDNPublicKey != null) {
                final String qName25 = this.getQName(b, str, s26);
                xmlSerializer.startElement(s, s26, qName25, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNPublicKey.toCharArray(), 0, this.m_strDNPublicKey.length());
                xmlSerializer.endElement(s, s26, qName25);
            }
            final String s27 = "DotNetDelaySign";
            final String value9 = String.valueOf(this.m_bDNDelaySign);
            final String qName26 = this.getQName(b, str, s27);
            xmlSerializer.startElement(s, s27, qName26, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value9.toCharArray(), 0, value9.length());
            xmlSerializer.endElement(s, s27, qName26);
            final String s28 = "DotNetRuntime";
            if (this.m_DNRuntime != null) {
                final String qName27 = this.getQName(b, str, s28);
                xmlSerializer.startElement(s, s28, qName27, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_DNRuntime.toCharArray(), 0, this.m_DNRuntime.length());
                xmlSerializer.endElement(s, s28, qName27);
            }
            final String s29 = "DotNetDataSetNamespace";
            if (this.m_strDNDSNamespace != null) {
                final String qName28 = this.getQName(b, str, s29);
                xmlSerializer.startElement(s, s29, qName28, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strDNDSNamespace.toCharArray(), 0, this.m_strDNDSNamespace.length());
                xmlSerializer.endElement(s, s29, qName28);
            }
            final String s30 = "DNUseDefaultDSNamespace";
            final String value10 = String.valueOf(this.m_bDNUseDefDSNamespace);
            final String qName29 = this.getQName(b, str, s30);
            xmlSerializer.startElement(s, s30, qName29, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value10.toCharArray(), 0, value10.length());
            xmlSerializer.endElement(s, s30, qName29);
            final String s31 = "DNUseNamespaceAsDirs";
            final String value11 = String.valueOf(this.m_bDNUseNamespaceAsDirs);
            final String qName30 = this.getQName(b, str, s31);
            xmlSerializer.startElement(s, s31, qName30, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value11.toCharArray(), 0, value11.length());
            xmlSerializer.endElement(s, s31, qName30);
            final String s32 = "DotNetUseNullableTypes";
            final String value12 = String.valueOf(this.m_bDNUseNullableTypes);
            final String qName31 = this.getQName(b, str, s32);
            xmlSerializer.startElement(s, s32, qName31, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(value12.toCharArray(), 0, value12.length());
            xmlSerializer.endElement(s, s32, qName31);
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
        final String nodeValue = node.getAttributes().getNamedItem("isSessionFree").getNodeValue();
        if (nodeValue.equals("true") || nodeValue.equals("1")) {
            this.m_bConnectionFree = true;
        }
        else {
            this.m_bConnectionFree = false;
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue2 = WsaParser.extractNodeValue(item);
                if (localName.equals("Author")) {
                    this.m_strAuthor = nodeValue2;
                }
                else if (localName.equals("VersionString")) {
                    this.m_strVersion = nodeValue2;
                }
                else if (localName.equals("VersionNumber")) {
                    this.m_nPGVersion = Integer.parseInt(nodeValue2);
                }
                else if (localName.equals("Package")) {
                    this.m_strPackage = nodeValue2;
                }
                else if (localName.equals("Service")) {
                    this.m_strService = nodeValue2;
                }
                else if (localName.equals("WorkDir")) {
                    this.m_strWorkDir = nodeValue2;
                }
                else if (localName.equals("VerboseLogging")) {
                    this.m_bVerbose = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("SaveBeforeGen")) {
                    this.m_bSaveBeforeGen = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("JavaClient")) {
                    this.m_bJava = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("WebServicesClient")) {
                    this.m_bWebServices = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("ESBClient")) {
                    this.m_bESB = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("ESBClient2")) {
                    this.m_bESB2 = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("UserDefinedAppService")) {
                    this.m_bUserDefinedAppService = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("JavaCompilerType")) {
                    this.m_nJCompilerType = Integer.parseInt(nodeValue2);
                }
                else if (localName.equals("JavaCompiler")) {
                    this.m_strJCompiler = nodeValue2;
                }
                else if (localName.equals("JavaSwitches")) {
                    this.m_strJSwitches = nodeValue2;
                }
                else if (localName.equals("JavaClasspathSwitch")) {
                    this.m_strJCPSwitch = nodeValue2;
                }
                else if (localName.equals("JavaClasspath")) {
                    this.m_strJClasspath = nodeValue2;
                }
                else if (localName.equals("DotNetClient")) {
                    this.m_bDotNet = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("DotNetCompilerType")) {
                    this.m_nDNCompilerType = Integer.parseInt(nodeValue2);
                }
                else if (localName.equals("DotNetCompiler")) {
                    this.m_strDNCompiler = nodeValue2;
                }
                else if (localName.equals("DotNetSwitches")) {
                    this.m_strDNSwitches = nodeValue2;
                }
                else if (localName.equals("DotNetXSDGenerator")) {
                    this.m_strDNXSDGenerator = nodeValue2;
                }
                else if (localName.equals("DotNetNamespace")) {
                    this.m_strDNNamespace = nodeValue2;
                }
                else if (localName.equals("DotNetTitle")) {
                    this.m_strDNTitle = nodeValue2;
                }
                else if (localName.equals("DotNetVersion")) {
                    this.m_strDNVersion = nodeValue2;
                }
                else if (localName.equals("DotNetDesc")) {
                    this.m_strDNDesc = nodeValue2;
                }
                else if (localName.equals("DotNetCompany")) {
                    this.m_strDNCompany = nodeValue2;
                }
                else if (localName.equals("DotNetProduct")) {
                    this.m_strDNProduct = nodeValue2;
                }
                else if (localName.equals("DotNetDelaySign")) {
                    this.m_bDNDelaySign = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("DotNetPublicKey")) {
                    this.m_strDNPublicKey = nodeValue2;
                    this.m_strDNPublicKey = this.updateWindowsPath(this.m_strDNPublicKey);
                }
                else if (localName.equals("DotNetRuntime")) {
                    this.m_DNRuntime = nodeValue2;
                }
                else if (localName.equals("DotNetStrongNamedRuntime")) {
                    if (Boolean.valueOf(nodeValue2)) {
                        this.m_DNRuntime = "Strongnamed-signed";
                    }
                    else {
                        this.m_DNRuntime = "Signed";
                    }
                }
                else if (localName.equals("DotNetDataSetNamespace")) {
                    this.m_strDNDSNamespace = nodeValue2;
                }
                else if (localName.equals("DNUseDefaultDSNamespace")) {
                    this.m_bDNUseDefDSNamespace = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("DNUseNamespaceAsDirs")) {
                    this.m_bDNUseNamespaceAsDirs = Boolean.valueOf(nodeValue2);
                }
                else if (localName.equals("DotNetUseNullableTypes")) {
                    this.m_bDNUseNullableTypes = Boolean.valueOf(nodeValue2);
                }
            }
        }
    }
    
    private String updateWindowsPath(final String str) {
        String s = "";
        final String str2 = "\\\\";
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "\\", false);
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (nextToken != null && nextToken.length() > 0) {
                if (!s.equals("")) {
                    s += str2;
                }
                s += nextToken;
            }
        }
        return s;
    }
    
    void doJClasspathUpdate() {
        if (this.m_strJClasspath.indexOf("ecore.sdo.jar") == -1) {
            this.m_strJClasspath = this.m_strJClasspath + ";" + "<Install Dir>\\java\\ext\\ecore.sdo.jar;<Install Dir>\\java\\ext\\ecore.change.jar;<Install Dir>\\java\\ext\\commonj.sdo.jar;<Install Dir>\\java\\ext\\ecore.jar;<Install Dir>\\java\\ext\\ecore.resources.jar;<Install Dir>\\java\\ext\\common.jar;<Install Dir>\\java\\ext\\ecore.xmi.jar";
        }
    }
    
    static {
        PGGenInfo.m_resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PGBundle");
        m_indent = new String[] { System.getProperty("line.separator") + "** ", "   ", "      ", "         ", "            ", "   -- " };
        fileSep = System.getProperty("file.separator");
        isWindows = (System.getProperty("os.name").indexOf("Windows") >= 0);
        INSTALLDIR = PGAppObj.fixOSPath(3, System.getProperty("Install.Dir"));
        JAVACCP = PGAppObj.fixOSPath(3, System.getProperty("JavacCP"));
        PGGenInfo.m_currAppObj = null;
        PGGenInfo.m_currProcDetail = null;
        PGGenInfo.m_currMethodDetail = null;
    }
}
