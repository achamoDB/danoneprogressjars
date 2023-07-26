// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.util.ResourceBundle;
import java.util.StringTokenizer;
import com.progress.international.resources.ProgressResources;

public class ProgressVersion
{
    private static ProgressResources m_resources;
    private static String m_buildVersion;
    private static String m_buildMachine;
    private static String m_buildTime;
    private static String m_buildOS;
    private static String m_minVer;
    private static ProgressVersionInfo vInfo;
    
    public static String getVersion() {
        return ProgressVersion.vInfo.getVersionJNI();
    }
    
    public static String getVersionString() {
        return ProgressVersion.m_resources.getTranString("PR_Version") + " " + ProgressVersion.vInfo.getVersionJNI();
    }
    
    public static String getMachine() {
        return "";
    }
    
    public static String getMachineString() {
        return "";
    }
    
    public static String getTime() {
        return "";
    }
    
    public static String getTimeString() {
        return "";
    }
    
    public static String getOS() {
        return "";
    }
    
    public static String getOSString() {
        return "";
    }
    
    public static String getFullVersion() {
        return ProgressVersion.vInfo.getFullVersionJNI();
    }
    
    public static String getFullVersionString() {
        return ProgressVersion.m_resources.getTranString("PR_Version") + " " + ProgressVersion.vInfo.getFullVersionJNI();
    }
    
    public static int getBuildNumber() {
        return ProgressVersion.vInfo.getBuildNumberJNI();
    }
    
    public static int getMajorNumber() {
        return ProgressVersion.vInfo.getMajorNumberJNI();
    }
    
    public static int getMinorNumber() {
        return ProgressVersion.vInfo.getMinorNumberJNI();
    }
    
    public static String getMaintenanceLevel() {
        return ProgressVersion.vInfo.getMaintenanceLevelJNI();
    }
    
    public static int getServicePackNumber() {
        return ProgressVersion.vInfo.getServicePackNumberJNI();
    }
    
    public static int getTemporaryFixNumber() {
        return ProgressVersion.vInfo.getTemporaryFixNumberJNI();
    }
    
    public static String getBaseVersion() {
        return ProgressVersion.vInfo.getBaseVersionJNI();
    }
    
    public static boolean validPatchlevel(final String s) {
        int int1 = 0;
        final String validatePatchJNI = ProgressVersion.vInfo.validatePatchJNI(s);
        boolean b;
        if (validatePatchJNI == null) {
            b = false;
        }
        else {
            final StringTokenizer stringTokenizer = new StringTokenizer(validatePatchJNI, "+");
            if (stringTokenizer.countTokens() != 3) {
                b = false;
            }
            else {
                final String nextToken = stringTokenizer.nextToken();
                stringTokenizer.nextToken();
                ProgressVersion.m_minVer = stringTokenizer.nextToken();
                try {
                    int1 = Integer.parseInt(nextToken);
                }
                catch (NumberFormatException ex) {}
                b = (int1 == 1);
            }
        }
        return b;
    }
    
    public static String getMinVer() {
        return ProgressVersion.m_minVer;
    }
    
    static {
        ProgressVersion.m_resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.ProgressBundle");
        ProgressVersion.m_buildVersion = "10.2B";
        ProgressVersion.m_buildMachine = "solbuild14";
        ProgressVersion.m_buildTime = "Jun  6 18:45:17 EDT 2009";
        ProgressVersion.m_buildOS = "solaris 5.9";
        ProgressVersion.m_minVer = null;
        ProgressVersion.vInfo = new ProgressVersionInfo();
    }
}
