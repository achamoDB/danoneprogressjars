// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import com.progress.common.log.ProLog;
import com.progress.common.util.Environment;
import com.progress.common.util.ProgressVersion;

public class FathomInitParams
{
    public static String FATHOM_ENABLED;
    public static String PROGRESS_WORK_DIR;
    public static String PROGRESS_DIR;
    public static String PROGRESS_DIR_SHORT;
    public static String FATHOM_DIR;
    public static String FATHOM_DIR_SHORT;
    public static String CONFIG_DIR;
    public static String ETC_DIR;
    public static String WORK_DIR;
    public static String INIT_FILENAME;
    private static boolean fInitialized;
    public static String PATH_SEPARATOR;
    public static String LINE_SEPARATOR;
    public static String FILE_SEPARATOR;
    public static String OS_NAME;
    public static boolean IS_WINDOWS;
    private static String m_fullInitFile;
    
    public static void setInitLoaded(final boolean fInitialized) {
        FathomInitParams.fInitialized = fInitialized;
    }
    
    public static boolean getInitLoaded() {
        return FathomInitParams.fInitialized;
    }
    
    public static void setInitialization(final String fullInitFile) {
        FathomInitParams.m_fullInitFile = fullInitFile;
    }
    
    public static String getInitialization() {
        return FathomInitParams.m_fullInitFile;
    }
    
    private static void loadini() throws FileNotFoundException, IOException {
        ProgressVersion.getVersion();
        final Environment environment = new Environment();
        String pathname = getInitialization();
        if (pathname == null) {
            ProLog.logd("Fathom", "WARNING: There is no environment setting for " + pathname);
            pathname = "." + File.separator + FathomInitParams.INIT_FILENAME;
            ProLog.logd("Fathom", "Defaulting to: " + pathname);
        }
        String line;
        while ((line = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathname)))).readLine()) != null) {
            final String trim = line.trim();
            if (trim.length() > 0) {
                if (trim.charAt(0) == '#') {
                    continue;
                }
                String trim2;
                boolean b;
                if (trim.toLowerCase().startsWith("set")) {
                    trim2 = trim.substring(3).trim();
                    b = true;
                }
                else {
                    b = false;
                    trim2 = trim;
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(trim2, "=", true);
                final int countTokens = stringTokenizer.countTokens();
                if (countTokens < 2 || countTokens > 3) {
                    continue;
                }
                final String nextToken = stringTokenizer.nextToken();
                final String nextToken2 = stringTokenizer.nextToken();
                final String value = (countTokens == 3) ? stringTokenizer.nextToken() : "";
                if (nextToken == null || !nextToken2.equals("=")) {
                    continue;
                }
                System.setProperty(nextToken, value);
                if (!FathomInitParams.IS_WINDOWS || !b) {
                    continue;
                }
                environment.setEnvironmentValue(trim2);
            }
        }
        FathomInitParams.fInitialized = true;
    }
    
    private static void checkForDirs(final String s, final String s2, final String s3, final String str) {
        final String string = s + FathomInitParams.FILE_SEPARATOR + "bin";
        final String string2 = s + FathomInitParams.FILE_SEPARATOR + "lib";
        final String anotherString = (s2 == null) ? "" : (s2 + FathomInitParams.FILE_SEPARATOR + "bin");
        final String anotherString2 = (s2 == null) ? "" : (s2 + FathomInitParams.FILE_SEPARATOR + "lib");
        int n = 0;
        int n2 = 0;
        if (str != null) {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, FathomInitParams.PATH_SEPARATOR);
            while (stringTokenizer.hasMoreTokens() && (n == 0 || n2 == 0)) {
                String s4 = stringTokenizer.nextToken();
                if (FathomInitParams.IS_WINDOWS) {
                    s4 = s4.replace('/', '\\');
                }
                if (s4.equalsIgnoreCase(string) || (FathomInitParams.IS_WINDOWS && s4.equalsIgnoreCase(anotherString))) {
                    n = 1;
                }
                else {
                    if (!s4.equalsIgnoreCase(string2) && (!FathomInitParams.IS_WINDOWS || !s4.equalsIgnoreCase(anotherString2))) {
                        continue;
                    }
                    n2 = 1;
                }
            }
        }
    }
    
    public static boolean getPropValue(final String s, final boolean b) {
        boolean equalsIgnoreCase = b;
        final String propValue = getPropValue(s, null);
        if (propValue != null) {
            equalsIgnoreCase = propValue.equalsIgnoreCase("true");
        }
        return equalsIgnoreCase;
    }
    
    public static String getPropValue(final String key, final String key2) {
        String property = null;
        if (!FathomInitParams.fInitialized) {
            try {
                loadini();
            }
            catch (Exception ex) {
                FathomInitParams.fInitialized = false;
                ProLog.logd("Fathom", "Failed to read Fathom init file " + ((FathomInitParams.m_fullInitFile == null) ? ("." + FathomInitParams.FILE_SEPARATOR + FathomInitParams.INIT_FILENAME) : FathomInitParams.m_fullInitFile));
            }
        }
        if (FathomInitParams.fInitialized) {
            try {
                property = System.getProperty(key);
            }
            catch (Exception ex2) {}
        }
        if (property == null && key2 != null) {
            return System.getProperty(key2);
        }
        return property;
    }
    
    public static boolean getFathomEnabled() {
        return getPropValue(FathomInitParams.FATHOM_ENABLED, true);
    }
    
    public static String getProgressWorkDir() {
        return getPropValue(FathomInitParams.PROGRESS_WORK_DIR, null);
    }
    
    public static String getProgressDir() {
        return getPropValue(FathomInitParams.PROGRESS_DIR, null);
    }
    
    public static String getProgressDirShort() {
        String s;
        if (FathomInitParams.IS_WINDOWS) {
            s = getPropValue(FathomInitParams.PROGRESS_DIR_SHORT, null);
        }
        else {
            s = getProgressDir();
        }
        return s;
    }
    
    public static String getFathomDir() {
        return getPropValue(FathomInitParams.FATHOM_DIR, null);
    }
    
    public static String getFathomDirShort() {
        String s;
        if (FathomInitParams.IS_WINDOWS) {
            s = getPropValue(FathomInitParams.FATHOM_DIR_SHORT, null);
        }
        else {
            s = getFathomDir();
        }
        return s;
    }
    
    public static String getConfigDir() {
        return getPropValue(FathomInitParams.CONFIG_DIR, "user.dir");
    }
    
    public static String getEtcDir() {
        return getPropValue(FathomInitParams.ETC_DIR, "user.dir");
    }
    
    static {
        FathomInitParams.FATHOM_ENABLED = "fathomEnabled";
        FathomInitParams.PROGRESS_WORK_DIR = "progressWorkDir";
        FathomInitParams.PROGRESS_DIR = "progressInstallDir";
        FathomInitParams.PROGRESS_DIR_SHORT = "progressInstallDirShort";
        FathomInitParams.FATHOM_DIR = "fathomInstallDir";
        FathomInitParams.FATHOM_DIR_SHORT = "fathomInstallDirShort";
        FathomInitParams.CONFIG_DIR = "fathomConfigDir";
        FathomInitParams.ETC_DIR = "fathomEtcDir";
        FathomInitParams.WORK_DIR = "fathomWorkDir";
        FathomInitParams.INIT_FILENAME = "fathom.init.params";
        FathomInitParams.fInitialized = false;
        FathomInitParams.PATH_SEPARATOR = System.getProperty("path.separator");
        FathomInitParams.LINE_SEPARATOR = System.getProperty("line.separator");
        FathomInitParams.FILE_SEPARATOR = System.getProperty("file.separator");
        FathomInitParams.OS_NAME = System.getProperty("os.name");
        FathomInitParams.IS_WINDOWS = (FathomInitParams.OS_NAME != null && FathomInitParams.OS_NAME.toLowerCase().startsWith("windows"));
        FathomInitParams.m_fullInitFile = null;
    }
}
