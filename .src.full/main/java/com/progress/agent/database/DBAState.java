// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import com.progress.juniper.admin.State;

public abstract class DBAState extends State
{
    static ProgressResources resources;
    
    public DBAState(final Class clazz) {
        super(clazz);
    }
    
    static {
        DBAState.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
    }
}
