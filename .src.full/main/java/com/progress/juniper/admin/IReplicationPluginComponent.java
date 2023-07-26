// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Vector;
import java.util.Hashtable;
import java.rmi.RemoteException;

public interface IReplicationPluginComponent
{
    Boolean isReplicationLicensed() throws RemoteException;
    
    Boolean hasReplicationProperties(final String p0) throws RemoteException;
    
    Boolean hasReplicationServer(final String p0) throws RemoteException;
    
    String[] getReplicationCtrlAgents(final String p0) throws RemoteException;
    
    Boolean hasReplicationAgent(final String p0) throws RemoteException;
    
    Boolean hasReplicationTransition(final String p0) throws RemoteException;
    
    Integer isReplicationEnabled(final String p0) throws RemoteException;
    
    Boolean writeReplicationProperties(final String p0) throws RemoteException;
    
    Vector validateReplicationProperties(final String p0, final String[] p1, final String p2, final Hashtable p3, final Hashtable p4) throws RemoteException;
    
    Hashtable getReplicationConfiguration(final String p0, final String p1) throws RemoteException;
    
    Hashtable getReplicationConfigurationSchema(final String p0, final String[] p1) throws RemoteException;
    
    Hashtable getReplicationConfigurationSchema(final String p0, final String p1) throws RemoteException;
    
    void setReplicationConfiguration(final String p0, final String p1, final Hashtable p2) throws RemoteException;
    
    Hashtable getReplicationGroupSchemaHashtable(final String p0, final String p1) throws RemoteException;
    
    Hashtable getReplicationGroupAttributeHashtable(final String p0, final String p1) throws RemoteException;
    
    Hashtable getReplicationPropertySchemaHashtable(final String p0, final String p1) throws RemoteException;
    
    Hashtable getReplicationCategorySchemaHashtable(final String p0, final String p1) throws RemoteException;
    
    Hashtable getReplicationCategorySchemaHashtable(final String p0, final String p1, final String p2) throws RemoteException;
    
    Hashtable getReplicationCategorySchemaHashtable(final String p0, final String p1, final String[] p2) throws RemoteException;
    
    Hashtable getReplicationCategoryAttributeHashtable(final String p0, final String p1) throws RemoteException;
    
    Hashtable makeReplicationPropertyValueHashtable(final String p0, final String p1) throws RemoteException;
    
    Boolean replicationCtrlAgentNameUsed(final String p0, final String p1) throws RemoteException;
    
    String replicationCtrlAgentMakeNewName(final String p0) throws RemoteException;
    
    Boolean replicationServerCreateNew(final String p0) throws RemoteException;
    
    Boolean replicationAgentCreateNew(final String p0) throws RemoteException;
    
    Boolean replicationTransitionCreateNew(final String p0) throws RemoteException;
    
    Boolean replicationCtrlAgentCreateNew(final String p0, final String p1) throws RemoteException;
    
    Boolean replicationDeleteCtrlAgent(final String p0, final String p1) throws RemoteException;
    
    Boolean replicationDeleteServer(final String p0) throws RemoteException;
    
    Boolean replicationDeleteAgent(final String p0) throws RemoteException;
    
    Boolean replicationPropertyRefresh(final String p0) throws RemoteException;
}
