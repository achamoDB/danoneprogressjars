// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

public interface IAiaDisplayInfo
{
    String getDisplayInfoTitle();
    
    String[] getDisplayInfoLabels();
    
    AiaDisplayInfoDesc[][] getDisplayInfoTable();
    
    AiaDisplayInfoDesc[] getDisplayInfoRow();
}
