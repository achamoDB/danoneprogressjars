// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Vector;

public interface IPanelUpdateCallback
{
    void applyChanges(final Vector p0);
    
    void resetChanges();
    
    void resetDefaults();
}
