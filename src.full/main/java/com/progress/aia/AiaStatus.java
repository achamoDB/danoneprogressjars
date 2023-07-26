// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

public class AiaStatus implements IAiaDisplayInfo
{
    private String titleString;
    private String[] scpDisplayInfoLabels;
    private AiaDisplayInfoDesc[][] scpDisplayInfoDesc;
    private IAiaDisplayInfo m_scpDispInfo;
    
    public AiaStatus(final IAiaDisplayInfo aiaDisplayInfo) {
        this.titleString = null;
        this.scpDisplayInfoLabels = null;
        this.scpDisplayInfoDesc = null;
        this.m_scpDispInfo = null;
        this.scpDisplayInfoDesc = aiaDisplayInfo.getDisplayInfoTable();
        this.scpDisplayInfoLabels = aiaDisplayInfo.getDisplayInfoLabels();
        this.titleString = aiaDisplayInfo.getDisplayInfoTitle();
    }
    
    public String getDisplayInfoTitle() {
        return this.titleString;
    }
    
    public String[] getDisplayInfoLabels() {
        return this.scpDisplayInfoLabels;
    }
    
    public AiaDisplayInfoDesc[][] getDisplayInfoTable() {
        return this.scpDisplayInfoDesc;
    }
    
    public AiaDisplayInfoDesc[] getDisplayInfoRow() {
        return null;
    }
}
