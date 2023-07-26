// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.io.Serializable;

public final class SDOScrollingMode implements Serializable
{
    private int mode;
    private static final String[] modeNames;
    public static final int FORWARD_ONLY_MODE = 1;
    public static final int KEEP_ROWS_MODE = 2;
    public static final int PREFETCH_MODE = 3;
    public static final SDOScrollingMode FORWARD_ONLY;
    public static final SDOScrollingMode KEEP_ROWS;
    public static final SDOScrollingMode PREFETCH;
    
    private SDOScrollingMode(final int mode) {
        this.mode = mode;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public String getModeName() {
        return SDOScrollingMode.modeNames[this.mode];
    }
    
    public static String getModeName(final int n) {
        return SDOScrollingMode.modeNames[n];
    }
    
    static {
        modeNames = new String[] { "", "FORWARD_ONLY", "KEEP_ROWS", "PREFETCH" };
        FORWARD_ONLY = new SDOScrollingMode(1);
        KEEP_ROWS = new SDOScrollingMode(2);
        PREFETCH = new SDOScrollingMode(3);
    }
}
