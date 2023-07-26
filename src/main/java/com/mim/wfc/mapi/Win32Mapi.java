// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.mapi;

class Win32Mapi
{
    public static native int MAPILogoff(final int p0, final int p1, final int p2, final int p3);
    
    public static native int MAPIDeleteMail(final int p0, final int p1, final String p2, final int p3, final int p4);
    
    public static native int MAPIFindNext(final int p0, final int p1, final String p2, final String p3, final int p4, final int p5, final StringBuffer p6);
    
    public static native int MAPIReadMail(final int p0, final int p1, final String p2, final int p3, final int p4, final int[] p5);
    
    public static native int MAPIDetails(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    public static native int MAPIFreeBuffer(final int p0);
    
    public static native int MAPIResolveName(final int p0, final int p1, final String p2, final int p3, final int p4, final int p5);
    
    public static native int MAPISendDocuments(final int p0, final String p1, final String p2, final String p3, final int p4);
    
    public static native int MAPISendMail(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    public static native int MAPISaveMail(final int p0, final int p1, final int p2, final int p3, final int p4, final String p5);
    
    public static native int MAPIAddress(final int p0, final int p1, final String p2, final int p3, final String p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int[] p10);
    
    public static native int MAPILogon(final int p0, final String p1, final String p2, final int p3, final int p4, final int[] p5);
}
