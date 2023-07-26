// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import com.progress.ubroker.util.PropMgrUtils;
import com.progress.juniper.admin.JuniperProperties;
import com.progress.juniper.admin.JATools;
import java.io.File;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;

public class MergeCommandLine
{
    public static String FILE_SEPARATOR;
    public static String NEWLINE;
    private static final int ERROR_SUCCESS = 0;
    private static final int ERROR_BADTYPE = 1;
    private static final int ERROR_BADACTION = 2;
    private static final int ERROR_BADTARGET = 3;
    private static final int ERROR_NOTARGET = 4;
    private static final int ERROR_BADDELTA = 5;
    private static final int ERROR_CANTBACKUP = 6;
    private static final int ERROR_BADPROPERTY = 7;
    private static final int ERROR_NOOVERWRITE = 8;
    private static final int ERROR_BADLISTGROUP = 9;
    private static final int ERROR_NODLC = 10;
    private static final int ERROR_GENERIC = 11;
    private static final int ERROR_BADARGS = 12;
    private static final int ERROR_BADRECURSE = 13;
    private static boolean m_silent;
    
    private static void printUsageAndExit(final String x, final int status) {
        if (!MergeCommandLine.m_silent) {
            if (x != null) {
                System.err.println(x);
            }
            System.err.println("Usage: mergeprop <parameter> ...");
            System.err.println("       <parameter>:");
            System.err.println("             -action <list|listall> <property group>");
            System.err.println("                       or");
            System.err.println("             -action <update|create|delete>");
            System.err.println("             -delta  <file>");
            System.err.println();
            System.err.println("             -type   <database|ubroker|plugin|tools|none>");
            System.err.println("             -target <file>");
            System.err.println("             -validate");
            System.err.println("             -nobackup");
            System.err.println("             -recurse  (-type database only)");
            System.err.println("             -silent");
        }
        System.exit(status);
    }
    
    public static void main(final String[] array) {
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PRPMsgBundle");
        final String property = System.getProperty("Install.Dir");
        if (property == null) {
            printUsageAndExit(progressResources.getTranString("no dlc"), 10);
        }
        final MergeGetopt mergeGetopt = new MergeGetopt(array, property);
        if (mergeGetopt.getHelp()) {
            printUsageAndExit("", 0);
        }
        MergeCommandLine.m_silent = mergeGetopt.getSilent();
        final String invalidArgs = mergeGetopt.getInvalidArgs();
        if (invalidArgs.length() > 0) {
            printUsageAndExit(progressResources.getTranString("errors found", (Object)invalidArgs), 12);
        }
        if (!mergeGetopt.isCreate() && !mergeGetopt.isDelete() && !mergeGetopt.isListAll() && !mergeGetopt.isListOnly() && !mergeGetopt.isUpdate()) {
            printUsageAndExit(progressResources.getTranString("no action"), 2);
        }
        if (!mergeGetopt.isDatabaseType() && !mergeGetopt.isUbrokerType() && !mergeGetopt.isPluginType() && !mergeGetopt.isToolsType() && !mergeGetopt.isNoneType()) {
            printUsageAndExit(progressResources.getTranString("no type"), 1);
        }
        if (mergeGetopt.getRecurse() && !mergeGetopt.isDatabaseType()) {
            printUsageAndExit(progressResources.getTranString("recurse error"), 13);
        }
        if (mergeGetopt.getRecurse() && (mergeGetopt.isCreate() || mergeGetopt.isUpdate() || mergeGetopt.isListOnly())) {
            printUsageAndExit(progressResources.getTranString("recurse error"), 13);
        }
        final String propertyFile = mergeGetopt.getPropertyFile();
        final File file = new File(propertyFile);
        if (!file.exists() || propertyFile.length() == 0) {
            if (!mergeGetopt.isUbrokerType() || !mergeGetopt.isCreate()) {
                printUsageAndExit(progressResources.getTranString("Property file not found", (Object)propertyFile), 4);
            }
        }
        if (file.exists() && !file.canWrite()) {
            printUsageAndExit(progressResources.getTranString("Can't write property file", (Object)propertyFile), 3);
        }
        final String delta = mergeGetopt.getDelta();
        final File file2 = new File(delta);
        if (!mergeGetopt.isListAll() && !mergeGetopt.isListOnly() && (!file2.exists() || delta.length() == 0)) {
            printUsageAndExit(progressResources.getTranString("no delta file", (Object)delta), 5);
        }
        if (file2.exists() && !file2.canRead()) {
            printUsageAndExit(progressResources.getTranString("delta read error", (Object)delta), 5);
        }
        if ((mergeGetopt.isListAll() || mergeGetopt.isListOnly()) && (mergeGetopt.getGroupName() == null || mergeGetopt.getGroupName().length() == 0)) {
            printUsageAndExit(progressResources.getTranString("Invalid group:", (Object)""), 9);
        }
        try {
            final String propertyFile2 = mergeGetopt.getPropertyFile();
            PropertyManager propMgr = null;
            if (mergeGetopt.isDatabaseType()) {
                JATools.setIsServer();
                propMgr = new JuniperProperties(propertyFile2);
            }
            if (mergeGetopt.isUbrokerType()) {
                PropMgrUtils.setUpdateUtility(MergeProperties.m_includeAllRegistered = true);
                final PropMgrUtils propMgrUtils = new PropMgrUtils(propertyFile2, true, false);
                propMgr = PropMgrUtils.m_propMgr;
            }
            if (mergeGetopt.isPluginType() || mergeGetopt.isToolsType() || mergeGetopt.isNoneType()) {
                propMgr = new MergePropMgr(propertyFile2);
            }
            new MergeUtility(propMgr, mergeGetopt);
            System.exit(0);
        }
        catch (PropertyManager.SaveIOException ex) {
            if (!MergeCommandLine.m_silent) {
                System.out.println(" Exception: " + ex.getMessage());
            }
            System.exit(6);
        }
        catch (PropertyManager.PropertySyntaxException ex2) {
            if (!MergeCommandLine.m_silent) {
                System.out.println(" Exception: " + ex2.getMessage());
            }
            System.exit(7);
        }
        catch (PropertyManager.GroupNameException ex3) {
            if (!MergeCommandLine.m_silent) {
                System.out.println(" Exception: " + ex3.getMessage());
            }
            System.exit(8);
        }
        catch (PropertyManager.NoSuchGroupException ex4) {
            if (!MergeCommandLine.m_silent) {
                System.out.println(" Exception: " + ex4.getMessage());
            }
            System.exit(9);
        }
        catch (Exception ex5) {
            if (!MergeCommandLine.m_silent) {
                System.out.println(" Exception: " + ex5.getMessage());
            }
            System.exit(11);
        }
    }
    
    static {
        MergeCommandLine.FILE_SEPARATOR = System.getProperty("file.separator");
        MergeCommandLine.NEWLINE = System.getProperty("line.separator");
        MergeCommandLine.m_silent = false;
    }
}
