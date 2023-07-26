// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.lang.reflect.Method;
import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.info.InfoFactory;
import java.util.Vector;
import java.util.Hashtable;
import com.progress.common.log.ProLog;
import com.sonicsw.mf.common.info.IManagementInfo;

public abstract class ReplicationPluginComponent extends DatabasePluginComponent implements IJAComponentConstants
{
    public boolean m_replicationIsLicensed;
    public static final IManagementInfo[] REPL_MANAGEMENT_INFO;
    
    public ReplicationPluginComponent() {
        this.m_replicationIsLicensed = true;
    }
    
    public abstract IManagementInfo[] getManagementInfo();
    
    public Boolean isReplicationLicensed() {
        return new Boolean(this.m_replicationIsLicensed);
    }
    
    public Boolean hasReplicationProperties(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.hasReplicationProperties(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean hasReplicationServer(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.hasReplicationServer(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public String[] getReplicationCtrlAgents(final String s) {
        String[] replicationCtrlAgents = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationCtrlAgents = replicationPluginComponent.getReplicationCtrlAgents(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationCtrlAgents;
    }
    
    public Boolean hasReplicationAgent(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.hasReplicationAgent(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean hasReplicationTransition(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.hasReplicationTransition(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Integer isReplicationEnabled(final String s) {
        Integer replicationEnabled = new Integer(0);
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationEnabled = replicationPluginComponent.isReplicationEnabled(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationEnabled;
    }
    
    public Boolean writeReplicationProperties(final String s) throws Exception {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.writeReplicationProperties(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Vector validateReplicationProperties(final String s, final String[] array, final String s2, final Hashtable hashtable, final Hashtable hashtable2) throws Exception {
        Vector validateReplicationProperties = new Vector();
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    validateReplicationProperties = replicationPluginComponent.validateReplicationProperties(s, array, s2, hashtable, hashtable2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return validateReplicationProperties;
    }
    
    public Hashtable getReplicationConfiguration(final String s, final String s2) {
        Hashtable replicationConfiguration = new Hashtable();
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationConfiguration = replicationPluginComponent.getReplicationConfiguration(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationConfiguration;
    }
    
    public Hashtable getReplicationConfigurationSchema(final String s, final String[] array) {
        Hashtable replicationConfigurationSchema = new Hashtable();
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationConfigurationSchema = replicationPluginComponent.getReplicationConfigurationSchema(s, array);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationConfigurationSchema;
    }
    
    public Hashtable getReplicationConfigurationSchema(final String s, final String s2) {
        Hashtable replicationConfigurationSchema = new Hashtable();
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationConfigurationSchema = replicationPluginComponent.getReplicationConfigurationSchema(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationConfigurationSchema;
    }
    
    public void setReplicationConfiguration(final String s, final String s2, final Hashtable hashtable) throws Exception {
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationPluginComponent.setReplicationConfiguration(s, s2, hashtable);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
    }
    
    public Hashtable getReplicationGroupSchemaHashtable(final String s, final String s2) {
        Hashtable replicationGroupSchemaHashtable = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationGroupSchemaHashtable = replicationPluginComponent.getReplicationGroupSchemaHashtable(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationGroupSchemaHashtable;
    }
    
    public Hashtable getReplicationGroupAttributeHashtable(final String s, final String s2) {
        Hashtable replicationGroupAttributeHashtable = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationGroupAttributeHashtable = replicationPluginComponent.getReplicationGroupAttributeHashtable(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationGroupAttributeHashtable;
    }
    
    public Hashtable getReplicationPropertySchemaHashtable(final String s, final String s2) {
        Hashtable replicationPropertySchemaHashtable = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationPropertySchemaHashtable = replicationPluginComponent.getReplicationPropertySchemaHashtable(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationPropertySchemaHashtable;
    }
    
    public Hashtable getReplicationCategorySchemaHashtable(final String s, final String s2) {
        Hashtable replicationCategorySchemaHashtable = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationCategorySchemaHashtable = replicationPluginComponent.getReplicationCategorySchemaHashtable(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationCategorySchemaHashtable;
    }
    
    public Hashtable getReplicationCategorySchemaHashtable(final String s, final String s2, final String s3) {
        Hashtable replicationCategorySchemaHashtable = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationCategorySchemaHashtable = replicationPluginComponent.getReplicationCategorySchemaHashtable(s, s2, s3);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationCategorySchemaHashtable;
    }
    
    public Hashtable getReplicationCategorySchemaHashtable(final String s, final String s2, final String[] array) {
        Hashtable replicationCategorySchemaHashtable = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationCategorySchemaHashtable = replicationPluginComponent.getReplicationCategorySchemaHashtable(s, s2, array);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationCategorySchemaHashtable;
    }
    
    public Hashtable getReplicationCategoryAttributeHashtable(final String s, final String s2) {
        Hashtable replicationCategoryAttributeHashtable = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationCategoryAttributeHashtable = replicationPluginComponent.getReplicationCategoryAttributeHashtable(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationCategoryAttributeHashtable;
    }
    
    public Hashtable makeReplicationPropertyValueHashtable(final String s, final String s2) {
        Hashtable replicationPropertyValueHashtable = null;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationPropertyValueHashtable = replicationPluginComponent.makeReplicationPropertyValueHashtable(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationPropertyValueHashtable;
    }
    
    public Boolean replicationCtrlAgentNameUsed(final String s, final String s2) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationCtrlAgentNameUsed(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public String replicationCtrlAgentMakeNewName(final String s) {
        String replicationCtrlAgentMakeNewName = "";
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    replicationCtrlAgentMakeNewName = replicationPluginComponent.replicationCtrlAgentMakeNewName(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return replicationCtrlAgentMakeNewName;
    }
    
    public Boolean replicationServerCreateNew(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationServerCreateNew(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean replicationAgentCreateNew(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationAgentCreateNew(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean replicationTransitionCreateNew(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationTransitionCreateNew(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean replicationCtrlAgentCreateNew(final String s, final String s2) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationCtrlAgentCreateNew(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean replicationDeleteCtrlAgent(final String s, final String s2) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationDeleteCtrlAgent(s, s2);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean replicationDeleteServer(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationDeleteServer(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean replicationDeleteAgent(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationDeleteAgent(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    public Boolean replicationPropertyRefresh(final String s) {
        Boolean b = Boolean.FALSE;
        try {
            if (this.m_replicationIsLicensed) {
                final IReplicationPluginComponent replicationPluginComponent = JADatabase.getReplicationPluginComponent(s);
                if (replicationPluginComponent != null) {
                    b = replicationPluginComponent.replicationPropertyRefresh(s);
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 5, ex.toString());
        }
        return b;
    }
    
    static {
        REPL_MANAGEMENT_INFO = new IManagementInfo[DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO.length + 39];
        int length = DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO, 0, ReplicationPluginComponent.REPL_MANAGEMENT_INFO, 0, DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO.length);
        try {
            method = ReplicationPluginComponent.class.getMethod("isReplicationLicensed", (Class[])new Class[0]);
        }
        catch (Exception ex) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the database licensed for database replication?", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("hasReplicationProperties", String.class);
        }
        catch (Exception ex2) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is there a replication properties file associated with this database?", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("isReplicationEnabled", String.class);
        }
        catch (Exception ex3) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is this database replication enabled?  Returns 0 - not enabled; 11 - source; 12 - target.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("hasReplicationServer", String.class);
        }
        catch (Exception ex4) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is there a replication server associated with this database?", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("hasReplicationAgent", String.class);
        }
        catch (Exception ex5) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is there a replication agent associated with this database?", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("hasReplicationTransition", String.class);
        }
        catch (Exception ex6) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is there a replication transition associated with this database?", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationCtrlAgents", String.class);
        }
        catch (Exception ex7) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Are there replication control agents associated with this database?", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("setReplicationConfiguration", String.class, String.class, Hashtable.class);
        }
        catch (Exception ex8) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set properties for the specified configuration (property) name.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationConfiguration", String.class, String.class);
        }
        catch (Exception ex9) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return a set of properties for the specified configuration (property) name.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationConfigurationSchema", String.class, String.class);
        }
        catch (Exception ex10) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified configuration element name.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationConfigurationSchema", String.class, String[].class);
        }
        catch (Exception ex11) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of configuration element names.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationGroupSchemaHashtable", String.class, String.class);
        }
        catch (Exception ex12) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for all groups.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationGroupAttributeHashtable", String.class, String.class);
        }
        catch (Exception ex13) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the attributes defined for the specified group.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationPropertySchemaHashtable", String.class, String.class);
        }
        catch (Exception ex14) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the property schema definition for the specified group.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationCategorySchemaHashtable", String.class, String.class);
        }
        catch (Exception ex15) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationCategorySchemaHashtable", String.class, String.class);
        }
        catch (Exception ex16) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationCategorySchemaHashtable", String.class, String.class);
        }
        catch (Exception ex17) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("getReplicationCategoryAttributeHashtable", String.class, String.class);
        }
        catch (Exception ex18) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the attributes defined for the specified category.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("makeReplicationPropertyValueHashtable", String.class, String.class);
        }
        catch (Exception ex19) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the property values defined for the specified group.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("writeReplicationProperties", String.class);
        }
        catch (Exception ex20) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Update/save the replication properties file associated with this database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("validateReplicationProperties", String.class, String[].class, String.class, Hashtable.class, Hashtable.class);
        }
        catch (Exception ex21) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Validate the provided values for the specified database and group.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationCtrlAgentNameUsed", String.class, String.class);
        }
        catch (Exception ex22) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the control agent group display name available for the named database?", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationCtrlAgentMakeNewName", String.class);
        }
        catch (Exception ex23) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Propose a new replication control agent name for the named database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationServerCreateNew", String.class);
        }
        catch (Exception ex24) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Create a new replication server group for the named database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationAgentCreateNew", String.class);
        }
        catch (Exception ex25) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Create a new replication agent group for the named database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationTransitionCreateNew", String.class);
        }
        catch (Exception ex26) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Create a new replication transition group for the named database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationCtrlAgentCreateNew", String.class, String.class);
        }
        catch (Exception ex27) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Create a new replication control agent name for the named database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationDeleteCtrlAgent", String.class, String.class);
        }
        catch (Exception ex28) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Delete the replication control agent name for the named database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationDeleteServer", String.class);
        }
        catch (Exception ex29) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Delete the replication server group for the named database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationDeleteAgent", String.class);
        }
        catch (Exception ex30) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Delete the replication agent group for the named database.", method);
        try {
            method = ReplicationPluginComponent.class.getMethod("replicationPropertyRefresh", String.class);
        }
        catch (Exception ex31) {}
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Refresh read of replication property file.", method);
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReplServerAdded", "A replication server group has been added.");
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReplServerDeleted", "A replication server group has been deleted.");
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReplCtlAgentAdded", "A replication control agent group has been added.");
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReplCtlAgentDeleted", "A replication control agent group has been deleted.");
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReplAgentAdded", "A replication agent group has been added.");
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReplAgentDeleted", "A replication agent group has been deleted.");
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReplTransitionAdded", "A replication transition group has been added.");
        ReplicationPluginComponent.REPL_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReplTransitionDeleted", "A replication transition group has been deleted.");
    }
}
