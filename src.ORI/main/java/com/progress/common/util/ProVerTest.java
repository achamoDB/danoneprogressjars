// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

class ProVerTest
{
    private void dispVersions() {
        System.out.println("Version " + ProgressVersion.getVersion());
        System.out.println("VersionString " + ProgressVersion.getVersionString());
        System.out.println("FullVersion " + ProgressVersion.getFullVersion());
        System.out.println("FullVersionString " + ProgressVersion.getFullVersionString());
        System.out.println("Build number " + ProgressVersion.getBuildNumber());
        System.out.println("Major number " + ProgressVersion.getMajorNumber());
        System.out.println("Minor number " + ProgressVersion.getMinorNumber());
        System.out.println("Maintenance level " + ProgressVersion.getMaintenanceLevel());
        System.out.println("Service Packnumber " + ProgressVersion.getServicePackNumber());
        System.out.println("Temp Fixnumber " + ProgressVersion.getTemporaryFixNumber());
        System.out.println("BaseVersion " + ProgressVersion.getBaseVersion());
        System.out.println("ValidPatch " + ProgressVersion.validPatchlevel("/tmp") + " Min version is " + ProgressVersion.getMinVer());
    }
    
    public static void main(final String[] array) {
        new ProVerTest().dispVersions();
    }
}
