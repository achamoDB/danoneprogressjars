// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;

public abstract class ARState extends State
{
    static ProgressResources resources;
    
    public ARState(final Class clazz) {
        super(clazz);
    }
    
    static {
        ARState.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
    }
}
