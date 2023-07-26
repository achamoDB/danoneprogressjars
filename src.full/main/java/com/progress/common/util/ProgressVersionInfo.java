// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class ProgressVersionInfo
{
    public native int getBuildNumber();
    
    public native int getMajorNumber();
    
    public native int getMinorNumber();
    
    public native String getMaintenanceLevel();
    
    public native int getServicePackNumber();
    
    public native int getTemporaryFixNumber();
    
    public native String getBaseVersion();
    
    public native String getVersion();
    
    public native String getFullVersion();
    
    public native String validatePatch(final String p0);
    
    public int getBuildNumberJNI() {
        return this.getBuildNumber();
    }
    
    public int getMajorNumberJNI() {
        return this.getMajorNumber();
    }
    
    public int getMinorNumberJNI() {
        return this.getMinorNumber();
    }
    
    public String getMaintenanceLevelJNI() {
        return this.getMaintenanceLevel();
    }
    
    public int getServicePackNumberJNI() {
        return this.getServicePackNumber();
    }
    
    public int getTemporaryFixNumberJNI() {
        return this.getTemporaryFixNumber();
    }
    
    public String getBaseVersionJNI() {
        return this.getBaseVersion();
    }
    
    public String getVersionJNI() {
        return this.getVersion();
    }
    
    public String getFullVersionJNI() {
        return this.getFullVersion();
    }
    
    public String validatePatchJNI(final String s) {
        return this.validatePatch(s);
    }
    
    static {
        System.load(new InstallPath().fullyQualifyFile("versioninfo.dll"));
    }
}
