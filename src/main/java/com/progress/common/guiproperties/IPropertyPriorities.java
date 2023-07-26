// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import com.progress.chimera.common.IChimeraRemoteObject;

public interface IPropertyPriorities extends IChimeraRemoteObject
{
    public static final int priorityMandatory = 1;
    public static final int priorityCritical = 2;
    public static final int priorityImportant = 3;
    public static final int priorityDesirable = 4;
    public static final int priorityLow = 5;
}
