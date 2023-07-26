// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.runtime;

import com.progress.common.networkevents.EventBroker;
import com.progress.common.property.PropertyManager;

public class ManagementProperties extends PropertyManager
{
    private String m_propFilePath;
    private EventBroker m_asEventBroker;
    public static final String m_schemaFile = "management.schema";
    
    public ManagementProperties(final String propFilePath, final EventBroker asEventBroker) throws Exception {
        super("management.schema", asEventBroker);
        this.m_propFilePath = null;
        this.m_asEventBroker = null;
        if (propFilePath == null || propFilePath.length() == 0) {
            return;
        }
        try {
            this.load(propFilePath);
        }
        catch (LoadFileNotFoundException ex) {
            this.createDefaultPropFile(propFilePath);
        }
        this.m_propFilePath = propFilePath;
        this.m_asEventBroker = asEventBroker;
    }
    
    public void createDefaultPropFile(final String str) throws Exception {
        this.save(str, "Management Plugin Properties File", null, true, true, false);
        System.out.println("Created property file " + str + " with defaults");
    }
    
    public String getHostName() {
        return this.getProperty("Management.hostName");
    }
    
    public String getContainerName() {
        return this.getProperty("Management.containerName");
    }
    
    public String getDomainName() {
        return this.getProperty("Management.domainName");
    }
    
    public int getPort() {
        return this.getIntProperty("Management.port");
    }
    
    public boolean getIsMonitored() {
        return this.getBooleanProperty("Management.isMonitored");
    }
    
    public boolean getDirectoryService() {
        return this.getBooleanProperty("Management.directoryService");
    }
    
    public boolean setIsMonitored(final boolean b) {
        try {
            this.putBooleanProperty("Management.isMonitored", b);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean setIsDirectoryService(final boolean b) {
        try {
            this.putBooleanProperty("Management.directoryService", b);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean setHostName(final String s) {
        try {
            this.putProperty("Management.hostName", s);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean setContainerName(final String s) {
        try {
            this.putProperty("Management.containerName", s);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean setDomainName(final String s) {
        try {
            this.putProperty("Management.domainName", s);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean setPort(final int n) {
        try {
            this.putIntProperty("Management.port", n);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean savePropertyFile() {
        try {
            this.save(this.m_propFilePath, "Updated property file");
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
}
