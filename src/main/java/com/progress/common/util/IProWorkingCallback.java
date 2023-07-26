// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public interface IProWorkingCallback
{
    public static final int WORKING_DONE = 0;
    public static final int WORKING_CANCEL = 1;
    
    void workingCallback(final Integer p0);
}
