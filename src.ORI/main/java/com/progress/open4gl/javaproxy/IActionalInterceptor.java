// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import java.util.HashMap;

public interface IActionalInterceptor
{
    public static final short ACT_DT_OEGROUP = 681;
    public static final short ACT_DT_BATCH = 682;
    public static final short ACT_DT_MQADAPTER = 683;
    public static final short ACT_DT_WSA = 684;
    public static final short ACT_DT_WEBSPEED = 685;
    public static final short ACT_DT_APPSERVER = 686;
    public static final short ACT_DT_AIA = 687;
    
    void beginInteraction(final HashMap p0);
    
    String analyze(final String p0);
    
    void endInteraction(final String p0);
    
    String getHeader();
    
    String getGroup();
    
    void setGroup(final String p0);
    
    String getService();
    
    void setService(final String p0);
    
    String getOperation();
    
    void setOperation(final String p0);
    
    String getUrl();
    
    void setUrl(final String p0);
    
    String getPeer();
    
    void setPeer(final String p0);
}
