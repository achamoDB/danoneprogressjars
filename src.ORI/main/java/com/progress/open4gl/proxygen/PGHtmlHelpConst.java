// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public class PGHtmlHelpConst
{
    public static final String DEF_HELP_FILENAME = "proxygenintro.html";
    public static final String DEF_CHM_FILENAME = "proxygen.chm";
    public static final String ADDPROCS_TOPIC = "ProxygenAddProceduresDialogs.html";
    public static final String ADDPROPATH_TOPIC = "ProxygenPropathComponentDialog.html";
    public static final String CHGPROPATH_TOPIC = "ProxygenChangePropathDialog.html";
    public static final String GEN_TOPIC = "ProxygenPreferencesDialogs.html";
    public static final String MAINAO_TOPIC = "";
    public static final String MAINPROCS_TOPIC = "";
    public static final String METHOD_TOPIC = "ProxygenEditInternalProcedureDialog.html";
    public static final String PERSPROC_TOPIC = "ProxygenEditPersistentProcedureDialog.html";
    public static final String PREFS_TOPIC = "ProxygenPreferencesDialogs.html";
    public static final String PROC_TOPIC = "ProxygenEditProcedureDialog.html";
    public static final String GENADVANCED_TOPIC = "ProxygenCompilerOptionsDialog.html";
    static final String PROPHELP_SUB_DIR = "prohelp";
    static final String HTML_HELP_SUB_DIR = "html";
    
    static String helpFilename() {
        final String property = System.getProperty("file.separator");
        return System.getProperty("Install.Dir") + property + "prohelp" + property + "html" + property + "proxygenintro.html";
    }
    
    public static String getChmHelpFilename() {
        final String property = System.getProperty("file.separator");
        return System.getProperty("Install.Dir") + property + "prohelp" + property + "html" + property + "proxygen.chm";
    }
}
