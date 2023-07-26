// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.property.PropertyManager;
import java.rmi.RemoteException;
import java.util.Vector;
import com.progress.chimera.common.IChimeraHierarchy;

public interface IReplDatabaseHandle extends IChimeraHierarchy, IReplicationPluginComponent
{
    public static final String REPL_DATABASE_CLASSNAME = "com.progress.database.replication.ReplDatabase";
    public static final String EREPLADDED__CLASSNAME = "com.progress.database.replication.EReplCategoryAdded";
    public static final String EREPLDELETED__CLASSNAME = "com.progress.database.replication.EReplCategoryDeleted";
    
    Vector getReplDatabaseArgs(final int p0) throws RemoteException;
    
    void updateChildren(final String p0, final Object p1, final boolean p2) throws RemoteException, PropertyManager.PropertyException;
}
