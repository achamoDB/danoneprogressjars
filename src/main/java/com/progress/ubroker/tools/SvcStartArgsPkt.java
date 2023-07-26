// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.util.crypto;
import com.progress.ubroker.util.PropGroupDescriptor;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.chimera.adminserver.ServerPolicyInfo;
import com.progress.common.property.PropertyManager;
import com.progress.ubroker.util.UBPreferenceProperties;
import com.progress.ubroker.util.IPropConst;

public class SvcStartArgsPkt implements IPropConst
{
    public String m_classMain;
    public String m_svcName;
    public String m_personality;
    public String m_propath;
    public String m_username;
    public String m_password;
    public String m_groupname;
    public String m_workDir;
    public String[][] m_envVars;
    public String m_propFileSpec;
    public String m_svcfullGroupPath;
    public String m_jvmArgs;
    public String m_classpath;
    public String m_javaPolicy;
    public String m_umask;
    public UBPreferenceProperties m_preferences;
    public String m_canonicalName;
    private PropMgrPlugin m_pmpObject;
    public PropertyManager.PropertyCollection m_theProperties;
    private ServerPolicyInfo m_policyInfo;
    
    public SvcStartArgsPkt(final ServerPolicyInfo policyInfo, final String svcfullGroupPath, final String svcName, final PropMgrPlugin pmpObject) {
        this.m_policyInfo = null;
        this.m_policyInfo = policyInfo;
        this.m_pmpObject = pmpObject;
        this.m_svcfullGroupPath = svcfullGroupPath;
        this.m_personality = pmpObject.getPersonStrFromPropPath(this.m_svcfullGroupPath);
        this.m_svcName = svcName;
        this.m_theProperties = this.m_pmpObject.getPropertyCollection(this.m_svcfullGroupPath);
        this.m_canonicalName = this.m_pmpObject.getCanonicalName(PropMgrUtils.getSvcGroupPath(this.m_personality));
        this.makePacket();
    }
    
    public SvcStartArgsPkt(final String svcfullGroupPath, final String svcName, final PropMgrPlugin pmpObject) {
        this.m_policyInfo = null;
        this.m_pmpObject = pmpObject;
        this.m_svcfullGroupPath = svcfullGroupPath;
        this.m_personality = pmpObject.getPersonStrFromPropPath(this.m_svcfullGroupPath);
        this.m_svcName = svcName;
        this.m_theProperties = this.m_pmpObject.getPropertyCollection(this.m_svcfullGroupPath);
        this.m_canonicalName = this.m_pmpObject.getCanonicalName(PropMgrUtils.getSvcGroupPath(this.m_personality));
        this.makePacket();
    }
    
    public SvcStartArgsPkt(final PropMgrPlugin pmpObject, final String svcName) {
        this.m_policyInfo = null;
        this.m_pmpObject = pmpObject;
        this.m_svcName = svcName;
        final PropGroupDescriptor ubPersonStrForSvcName = pmpObject.getUBPersonStrForSvcName(this.m_svcName);
        this.m_personality = ubPersonStrForSvcName.getSvcTypeStr();
        this.m_svcfullGroupPath = ubPersonStrForSvcName.getfullPropSpec();
        this.m_theProperties = this.m_pmpObject.getPropertyCollection(this.m_svcfullGroupPath);
        this.makePacket();
    }
    
    public SvcStartArgsPkt(final PropMgrPlugin pmpObject, final int n, final String svcName) {
        this.m_policyInfo = null;
        this.m_pmpObject = pmpObject;
        this.m_svcName = svcName;
        final PropGroupDescriptor ubPersonStrForSvcName = pmpObject.getUBPersonStrForSvcName(this.m_svcName);
        this.m_personality = ubPersonStrForSvcName.getSvcTypeStr();
        this.m_svcfullGroupPath = ubPersonStrForSvcName.getfullPropSpec();
        this.m_theProperties = this.m_pmpObject.getPropertyCollection(n, this.m_svcfullGroupPath);
        this.makePacket(n);
    }
    
    private void makePacket() {
        this.m_workDir = this.m_pmpObject.getPropValueFromCollection("workDir", this.m_theProperties);
        this.m_username = this.m_pmpObject.getPropValueFromCollection("userName", this.m_theProperties);
        this.m_password = new crypto().decrypt(this.m_pmpObject.getPropValueFromCollection("password", this.m_theProperties));
        this.m_groupname = this.m_pmpObject.getPropValueFromCollection("groupName", this.m_theProperties);
        this.m_jvmArgs = this.m_pmpObject.getExpandedPropertyValue("jvmArgs", this.m_svcfullGroupPath);
        this.m_envVars = this.m_pmpObject.getCustomizedEnvVars(this.m_theProperties);
        this.m_propath = this.m_pmpObject.getExpandedPropertyValue("PROPATH", this.m_svcfullGroupPath, false);
        this.m_classMain = this.m_pmpObject.getPropValueOrDefaultFromCollection("classMain", this.m_theProperties);
        this.m_propFileSpec = this.m_pmpObject.getPropFilePath();
        this.m_preferences = this.m_pmpObject.getPreferences();
        if (this.m_policyInfo != null) {
            final String jvmArgs = this.m_jvmArgs;
            this.m_classpath = this.m_policyInfo.getPluginClasspath();
            if (this.m_classpath == null) {
                this.m_classpath = this.m_policyInfo.getClasspath();
            }
            this.m_javaPolicy = this.m_policyInfo.getPolicyFile();
            this.m_umask = this.m_policyInfo.getUmask();
            this.m_jvmArgs = this.m_policyInfo.getJvmArgs();
            if (this.m_jvmArgs == null || this.m_jvmArgs.length() == 0) {
                this.m_jvmArgs = jvmArgs;
            }
        }
    }
    
    private void makePacket(final int n) {
        this.m_workDir = this.m_pmpObject.getPropValueFromCollection(n, "workDir", this.m_theProperties);
        this.m_username = this.m_pmpObject.getPropValueFromCollection(n, "userName", this.m_theProperties);
        this.m_password = new crypto().decrypt(this.m_pmpObject.getPropValueFromCollection(n, "password", this.m_theProperties));
        this.m_groupname = this.m_pmpObject.getPropValueFromCollection(n, "groupName", this.m_theProperties);
        this.m_jvmArgs = this.m_pmpObject.getExpandedPropertyValue(n, "jvmArgs", this.m_svcfullGroupPath);
        this.m_envVars = this.m_pmpObject.getCustomizedEnvVars(n, this.m_theProperties);
        this.m_propath = this.m_pmpObject.getExpandedPropertyValue(n, "PROPATH", this.m_svcfullGroupPath, false);
        this.m_classMain = this.m_pmpObject.getPropValueOrDefaultFromCollection(n, "classMain", this.m_theProperties);
        this.m_propFileSpec = this.m_pmpObject.getPropFilePath(n);
        this.m_preferences = this.m_pmpObject.getPreferences(n);
        if (this.m_policyInfo != null) {
            final String jvmArgs = this.m_jvmArgs;
            this.m_classpath = this.m_policyInfo.getPluginClasspath();
            if (this.m_classpath == null) {
                this.m_classpath = this.m_policyInfo.getClasspath();
            }
            this.m_javaPolicy = this.m_policyInfo.getPolicyFile();
            this.m_umask = this.m_policyInfo.getUmask();
            this.m_jvmArgs = this.m_policyInfo.getJvmArgs();
            if (this.m_jvmArgs == null || this.m_jvmArgs.length() == 0) {
                this.m_jvmArgs = jvmArgs;
            }
        }
    }
    
    public void updateConfig() {
        this.m_theProperties = this.m_pmpObject.getPropertyCollection(this.m_svcfullGroupPath);
        this.makePacket();
    }
    
    public void updateConfig(final int n) {
        this.m_theProperties = this.m_pmpObject.getPropertyCollection(n, this.m_svcfullGroupPath);
        this.makePacket(n);
    }
    
    public static boolean isServiceOnNT() {
        return System.getProperty("os.name").indexOf("Windows") >= 0;
    }
    
    public PropMgrPlugin getPmpObject() {
        return this.m_pmpObject;
    }
}
